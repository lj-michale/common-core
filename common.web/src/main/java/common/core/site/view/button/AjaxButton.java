package common.core.site.view.button;

public class AjaxButton extends Button {

	public AjaxButton(String title, String url) {
		super(title, url);
		this.setType(ButtonType.AJAX);
	}

}
