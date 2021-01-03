package common.core.common.exception;

public class HttpClientsResponseException extends RuntimeException {

	private static final long serialVersionUID = -1899758211444941534L;

	public HttpClientsResponseException(String message) {
		super(message);
	}

	public HttpClientsResponseException(String message, Throwable cause) {
		super(message, cause);
	}

}
