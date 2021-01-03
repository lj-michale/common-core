package common.core.site.view;

@SuppressWarnings("serial")
public class ViewException extends RuntimeException {

	public ViewException() {
		super();
	}

	public ViewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ViewException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViewException(String message) {
		super(message);
	}

	public ViewException(Throwable cause) {
		super(cause);
	}

}
