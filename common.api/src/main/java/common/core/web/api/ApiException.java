package common.core.web.api;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = -3045554549064419548L;

	public static final String SIGNATURE_VERIFY_FALSE = "SIGNATURE_VERIFY_FALSE";
	public static final String PARAM_IS_NOT_EMPTY = "PARAM_IS_NOT_EMPTY";
	public static final String PARAM_VALIDATOR_ERROR = "PARAM_VALIDATOR_ERROR";
	public static final String RUNTIME_EXCEPITON = "RUNTIME_EXCEPITON";

	private String code;

	public ApiException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public ApiException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ApiException(String code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "ApiException [code=" + code + ", toString()=" + super.toString() + "]";
	}

}
