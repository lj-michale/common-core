package common.core.site.view;

public class Render {
	private String target;
	private String value;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Render() {
		
	}
	
	public Render(String target, String value) {
		super();
		this.target = target;
		this.value = value;
	}

}
