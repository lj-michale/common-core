package common.core.app.lang;

/**
 * @author qinfj
 * @date 2015-1-23
 * @version 1.0.0
 */
public class KcbpException extends RuntimeException {
	private static final long serialVersionUID = 3650824655178728346L;
	private int errorCode;

	public KcbpException() {
	}

	public KcbpException(Throwable e) {
		super(e.getMessage(), e);
		super.setStackTrace(e.getStackTrace());
	}

	public KcbpException(String message) {
		super(message);
	}

	public KcbpException(String message, Throwable e) {
		super(message, e);
		super.setStackTrace(e.getStackTrace());
	}

	public KcbpException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
