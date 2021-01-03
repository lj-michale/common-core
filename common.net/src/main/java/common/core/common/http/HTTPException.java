package common.core.common.http;

public class HTTPException extends RuntimeException {
	private static final long serialVersionUID = -6288182623876453411L;

	public HTTPException(String message) {
		super(message);
	}

	public HTTPException(Throwable cause) {
		super(cause);
	}
}
