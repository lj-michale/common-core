package common.core.web.api.view.views;

import java.util.List;

import common.core.web.api.view.form.FormFieldView;

@ApiResponsePlugin
public class PluralViews {
    
	private String title;
	
	private List<FormFieldView> lists;
	
	private List<ButtonView> buttonViews;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<FormFieldView> getLists() {
		return lists;
	}

	public void setLists(List<FormFieldView> lists) {
		this.lists = lists;
	}
	

	public List<ButtonView> getButtonViews() {
		return buttonViews;
	}

	public void setButtonViews(List<ButtonView> buttonViews) {
		this.buttonViews = buttonViews;
	}

	@Override
	public String toString() {
		return "PluralViews [title=" + title + ", lists=" + lists + ", buttonViews=" + buttonViews + "]";
	}
}
