package common.core.site.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import common.core.web.context.RequestContext;

public class SessionRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {
	private String sessionId;
	private HttpSession httpSession;

	@Override
	public HttpSession getSession() {
		return this.getSession(true);
	}

	@Override
	public HttpSession getSession(boolean create) {
		if (httpSession == null)
			httpSession = new SessionWrapper(sessionId, RequestContext.getRemoteAddr(), super.getSession(true));
		return httpSession;
	}

	public SessionRequestWrapper(String sessionId, HttpServletRequest request) {
		super(request);
		this.sessionId = sessionId;
	}

}
