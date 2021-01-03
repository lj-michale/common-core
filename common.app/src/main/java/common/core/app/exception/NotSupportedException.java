package common.core.app.exception;

public class NotSupportedException extends RuntimeException {

	private static final long serialVersionUID = -255635847744257100L;

	public NotSupportedException() {
		super();
	}

	public NotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotSupportedException(String message) {
		super(message);
	}

	public NotSupportedException(Throwable cause) {
		super(cause);
	}

}
