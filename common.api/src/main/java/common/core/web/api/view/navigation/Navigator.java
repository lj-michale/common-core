package common.core.web.api.view.navigation;

import java.util.ArrayList;
import java.util.List;

public class Navigator {
	private String title;

	private String action;

	private String icon;

	private List<Navigator> subNavigators;

	public String getTitle() {
		return title;
	}

	public Navigator setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getAction() {
		return action;
	}

	public String getIcon() {
		return icon;
	}

	public Navigator setIcon(String icon) {
		this.icon = icon;
		return this;
	}

	public Navigator setAction(String action) {
		this.action = action;
		return this;
	}

	public List<Navigator> getSubNavigators() {
		return subNavigators;
	}

	public Navigator setSubNavigators(List<Navigator> subNavigators) {
		this.subNavigators = subNavigators;
		return this;
	}

	public Navigator addSubNavigator(Navigator subNavigator) {
		if (null == this.subNavigators) {
			this.subNavigators = new ArrayList<>();
		}
		this.subNavigators.add(subNavigator);
		return this;
	}

	public Navigator(String title, String action) {
		super();
		this.title = title;
		this.action = action;
	}

	public Navigator() {
		super();
	}

	@Override
	public String toString() {
		return "Navigator [title=" + title + ", action=" + action + ", icon=" + icon + ", subNavigators=" + subNavigators + "]";
	}

}
