package common.core.site.view.button;

public class Button {
	private ButtonType type;
	private String title;
	private String url;
	private ShowButtonFilter showButtonFilter;
	private String permissionCode;
	private String confirmMessage;

	public Button(String title, String url) {
		super();
		this.title = title;
		this.url = url;
	}

	public Button(String title, String url, String permissionCode) {
		super();
		this.title = title;
		this.url = url;
		this.permissionCode = permissionCode;
	}

	public Button(ButtonType type, String title, String url) {
		super();
		this.type = type;
		this.title = title;
		this.url = url;
	}

	public ButtonType getType() {
		return type;
	}

	public void setType(ButtonType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean showButton(Object param) {
		return null == showButtonFilter ? true : showButtonFilter.filter(param);
	}

	public ShowButtonFilter getShowButtonFilter() {
		return showButtonFilter;
	}

	public void setShowButtonFilter(ShowButtonFilter showButtonFilter) {
		this.showButtonFilter = showButtonFilter;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getConfirmMessage() {
		return confirmMessage;
	}

	public void setConfirmMessage(String confirmMessage) {
		this.confirmMessage = confirmMessage;
	}

}
