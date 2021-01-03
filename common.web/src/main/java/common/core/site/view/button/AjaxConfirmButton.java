package common.core.site.view.button;

public class AjaxConfirmButton extends Button {

	public AjaxConfirmButton(String title, String url) {
		super(title, url);
		this.setType(ButtonType.AJAX_CONFIRM);
	}

}
