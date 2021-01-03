package common.core.app.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FieldValidationException extends RuntimeException {

	private static final long serialVersionUID = -7939662581297979222L;

	private List<FieldError> fieldErrors = new ArrayList<FieldError>();

	public void addFieldError(FieldError fieldError) {
		fieldErrors.add(fieldError);
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	@Override
	public String getMessage() {
		StringBuffer msg = new StringBuffer();
		msg.append("fieldErrors:");
		msg.append(fieldErrors.toString());
		return msg.toString();
	}

	@Override
	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

	@Override
	public void printStackTrace(PrintStream s) {
		super.printStackTrace(s);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		super.printStackTrace(s);
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return super.getStackTrace();
	}

}
