package common.core.web.api.view.method;

import org.hibernate.validator.constraints.NotBlank;

public class ApiMethodInvokeObjectViewForm {

	@NotBlank
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
