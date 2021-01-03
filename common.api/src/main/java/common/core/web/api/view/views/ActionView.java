package common.core.web.api.view.views;

import common.core.web.api.view.action.Action;

@ApiResponsePlugin
public class ActionView {

	private Action[] actions;

	public Action[] getActions() {
		return actions;
	}

	public void setActions(Action[] actions) {
		this.actions = actions;
	}

	public ActionView(Action[] actions) {
		super();
		this.actions = actions;
	}

	public ActionView() {
		super();
	}

}
