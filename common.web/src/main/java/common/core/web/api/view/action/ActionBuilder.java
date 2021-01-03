package common.core.web.api.view.action;

public class ActionBuilder {

	public static Action buildInfoAction(String message) {
		return new Action(ActionTypes.INFO, message);
	}

	public static Action buildWarnAction(String message) {
		return new Action(ActionTypes.WARN, message);
	}

	public static Action buildCloseAction() {
		return new Action(ActionTypes.CLOSE);
	}

	public static Action buildReloadParentAction() {
		return new Action(ActionTypes.RELOAD_PARENT);
	}

	public static Action buildReloadAction() {
		return new Action(ActionTypes.RELOAD);
	}

	public static Action buildReloadAction(String select) {
		return new Action(ActionTypes.RELOAD, select);
	}

	public static Action buildRedirectAction(String api) {
		return new Action(ActionTypes.REDIRECT, api);
	}

	public static Action buildOpenAction(String api) {
		return new Action(ActionTypes.OPEN, api);
	}
	
	public static Action buildBackAction() {
		return new Action(ActionTypes.BACK);
	}

}
