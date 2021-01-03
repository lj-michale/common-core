package common.core.common.exception;

public class ConvertException extends RuntimeException {
	private static final long serialVersionUID = 2471376384792283090L;

	public ConvertException() {
		super();
	}

	public ConvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConvertException(String message) {
		super(message);
	}

	public ConvertException(Throwable cause) {
		super(cause);
	}

}
