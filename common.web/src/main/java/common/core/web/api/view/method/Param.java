package common.core.web.api.view.method;

import java.lang.reflect.Type;
import java.util.List;

public class Param {
	private Type type;
	private List<Option> options;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Param(Type type) {
		super();
		this.type = type;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "Param [type=" + type + ", options=" + options + "]";
	}

}
