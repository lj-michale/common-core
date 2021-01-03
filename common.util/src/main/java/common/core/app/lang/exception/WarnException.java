package common.core.app.lang.exception;

public class WarnException extends RuntimeException {
	private static final long serialVersionUID = -5077181725950675617L;

	public WarnException() {
		super();
	}

	public WarnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WarnException(String message, Throwable cause) {
		super(message, cause);
	}

	public WarnException(String message) {
		super(message);
	}

	public WarnException(Throwable cause) {
		super(cause);
	}

}
