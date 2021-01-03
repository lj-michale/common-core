package common.core.site.view;

public class MessageRestResult extends BaseRestResult<String> {

	private static final long serialVersionUID = -5634031437731823924L;

	public MessageRestResult(String data) {
		super(data);
		setMessage(data);
	}

	public MessageRestResult andRefresh() {
		this.addCallBackupScript("window.location.reload()");
		return this;
	}

	public MessageRestResult andTopRefresh() {
		this.addCallBackupScript("top.location.reload()");
		return this;
	}

	public MessageRestResult andRedirect(String url) {
		this.addCallBackupScript("window.location.href = '" + url + "'");
		return this;
	}

	public MessageRestResult andTopRedirect(String url) {
		this.addCallBackupScript("top.location.href = '" + url + "'");
		return this;
	}

	public MessageRestResult andActionAjaxLoad(String url) {
		this.addCallBackupScript("$.ajaxActionLoad( '" + url + "')");
		return this;
	}

	public MessageRestResult andJquerySelectAjaxAction(String jquerySelect) {
		this.addCallBackupScript("$(\"" + jquerySelect + "\").ajaxAction()");
		return this;
	}

	public MessageRestResult andJquerySelectAjaxAction(String jquerySelect, String actionUrl) {
		this.addCallBackupScript("$(\"" + jquerySelect + "\").attr(\"data-url\",\"" + actionUrl + "\")");
		this.addCallBackupScript("$(\"" + jquerySelect + "\").ajaxAction()");
		return this;
	}

	public MessageRestResult andJqueryObjectAjaxAction(String jqueryObject) {
		this.addCallBackupScript(jqueryObject + ".ajaxAction()");
		return this;
	}

	public MessageRestResult andCloseActionDialog() {
		this.addCallBackupScript("$.actionDialog.close()");
		return this;
	}

	public MessageRestResult andEval(String script) {
		this.addCallBackupScript(script);
		return this;
	}

	public void addCallBackupScript(String script) {
		String data = this.getData();
		if (data == null || data.equals(this.getAction()) || data.equals(this.getMessage()))
			data = "";
		if (data.length() > 0) {
			data = data + ";";
		}
		data = data + script;
		this.setData(data);
	}

}
