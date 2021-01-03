package common.core.common.http;

public class HTTPStatusCode {
	private final int code;

	public HTTPStatusCode(int code) {
		this.code = code;
	}

	public int code() {
		return code;
	}

	public boolean isRedirect() {
		return code == HTTPConstants.SC_MOVED_TEMPORARILY || code == HTTPConstants.SC_MOVED_PERMANENTLY || code == HTTPConstants.SC_TEMPORARY_REDIRECT || code == HTTPConstants.SC_SEE_OTHER;
	}

	public boolean isSuccess() {
		return code >= HTTPConstants.SC_OK && code <= HTTPConstants.SC_MULTI_STATUS;
	}

	public boolean isServerError() {
		return code >= HTTPConstants.SC_INTERNAL_SERVER_ERROR && code <= HTTPConstants.SC_INSUFFICIENT_STORAGE;
	}

	@Override
	public String toString() {
		return String.valueOf(code);
	}
}
