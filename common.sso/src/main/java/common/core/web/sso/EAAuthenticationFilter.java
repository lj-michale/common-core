package common.core.web.sso;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.DefaultGatewayResolverImpl;
import org.jasig.cas.client.authentication.GatewayResolver;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.ApplicationContextUtil;
import common.core.common.util.StringUtil;

public class EAAuthenticationFilter extends AbstractCasFilter {
	private final Logger logger = LoggerFactory.getLogger(EAAuthenticationFilter.class);
	/**
	 * The URL to the CAS Server login.
	 */
	private String casServerLoginUrl;

	/**
	 * Whether to send the renew request or not.
	 */
	private boolean renew = false;

	/**
	 * Whether to send the gateway request or not.
	 */
	private boolean gateway = false;

	private String[] ignoreUrls = {};
	private String[] ignoreStartWithUrls = {};

	private GatewayResolver gatewayStorage = new DefaultGatewayResolverImpl();
	private EaSsoService eaSsoService;

	protected void initInternal(final FilterConfig filterConfig) throws ServletException {
		if (!isIgnoreInitConfiguration()) {
			super.initInternal(filterConfig);
			this.setServerName(EaSsoSetting.get().getServerName());
			this.setCasServerLoginUrl(EaSsoSetting.get().getCasServerLoginUrl());
			setRenew(EaSsoSetting.get().isRenew());
			logger.trace("Loaded renew parameter: " + this.renew);
			setGateway(EaSsoSetting.get().getGateway());
			logger.trace("Loaded gateway parameter: " + this.gateway);

			final String gatewayStorageClass = EaSsoSetting.get().getGatewayStorageClass();

			if (gatewayStorageClass != null) {
				try {
					this.gatewayStorage = (GatewayResolver) Class.forName(gatewayStorageClass).newInstance();
				} catch (final Exception e) {
					logger.error(e.getMessage(), e);
					throw new ServletException(e);
				}
			}
		}
	}

	public void init() {
		super.init();
		CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
		this.ignoreUrls = EaSsoSetting.get().getIgnoreUrls();
		this.ignoreStartWithUrls = EaSsoSetting.get().getIgnoreStartWithUrls();
	}

	public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final HttpSession session = request.getSession(false);
		final Assertion assertion = session != null ? (Assertion) session.getAttribute(CONST_CAS_ASSERTION) : null;

		if (isIgnoreUrl(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		if (assertion != null) {
			filter(filterChain, request, response, assertion);
			return;
		}

		final String serviceUrl = constructServiceUrl(request, response);
		final String ticket = CommonUtils.safeGetParameter(request, getArtifactParameterName());
		final boolean wasGatewayed = this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);

		if (CommonUtils.isNotBlank(ticket) || wasGatewayed) {
			filterChain.doFilter(request, response);
			return;
		}

		final String modifiedServiceUrl;

		logger.debug("no ticket and no assertion found");
		if (this.gateway) {
			logger.debug("setting gateway attribute in session");
			modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);
		} else {
			modifiedServiceUrl = serviceUrl;
		}

		logger.debug("Constructed service url: " + modifiedServiceUrl);

		final String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl, getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);

		logger.debug("redirecting to \"" + urlToRedirectTo + "\"");

		response.sendRedirect(urlToRedirectTo);
	}

	private void filter(final FilterChain filterChain, final HttpServletRequest request, final HttpServletResponse response, final Assertion assertion) throws IOException, ServletException {
		String account = StringUtil.split(assertion.getPrincipal().getName(), "||")[0];
		logger.debug("request from account [{}]", account);
		User user = getUser(request, account);
		AssertErrorUtils.assertNotNull(user, "can't load user account [{}]", account);
		try {
			UserHolder.setUser(user);
			filterChain.doFilter(request, response);
		} finally {
			UserHolder.clear();
		}
	}


	private User getUser(final HttpServletRequest request, String account) {
		final HttpSession session = request.getSession();
		User user = (User) session.getAttribute(User.SESSION_USER_NAME);
		if (user == null || !account.equals(user.getAccount())) {
			// get user by loginName
			if (null == eaSsoService) {
				eaSsoService = ApplicationContextUtil.getBean(EaSsoService.class);
				AssertErrorUtils.assertNotNull(eaSsoService, "UserService is not register");
			}
			logger.debug("get user by loginName [{}]", account);
			user = eaSsoService.getUserByAccount(account);
			session.setAttribute(User.SESSION_USER_NAME, user);
		}
		return user;
	}

	private boolean isIgnoreUrl(final HttpServletRequest request) {
		String uri = request.getRequestURI();
		if (StringUtil.isNotBlank(EaSsoSetting.get().getValidUrl())) {
			if (EaSsoSetting.get().getValidUrl().equals(request.getRequestURL().toString()))
				return false;
			return true;
		}

		boolean filterUrl = Arrays.binarySearch(this.ignoreUrls, 0, this.ignoreUrls.length, uri) >= 0;
		if (!filterUrl) {
			for (String item : this.ignoreStartWithUrls) {
				if (!uri.startsWith(item))
					continue;
				filterUrl = true;
			}
		}
		return filterUrl;
	}

	public final void setRenew(final boolean renew) {
		this.renew = renew;
	}

	public final void setGateway(final boolean gateway) {
		this.gateway = gateway;
	}

	public final void setCasServerLoginUrl(final String casServerLoginUrl) {
		this.casServerLoginUrl = casServerLoginUrl;
	}

	public final void setGatewayStorage(final GatewayResolver gatewayStorage) {
		this.gatewayStorage = gatewayStorage;
	}

}
