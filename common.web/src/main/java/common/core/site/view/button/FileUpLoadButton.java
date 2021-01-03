package common.core.site.view.button;

public class FileUpLoadButton extends Button {

	public FileUpLoadButton(String title, String url) {
		super(title, url);
		this.setType(ButtonType.FILE_UPLOAD);
	}

}
