/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package common.core.web.sso;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.proxy.AbstractEncryptedProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.proxy.Cas20ProxyRetriever;
import org.jasig.cas.client.proxy.CleanUpTimerTask;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.ReflectUtils;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;

import common.core.app.context.ConfigContext;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;

/**
 * Creates either a CAS20ProxyTicketValidator or a CAS20ServiceTicketValidator
 * depending on whether any of the proxy parameters are set.
 * <p>
 * This filter can also pass additional parameters to the ticket validator. Any
 * init parameter not included in the reserved list
 * {@link org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter#RESERVED_INIT_PARAMS}
 * .
 *
 * @author Scott Battaglia
 * @author Brad Cupit (brad [at] lsu {dot} edu)
 * @version $Revision$ $Date$
 * @since 3.1
 *
 */
public class Cas20ProxyReceivingTicketValidationFilter extends AbstractTicketValidationFilter {

	private Logger log = LoggerFactory.getLogger(Cas20ProxyReceivingTicketValidationFilter.class);

	private static final String[] RESERVED_INIT_PARAMS = new String[] { "proxyGrantingTicketStorageClass", "proxyReceptorUrl", "acceptAnyProxy", "allowedProxyChains", "casServerUrlPrefix", "proxyCallbackUrl", "renew", "exceptionOnValidationFailure", "redirectAfterValidation", "useSession",
			"serverName", "service", "artifactParameterName", "serviceParameterName", "encodeServiceUrl", "millisBetweenCleanUps", "hostnameVerifier", "encoding", "config" };

	private static final int DEFAULT_MILLIS_BETWEEN_CLEANUPS = 60 * 1000;

	/**
	 * The URL to send to the CAS server as the URL that will process proxying
	 * requests on the CAS client.
	 */
	private String proxyReceptorUrl;

	private Timer timer;

	private TimerTask timerTask;

	private int millisBetweenCleanUps;

	/**
	 * Storage location of ProxyGrantingTickets and Proxy Ticket IOUs.
	 */
	private ProxyGrantingTicketStorage proxyGrantingTicketStorage = new ProxyGrantingTicketStorageImpl();

