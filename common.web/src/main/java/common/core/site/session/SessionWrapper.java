package common.core.site.session;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import common.core.app.exception.MethodNotSupportedException;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.ApplicationContextUtil;

public class SessionWrapper implements HttpSession {
	private HttpSession httpSession;
	private SessionData sessionData;
	private static SessionProvider sessionProvider = null;

	public SessionWrapper(String sessionId, String ip, HttpSession httpSession) {
		super();
		AssertErrorUtils.assertNotNull(httpSession, "httpSession must not null");
		if (null == sessionProvider)
			sessionProvider = ApplicationContextUtil.getBean(SessionProvider.class);
		loadSessionData(sessionId, ip);
	}

	public void loadSessionData(String sid, String ip) {
		this.sessionData = sessionProvider.loadSessionData(sid, ip);
		if (null == sessionData) {
			sessionData = sessionProvider.createSessionData(sid, ip);
			this.updateSessionData();
		}
	}

	private void updateSessionData() {
		AssertErrorUtils.assertNotNull(sessionData.getId(), "Session id is null");
		sessionProvider.updateSessionData(sessionData);
	}

	@Override
	public long getCreationTime() {
		return this.sessionData.getCreationTime();
	}

	@Override
	public String getId() {
		return this.sessionData.getId();
	}

	@Override
	public long getLastAccessedTime() {
		return System.currentTimeMillis();
	}

	@Override
	public ServletContext getServletContext() {
		if (null == httpSession)
			return null;
		return httpSession.getServletContext();
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		this.sessionData.setMaxInactiveInterval(interval);
		this.updateSessionData();
	}

	@Override
	public int getMaxInactiveInterval() {
		return this.sessionData.getMaxInactiveInterval();
	}

	@Override
	public Object getAttribute(String name) {
		return this.sessionData.get(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {

		Iterator<String> keys = this.sessionData.keySet().iterator();
		return new Enumeration<String>() {

			@Override
			public String nextElement() {
				return keys.next();
			}

			@Override
			public boolean hasMoreElements() {
				return keys.hasNext();
			}
		};

	}

	@Override
	public void setAttribute(String name, Object value) {
		if (null == value) {
			this.removeAttribute(name);
			return;
		}
		AssertErrorUtils.assertTrue(value instanceof java.io.Serializable, "please put session value as java.io.Serializable");
		sessionData.put(name, (Serializable) value);
		this.updateSessionData();
	}

	@Override
	public void removeAttribute(String name) {
		sessionData.remove(name);
		this.updateSessionData();
	}

	@Override
	public void invalidate() {
		this.sessionData.clear();
		this.updateSessionData();
	}

	@Override
	public boolean isNew() {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		throw new MethodNotSupportedException("ShareSessionHttpSessionWrapper.getValue");
	}

	@Override
	public Object getValue(String name) {
		throw new MethodNotSupportedException("ShareSessionHttpSessionWrapper.getValue");
	}

	@Override
	public String[] getValueNames() {
		throw new MethodNotSupportedException("ShareSessionHttpSessionWrapper.getValue");
	}

	@Override
	public void putValue(String name, Object value) {
		throw new MethodNotSupportedException("ShareSessionHttpSessionWrapper.getValue");
	}

	@Override
	public void removeValue(String name) {
		throw new MethodNotSupportedException("ShareSessionHttpSessionWrapper.getValue");
	}

}
