package common.core.site.view.button;

public class RedirectButton extends Button {

	public RedirectButton(String title, String url) {
		super(title, url);
		this.setType(ButtonType.REDIRECT);
	}

}
