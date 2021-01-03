/**
 * 
 */
package common.core.web.sso;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.ReflectUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.StringUtil;

/**
 *
 */
public class AbstractTicketValidationFilter extends AbstractCasFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTicketValidationFilter.class);

	/** The TicketValidator we will use to validate tickets. */
	private TicketValidator ticketValidator;

	/**
	 * Specify whether the filter should redirect the user agent after a
	 * successful validation to remove the ticket parameter from the query
	 * string.
	 */
	private boolean redirectAfterValidation = false;

	/**
	 * Determines whether an exception is thrown when there is a ticket
	 * validation failure.
	 */
	private boolean exceptionOnValidationFailure = true;

	private boolean useSession = true;

	/**
	 * Template method to return the appropriate validator.
	 *
	 * @param filterConfig
	 *            the FilterConfiguration that may be needed to construct a
	 *            validator.
	 * @return the ticket validator.
	 */
	protected TicketValidator getTicketValidator(final FilterConfig filterConfig) {
		return this.ticketValidator;
	}

	/**
	 * Gets the configured {@link HostnameVerifier} to use for HTTPS connections
	 * if one is configured for this filter.
	 * 
	 * @param filterConfig
	 *            Servlet filter configuration.
	 * @return Instance of specified host name verifier or null if none
	 *         specified.
	 */
	protected HostnameVerifier getHostnameVerifier(final FilterConfig filterConfig) {
		final String className = getPropertyFromInitParams(filterConfig, "hostnameVerifier", null);
		LOGGER.trace("Using hostnameVerifier parameter: " + className);
		final String config = getPropertyFromInitParams(filterConfig, "hostnameVerifierConfig", null);
		LOGGER.trace("Using hostnameVerifierConfig parameter: " + config);
		if (className != null) {
			if (config != null) {
				return ReflectUtils.newInstance(className, config);
			} else {
				return ReflectUtils.newInstance(className);
			}
		}
		return null;
	}

	protected void initInternal(final FilterConfig filterConfig) throws ServletException {
		setExceptionOnValidationFailure(parseBoolean(getPropertyFromInitParams(filterConfig, "exceptionOnValidationFailure", "true")));
		LOGGER.trace("Setting exceptionOnValidationFailure parameter: " + this.exceptionOnValidationFailure);
		setRedirectAfterValidation(parseBoolean(getPropertyFromInitParams(filterConfig, "redirectAfterValidation", "true")));
		LOGGER.trace("Setting redirectAfterValidation parameter: " + this.redirectAfterValidation);
		setUseSession(parseBoolean(getPropertyFromInitParams(filterConfig, "useSession", "true")));
		LOGGER.trace("Setting useSession parameter: " + this.useSession);
		setTicketValidator(getTicketValidator(filterConfig));
		super.initInternal(filterConfig);
	}

	public void init() {
		super.init();
		CommonUtils.assertNotNull(this.ticketValidator, "ticketValidator cannot be null.");
	}

	/**
	 * Pre-process the request before the normal filter process starts. This
	 * could be useful for pre-empting code.
	 *
	 * @param servletRequest
	 *            The servlet request.
	 * @param servletResponse
	 *            The servlet response.
	 * @param filterChain
	 *            the filter chain.
	 * @return true if processing should continue, false otherwise.
	 * @throws IOException
	 *             if there is an I/O problem
	 * @throws ServletException
	 *             if there is a servlet problem.
	 */
	protected boolean preFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
		return true;
	}


	/**
	 * Template method that gets executed if ticket validation succeeds.
	 * Override if you want additional behavior to occur if ticket validation
	 * succeeds. This method is called after all ValidationFilter processing
	 * required for a successful authentication occurs.
	 *
	 * @param request
	 *            the HttpServletRequest.
	 * @param response
	 *            the HttpServletResponse.
	 * @param assertion
	 *            the successful Assertion from the server.
	 */
	protected void onSuccessfulValidation(final HttpServletRequest request, final HttpServletResponse response, final Assertion assertion) {
		// nothing to do here.
	}

	/**
	 * Template method that gets executed if validation fails. This method is
	 * called right after the exception is caught from the ticket validator but
	 * before any of the processing of the exception occurs.
	 *
	 * @param request
	 *            the HttpServletRequest.
	 * @param response
	 *            the HttpServletResponse.
	 */
	protected void onFailedValidation(final HttpServletRequest request, final HttpServletResponse response) {
		// nothing to do here.
	}

	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {

		if (!preFilter(servletRequest, servletResponse, filterChain)) {
			return;
		}

		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final String ticket = CommonUtils.safeGetParameter(request, getArtifactParameterName());

		if (CommonUtils.isNotBlank(ticket)) {
			if (!validateSuccess(request, response, ticket))
				return;
		}

		filterChain.doFilter(request, response);

	}

	private boolean validateSuccess(final HttpServletRequest request, final HttpServletResponse response, final String ticket) throws IOException, ServletException {
		if (StringUtil.hasText(EaSsoSetting.get().getCasServerUrlPrefix()) && request.getRequestURL().toString().startsWith(EaSsoSetting.get().getCasServerUrlPrefix())) {
			return true;
		}

		LOGGER.debug("Attempting to validate ticket: " + ticket);

		try {
			final Assertion assertion = this.ticketValidator.validate(ticket, constructServiceUrl(request, response));

			LOGGER.debug("Successfully authenticated user: " + assertion.getPrincipal().getName());

			request.setAttribute(CONST_CAS_ASSERTION, assertion);

			if (this.useSession) {
				request.getSession().setAttribute(CONST_CAS_ASSERTION, assertion);
			}
			onSuccessfulValidation(request, response, assertion);

			if (this.redirectAfterValidation) {
				LOGGER.debug("Redirecting after successful ticket validation.");
				response.sendRedirect(constructServiceUrl(request, response));
				return false;
			}
		} catch (final TicketValidationException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			LOGGER.warn(e.getMessage(), e);

			onFailedValidation(request, response);

			if (this.exceptionOnValidationFailure) {
				throw new ServletException(e);
			}

			return false;
		}
		return true;
	}

	public final void setTicketValidator(final TicketValidator ticketValidator) {
		this.ticketValidator = ticketValidator;
	}

	public final void setRedirectAfterValidation(final boolean redirectAfterValidation) {
		this.redirectAfterValidation = redirectAfterValidation;
	}

	public final void setExceptionOnValidationFailure(final boolean exceptionOnValidationFailure) {
		this.exceptionOnValidationFailure = exceptionOnValidationFailure;
	}

	public final void setUseSession(final boolean useSession) {
		this.useSession = useSession;
	}

}
