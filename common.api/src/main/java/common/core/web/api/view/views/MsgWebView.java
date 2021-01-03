package common.core.web.api.view.views;

import java.util.List;

@ApiResponsePlugin
public class MsgWebView {

	private String title;

	private String msgActions;
	
	private List<ButtonView> buttonViews;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsgActions() {
		return msgActions;
	}

	public void setMsgActions(String msgActions) {
		this.msgActions = msgActions;
	}

	public List<ButtonView> getButtonViews() {
		return buttonViews;
	}

	public void setButtonViews(List<ButtonView> buttonViews) {
		this.buttonViews = buttonViews;
	}

	@Override
	public String toString() {
		return "MsgWebView [title=" + title + ", msgActions=" + msgActions + ", buttonViews=" + buttonViews + "]";
	}
	
}
