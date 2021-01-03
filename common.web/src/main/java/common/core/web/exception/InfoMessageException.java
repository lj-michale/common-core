package common.core.web.exception;

public class InfoMessageException extends RuntimeException {
	private static final long serialVersionUID = 355519306031810190L;

	public InfoMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public InfoMessageException(String message) {
		super(message);
	}

}
