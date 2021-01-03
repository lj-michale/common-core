package common.core.app.exception;

public class MethodNotSupportedException extends RuntimeException {

	private static final long serialVersionUID = -255635847744257100L;

	public MethodNotSupportedException() {
		super();
	}

	public MethodNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MethodNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public MethodNotSupportedException(String message) {
		super(message);
	}

	public MethodNotSupportedException(Throwable cause) {
		super(cause);
	}

}
