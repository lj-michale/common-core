package common.core.common.exception;

public final class RuntimeIOException extends RuntimeException {
	private static final long serialVersionUID = 7696083883123852809L;

	public RuntimeIOException(Throwable cause) {
		super(cause);
	}

	public RuntimeIOException() {
		super();
	}

	public RuntimeIOException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuntimeIOException(String message) {
		super(message);
	}

}
