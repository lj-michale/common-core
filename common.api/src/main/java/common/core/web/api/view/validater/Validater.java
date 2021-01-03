package common.core.web.api.view.validater;

public class Validater {

	private String rule;
	private String value;
	private String message;

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Validater() {
		super();
	}
	
	public Validater(String rule, String message) {
		super();
		this.rule = rule;
		this.message = message;
	}

	public Validater(String rule, String value, String message) {
		super();
		this.rule = rule;
		this.value = value;
		this.message = message;
	}

	@Override
	public String toString() {
		return "Validater [rule=" + rule + ", value=" + value + ", message=" + message + "]";
	}

}
