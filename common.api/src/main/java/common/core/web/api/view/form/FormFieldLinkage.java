package common.core.web.api.view.form;

public class FormFieldLinkage {
    
	private String value;
	private String name;
	private String dyattr;
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
	public String getDyattr() {
		return dyattr;
	}
	public void setDyattr(String dyattr) {
		this.dyattr = dyattr;
	}
	public FormFieldLinkage(String value, String name) {
		super();
		this.value = value;
		this.name = name;
	}
	public FormFieldLinkage(String value, String name, String dyattr) {
		super();
		this.value = value;
		this.name = name;
		this.dyattr = dyattr;
	}
	@Override
	public String toString() {
		return "FormFieldLinkage [value=" + value + ", name=" + name + ", dyattr=" + dyattr + "]";
	}
	
	
	
}
