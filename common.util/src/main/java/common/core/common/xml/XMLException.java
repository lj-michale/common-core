package common.core.common.xml;

public final class XMLException extends RuntimeException {
	private static final long serialVersionUID = -7666302679799061519L;

	public XMLException(String message) {
		super(message);
	}

	public XMLException(Throwable cause) {
		super(cause);
	}
}
