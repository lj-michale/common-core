package common.core.app.lang.exception;

public class ErrorException extends RuntimeException {

	private static final long serialVersionUID = -8449777407412482766L;

	public ErrorException() {
		super();
	}

	public ErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ErrorException(String message) {
		super(message);
	}

	public ErrorException(Throwable cause) {
		super(cause);
	}

}