	protected void initInternal(final FilterConfig filterConfig) throws ServletException {

		setProxyReceptorUrl(ConfigContext.getStringValue("sso.ea.proxyReceptorUrl", null));
		final String proxyGrantingTicketStorageClass = ConfigContext.getStringValue("sso.ea.proxyGrantingTicketStorageClass", null);

		if (proxyGrantingTicketStorageClass != null) {
			this.proxyGrantingTicketStorage = ReflectUtils.newInstance(proxyGrantingTicketStorageClass);

			if (this.proxyGrantingTicketStorage instanceof AbstractEncryptedProxyGrantingTicketStorageImpl) {
				final AbstractEncryptedProxyGrantingTicketStorageImpl p = (AbstractEncryptedProxyGrantingTicketStorageImpl) this.proxyGrantingTicketStorage;
				final String cipherAlgorithm = ConfigContext.getStringValue("sso.ea.cipherAlgorithm", AbstractEncryptedProxyGrantingTicketStorageImpl.DEFAULT_ENCRYPTION_ALGORITHM);
				final String secretKey = ConfigContext.getStringValue("sso.ea.secretKey", null);

				p.setCipherAlgorithm(cipherAlgorithm);

				try {
					if (secretKey != null) {
						p.setSecretKey(secretKey);
					}
				} catch (final Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		log.trace("Setting proxyReceptorUrl parameter: " + this.proxyReceptorUrl);
		this.millisBetweenCleanUps = ConfigContext.getIntegerValue("sso.ea.millisBetweenCleanUps", DEFAULT_MILLIS_BETWEEN_CLEANUPS);
		super.initInternal(filterConfig);
	}

	public void init() {
		super.init();
		CommonUtils.assertNotNull(this.proxyGrantingTicketStorage, "proxyGrantingTicketStorage cannot be null.");

		if (this.timer == null) {
			this.timer = new Timer(true);
		}

		if (this.timerTask == null) {
			this.timerTask = new CleanUpTimerTask(this.proxyGrantingTicketStorage);
		}
		this.timer.schedule(this.timerTask, this.millisBetweenCleanUps, this.millisBetweenCleanUps);
	}

	/**
	 * Constructs a Cas20ServiceTicketValidator or a Cas20ProxyTicketValidator
	 * based on supplied parameters.
	 *
	 * @param filterConfig
	 *            the Filter Configuration object.
	 * @return a fully constructed TicketValidator.
	 */
	protected final TicketValidator getTicketValidator(final FilterConfig filterConfig) {
		final String allowAnyProxy = ConfigContext.getStringValue("sso.ea.acceptAnyProxy", null);
		final String allowedProxyChains = ConfigContext.getStringValue("sso.ea.allowedProxyChains", null);
		final String casServerUrlPrefix = EaSsoSetting.get().getCasServerUrlPrefix();
		super.setServerName(EaSsoSetting.get().getServerName());
		final Cas20ServiceTicketValidator validator;

		if (CommonUtils.isNotBlank(allowAnyProxy) || CommonUtils.isNotBlank(allowedProxyChains)) {
			final Cas20ProxyTicketValidator v = new Cas20ProxyTicketValidator(casServerUrlPrefix);
			v.setAcceptAnyProxy(parseBoolean(allowAnyProxy));
			v.setAllowedProxyChains(CommonUtils.createProxyList(allowedProxyChains));
			validator = v;
		} else {
			validator = new Cas20ServiceTicketValidator(casServerUrlPrefix);
		}
		validator.setProxyCallbackUrl(ConfigContext.getStringValue("sso.ea.proxyCallbackUrl", null));
		validator.setProxyGrantingTicketStorage(this.proxyGrantingTicketStorage);
		validator.setProxyRetriever(new Cas20ProxyRetriever(casServerUrlPrefix, ConfigContext.getStringValue("sso.ea.encoding", null)));
		validator.setRenew(EaSsoSetting.get().isRenew());
		validator.setEncoding(ConfigContext.getStringValue("sso.ea.encoding", null));

		final Map<String, String> additionalParameters = new HashMap<String, String>();
		final List<String> params = Arrays.asList(RESERVED_INIT_PARAMS);

		for (final Enumeration<?> e = filterConfig.getInitParameterNames(); e.hasMoreElements();) {
			final String s = (String) e.nextElement();

			if (!params.contains(s)) {
				additionalParameters.put(s, filterConfig.getInitParameter(s));
			}
		}
		validator.setCustomParameters(additionalParameters);
		validator.setHostnameVerifier(getHostnameVerifier(filterConfig));

		return validator;
	}

	public void destroy() {
		super.destroy();
		this.timer.cancel();
	}

	/**
	 * This processes the ProxyReceptor request before the ticket validation
	 * code executes.
	 */
	protected final boolean preFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final String requestUri = request.getRequestURI();

		if (CommonUtils.isEmpty(this.proxyReceptorUrl) || !requestUri.endsWith(this.proxyReceptorUrl)) {
			return true;
		}

		try {
			CommonUtils.readAndRespondToProxyReceptorRequest(request, response, this.proxyGrantingTicketStorage);
		} catch (final RuntimeException e) {
			log.error(e.getMessage(), e);
			throw e;
		}

		return false;
	}

	public final void setProxyReceptorUrl(final String proxyReceptorUrl) {
		this.proxyReceptorUrl = proxyReceptorUrl;
	}

	public void setProxyGrantingTicketStorage(final ProxyGrantingTicketStorage storage) {
		this.proxyGrantingTicketStorage = storage;
	}

	public void setTimer(final Timer timer) {
		this.timer = timer;
	}

	public void setTimerTask(final TimerTask timerTask) {
		this.timerTask = timerTask;
	}

	public void setMillisBetweenCleanUps(final int millisBetweenCleanUps) {
		this.millisBetweenCleanUps = millisBetweenCleanUps;
	}
}
