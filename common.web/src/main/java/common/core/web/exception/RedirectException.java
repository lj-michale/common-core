package common.core.web.exception;

import common.core.app.lang.exception.IgnoreException;

@IgnoreException
public class RedirectException extends RuntimeException {

	private static final long serialVersionUID = -560202209880997283L;

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public RedirectException(String url) {
		super();
		this.url = url;
	}

	@Override
	public String getMessage() {
		return "redirect to :" + this.getUrl();
	}

}
