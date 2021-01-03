package common.core.web.sso;

public class UserHolder {
	/**
	 * ThreadLocal to hold the User for Threads to access.
	 */
	private static final ThreadLocal<User> threadLocal = new ThreadLocal<User>();

	/**
	 * Retrieve the assertion from the ThreadLocal.
	 *
	 * @return the Asssertion associated with this thread.
	 */
	public static User getUser() {
		return threadLocal.get();
	}

	/**
	 * Add the Assertion to the ThreadLocal.
	 *
	 * @param assertion
	 *            the assertion to add.
	 */
	public static void setUser(final User user) {
		threadLocal.set(user);
	}

	/**
	 * Clear the ThreadLocal.
	 */
	public static void clear() {
		threadLocal.set(null);
	}
}
