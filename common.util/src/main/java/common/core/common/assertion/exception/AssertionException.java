package common.core.common.assertion.exception;

public class AssertionException extends RuntimeException {
	private static final long serialVersionUID = 6937430613934060304L;

	public AssertionException() {
		super();
	}

	public AssertionException(String message, Throwable cause) {
		super(message, cause);
	}

	public AssertionException(String message) {
		super(message);
	}

	public AssertionException(Throwable cause) {
		super(cause);
	}

}
