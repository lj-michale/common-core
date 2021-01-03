package common.core.site.view.button;

public class OpenButton extends Button {

	public OpenButton(String title, String url) {
		super(title, url);
		this.setType(ButtonType.OPEN);
	}

}
