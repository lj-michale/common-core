package common.core.app.exception;

public class FieldError {

	private String field;
	private String message;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public FieldError(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}

	@Override
	public String toString() {
		return "FieldError [field=" + field + ", message=" + message + "]";
	}

}
