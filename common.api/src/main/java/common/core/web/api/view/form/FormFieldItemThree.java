package common.core.web.api.view.form;

public class FormFieldItemThree {

	private String value;
	private String name;
	private String rate;

	
	public String getRete() {
		return rate;
	}

	public void setRete(String rate) {
		this.rate = rate;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FormFieldItemThree(String value, String name) {
		super();
		this.value = value;
		this.name = name;
	}
	public FormFieldItemThree(String value, String name, String rate) {
		super();
		this.value = value;
		this.name = name;
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "FormFieldItemThree [value=" + value + ", name=" + name + ", rete=" + rate + "]";
	}
}
