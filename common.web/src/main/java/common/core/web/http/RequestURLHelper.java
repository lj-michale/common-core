package common.core.web.http;

import javax.servlet.RequestDispatcher;

import org.springframework.util.StringUtils;

import common.core.web.context.RequestContext;

public class RequestURLHelper {
	public String getClientRequestedRelativeURLWithQueryString() {
		String pathInfo = getClientRequestedPathInfo();
		String queryString = getClientRequestedQueryString();
		if (StringUtils.hasText(queryString)) {
			return pathInfo + '?' + queryString;
		} else {
			return pathInfo;
		}
	}

	public String getClientRequestedRelativeURL() {
		return getClientRequestedPathInfo();
	}

	// path info starts with '/' and doesn't include any context (servletContext
	// or deploymentContext)
	private String getClientRequestedPathInfo() {
		String forwardPath = (String) RequestContext.get().getAttribute(RequestDispatcher.FORWARD_PATH_INFO);
		if (forwardPath != null)
			return forwardPath;
		return RequestContext.get().getPathInfo();
	}

	public String getClientRequestedQueryString() {
		String forwardQueryString = (String) RequestContext.get().getAttribute(RequestDispatcher.FORWARD_QUERY_STRING);
		if (forwardQueryString != null)
			return forwardQueryString;
		return RequestContext.get().getQueryString();
	}
}
