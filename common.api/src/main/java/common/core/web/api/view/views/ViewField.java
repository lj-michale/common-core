package common.core.web.api.view.views;

import common.core.web.api.view.field.Field;

public class ViewField extends Field {
	/**
	 * 字段的标题
	 */
	private String title;

	public String getTitle() {
		return title;
	}

	public ViewField setTitle(String title) {
		this.title = title;
		return this;
	}

	public ViewField(String title, String value) {
		this.title = title;
		this.setValue(value);
	}

	@Override
	public ViewField setValue(String value) {
		return (ViewField) super.setValue(value);
	}

	@Override
	public ViewField setType(String type) {
		return (ViewField) super.setType(type);
	}

	@Override
	public ViewField setAttribute(String attribute) {
		return (ViewField) super.setAttribute(attribute);
	}

	@Override
	public ViewField addAttribute(String name, String value) {
		return (ViewField) super.addAttribute(name, value);
	}

	public ViewField() {
		super();
	}

	@Override
	public String toString() {
		return "ViewField [title=" + title + ", toString()=" + super.toString() + "]";
	}

}
