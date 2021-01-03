package common.core.web.api.view.form;

public class FormFieldItem {

	private String value;
	private String name;
	private String rate;
	private String linkage;
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
    
	
	public String getLinkage() {
		return linkage;
	}

	public void setLinkage(String linkage) {
		this.linkage = linkage;
	}

	public FormFieldItem(String value, String name) {
		super();
		this.value = value;
		this.name = name;
	}
    
	public FormFieldItem(String value, String name, String rate) {
		super();
		this.value = value;
		this.name = name;
		this.rate = rate;
	}
    
	
	public FormFieldItem(String value, String name, String rate, String linkage) {
		super();
		this.value = value;
		this.name = name;
		this.rate = rate;
		this.linkage = linkage;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "FormFieldItem [value=" + value + ", name=" + name + ", linkage=" + linkage + "]";
	}

    

	
}