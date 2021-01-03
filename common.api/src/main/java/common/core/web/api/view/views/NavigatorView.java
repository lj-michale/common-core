package common.core.web.api.view.views;

import java.util.List;

import common.core.web.api.view.navigation.Navigator;

@ApiResponsePlugin
public class NavigatorView {

	private String title;

	private String type;

	private List<Navigator> navigators;
	
	private List<ButtonView> buttonViews;

	public String getTitle() {
		return title;
	}

	public NavigatorView setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getType() {
		return type;
	}

	public NavigatorView setType(String type) {
		this.type = type;
		return this;
	}

	public List<Navigator> getNavigators() {
		return navigators;
	}

	public NavigatorView setNavigators(List<Navigator> navigators) {
		this.navigators = navigators;
		return this;
	}

	public List<ButtonView> getButtonViews() {
		return buttonViews;
	}

	public void setButtonViews(List<ButtonView> buttonViews) {
		this.buttonViews = buttonViews;
	}

	@Override
	public String toString() {
		return "NavigatorView [title=" + title + ", type=" + type + ", navigators=" + navigators + ", buttonViews=" + buttonViews + "]";
	}

}
