package common.core.site.session;

import javax.servlet.http.HttpSession;

public class SessionContext {
	private static final ThreadLocal<HttpSession> HTTP_SESSION = new ThreadLocal<>();

	public static void initContext(HttpSession httpSession) {
		SessionContext.HTTP_SESSION.set(httpSession);
	}

	public static void cleanContext() {
		SessionContext.HTTP_SESSION.remove();
	}

	public static HttpSession get() {
		return SessionContext.HTTP_SESSION.get();
	}

	@SuppressWarnings("unchecked")
	protected static <T> T getAttribute(SessionSpec<T> sessionSpec) {
		return (T) SessionContext.get().getAttribute(sessionSpec.getName());
	}

	protected static <T> void setAttribute(SessionSpec<T> sessionSpec, T value) {
		SessionContext.get().setAttribute(sessionSpec.getName(), value);
	}

	protected static <T> void removeAttribute(SessionSpec<T> sessionSpec) {
		SessionContext.get().removeAttribute(sessionSpec.getName());
	}

	public static String getSessionId() {
		return SessionContext.get().getId();
	}

	public static void invalidate() {
		SessionContext.get().invalidate();
	}

}
