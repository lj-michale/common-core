package common.core.web.api.view.views;

import java.util.List;

import common.core.web.api.view.button.Button;

@ApiResponsePlugin
public class ButtonView {

	private String title;

	private String type;
	
	private String imageNameOrUrl;//按钮选图或者按钮URL

	private List<Button> buttonFields;
	
	private List<Button> downButtons;

	public String getTitle() {
		return title;
	}

	public ButtonView setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getType() {
		return type;
	}

	public ButtonView setType(String type) {
		this.type = type;
		return this;
	}

	public List<Button> getButtonFields() {
		return buttonFields;
	}

	public ButtonView setButtonFields(List<Button> buttonFields) {
		this.buttonFields = buttonFields;
		return this;
	}

	public List<Button> getDownButtons() {
		return downButtons;
	}

	public ButtonView setDownButtons(List<Button> downButtons) {
		this.downButtons = downButtons;
		return this;
	}

	public String getImageNameOrUrl() {
		return imageNameOrUrl;
	}

	public ButtonView setImageNameOrUrl(String imageNameOrUrl) {
		this.imageNameOrUrl = imageNameOrUrl;
		return this;
	}

	@Override
	public String toString() {
		return "ButtonView [title=" + title + ", type=" + type + ", imageNameOrUrl=" + imageNameOrUrl
				+ ", buttonFields=" + buttonFields + ", downButtons=" + downButtons + "]";
	}
}
