package common.core.web.url;

public enum Scheme {
	HTTP("http"), HTTPS("https");
	
	String value;

	Scheme(String value) {
		this.value = value;
	}
	
}
