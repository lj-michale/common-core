package common.core.web.exception;

public class WarnMessageException extends RuntimeException {
	private static final long serialVersionUID = 355519306031810190L;

	public WarnMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public WarnMessageException(String message) {
		super(message);
	}

}
