package common.core.site.tag;

public class TemplateExceptoin extends RuntimeException {
	private static final long serialVersionUID = 7586523858490569048L;

	public TemplateExceptoin() {
		super();
	}

	public TemplateExceptoin(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TemplateExceptoin(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateExceptoin(String message) {
		super(message);
	}

	public TemplateExceptoin(Throwable cause) {
		super(cause);
	}

}
