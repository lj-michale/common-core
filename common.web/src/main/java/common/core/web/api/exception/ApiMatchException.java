package common.core.web.api.exception;

public class ApiMatchException extends RuntimeException {

	private static final long serialVersionUID = -3386013238530258330L;

	public ApiMatchException() {
		super();
	}

	public ApiMatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiMatchException(String message) {
		super(message);
	}

	public ApiMatchException(Throwable cause) {
		super(cause);
	}

}
