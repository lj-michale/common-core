/**
 * 
 */
package common.core.app.exception;

/**
 * @author user
 *
 */
public class InitException extends RuntimeException {
	private static final long serialVersionUID = -1604220412924789591L;

	public InitException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InitException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitException(String message) {
		super(message);
	}

	public InitException(Throwable cause) {
		super(cause);
	}

}
