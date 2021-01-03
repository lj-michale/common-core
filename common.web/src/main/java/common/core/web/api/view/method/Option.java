package common.core.web.api.view.method;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import javax.validation.constraints.Pattern;
import common.core.web.api.ApiOptionDoc;
import common.core.common.util.StringUtil;

public class Option {

	private String name;
	private Type type;
	private String description;
	private String demoValue;
	private String demoRule;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getDemoValue() {
		return demoValue;
	}

	public void setDemoValue(String demoValue) {
		this.demoValue = demoValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Option(String name, Field field) {
		super();
		this.name = name;
		this.type = field.getGenericType();
		ApiOptionDoc apiOptionDoc = field.getAnnotation(ApiOptionDoc.class);
		Pattern pattern = field.getAnnotation(Pattern.class);
		if (null != apiOptionDoc) {
			this.demoValue = apiOptionDoc.demoValue();
			this.description = apiOptionDoc.description();
			this.demoRule = apiOptionDoc.demoRule();
		} else {
			this.demoValue = "";
			this.description = "";
			this.demoRule = "";
		}
		if (StringUtil.isEmpty(this.demoRule)) {
			if (null != pattern) {
				this.demoRule = pattern.message() + "(" + pattern.regexp() + ")";
			}
		}
	}

	protected Option() {
		super();
	}

	public String getDemoRule() {
		return demoRule;
	}

	public void setDemoRule(String demoRule) {
		this.demoRule = demoRule;
	}

	@Override
	public String toString() {
		return "Option [name=" + name + ", type=" + type + ", description=" + description + ", demoValue=" + demoValue
				+ ", demoRule=" + demoRule + "]";
	}

}
