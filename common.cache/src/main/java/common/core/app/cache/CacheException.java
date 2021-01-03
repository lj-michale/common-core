package common.core.app.cache;

public class CacheException extends RuntimeException {
	private static final long serialVersionUID = -9115663080634524271L;

	public CacheException() {
		super();
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}

}
