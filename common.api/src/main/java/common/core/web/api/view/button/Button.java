package common.core.web.api.view.button;

public class Button {

	private String title;

	private String action;
	
	private String confirmMessage;
	
	private String confirmTitle;
	
	public String getTitle() {
		return title;
	}

	public Button setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getAction() {
		return action;
	}

	public Button setAction(String action) {
		this.action = action;
		return this;
	}

	public Button(String title, String action) {
		super();
		this.title = title;
		this.action = action;
	}

	public Button() {
		super();
	}

	public String getConfirmMessage() {
		return confirmMessage;
	}

	public Button setConfirmMessage(String confirmMessage) {
		this.confirmMessage = confirmMessage;
		return this;
	}

	public String getConfirmTitle() {
		return confirmTitle;
	}

	public Button setConfirmTitle(String confirmTitle) {
		this.confirmTitle = confirmTitle;
		return this;
	}

	@Override
	public String toString() {
		return "Button [title=" + title + ", action=" + action + ", confirmMessage=" + confirmMessage
				+ ", confirmTitle=" + confirmTitle + "]";
	}
}
