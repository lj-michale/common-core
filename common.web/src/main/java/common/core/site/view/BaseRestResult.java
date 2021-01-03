package common.core.site.view;

import java.io.Serializable;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class BaseRestResult<T> implements Serializable {
	private static final long serialVersionUID = 3077359822203188781L;
	public static final String ACTION_SUCCESS = "success";
	public static final String ACTION_INFO = "info";
	public static final String ACTION_WARN = "warn";
	public static final String ACTION_ERROR = "error";
	public static final String ACTION_CONFIRM = "confirm";
	public static final String ACTION_REDIRECT = "redirect";
	public static final String ACTION_TOPREDIRECT = "topRedirect";
	public static final String ACTION_REFRESH = "refresh";
	public static final String ACTION_TOPREFRESH = "topRefresh";
	public static final String ACTION_EVAL = "eval";
	public static final String ACTION_FILEDSERROR = "filedsError";
	public static final String ACTION_OPENHTML = "popWin";
	public static final String ACTION_OPENIFRAME = "openIframe";
	public static final String ACTION_RENDER = "render";
	public static final String ACTION_OPENNEWPAGE = "redirectNewPage";
	public static final String ACTION_HTML = "html";
	public static final String ACTION_REPLACE_HTML = "replaceHtml";
	// public static final String ACTION_OPENHTMLFRAGMENT = "openHtml";
	public static final String ACTION_POP_HTML_FRAGMENT = "popHtmlFragment";
	public static final String ACTION_POP_HTML_FRAGMENT_URL = "popHtmlFragmentUrl";
	public static final String ACTION_CLOSEST_RELOAD = "closestReload";
	public static final String ACTION_CLOSEST_CLOSEACTIONDIALOG = "closeActionDialog";

	private String action = BaseRestResult.ACTION_SUCCESS;
	private String message;
	private T data;

	public static MessageRestResult buildSuccessResult() {
		MessageRestResult messageRestResult = new MessageRestResult(BaseRestResult.ACTION_SUCCESS);
		messageRestResult.setAction(BaseRestResult.ACTION_SUCCESS);
		return messageRestResult;
	}

	public static MessageRestResult buildClosestReloadResult() {
		MessageRestResult messageRestResult = new MessageRestResult(BaseRestResult.ACTION_CLOSEST_RELOAD);
		messageRestResult.setAction(BaseRestResult.ACTION_CLOSEST_RELOAD);
		return messageRestResult;
	}

	public static MessageRestResult buildCloseActionDialogResult() {
		MessageRestResult messageRestResult = new MessageRestResult(BaseRestResult.ACTION_CLOSEST_CLOSEACTIONDIALOG);
		messageRestResult.setAction(BaseRestResult.ACTION_CLOSEST_CLOSEACTIONDIALOG);
		return messageRestResult;
	}

	public static <T> BaseRestResult<T> buildRestResult(T data) {
		return new BaseRestResult<T>(data);
	}

	public static BaseRestResult<Render[]> render(Render[] renders) {
		BaseRestResult<Render[]> result = new BaseRestResult<Render[]>(renders);
		result.setAction(BaseRestResult.ACTION_RENDER);
		return result;
	}

	public static MessageRestResult info(String message) {
		MessageRestResult messageRestResult = new MessageRestResult(message);
		messageRestResult.setAction(BaseRestResult.ACTION_INFO);
		return messageRestResult;
	}

	public static MessageRestResult warn(String message) {
		MessageRestResult messageRestResult = new MessageRestResult(message);
		messageRestResult.setAction(BaseRestResult.ACTION_WARN);
		return messageRestResult;
	}

	public static MessageRestResult error(String message) {
		MessageRestResult messageRestResult = new MessageRestResult(message);
		messageRestResult.setAction(BaseRestResult.ACTION_ERROR);
		return messageRestResult;
	}

	public static MessageRestResult confirm(String message) {
		MessageRestResult messageRestResult = new MessageRestResult(message);
		messageRestResult.setAction(BaseRestResult.ACTION_CONFIRM);
		return messageRestResult;
	}

	public static MessageRestResult redirect(String url) {
		MessageRestResult messageRestResult = new MessageRestResult(url);
		messageRestResult.setAction(BaseRestResult.ACTION_REDIRECT);
		return messageRestResult;
	}

	public static MessageRestResult topRedirect(String url) {
		MessageRestResult messageRestResult = new MessageRestResult(url);
		messageRestResult.setAction(BaseRestResult.ACTION_TOPREDIRECT);
		return messageRestResult;
	}

	public static MessageRestResult openHtml(String url) {
		MessageRestResult messageRestResult = new MessageRestResult(url);
		messageRestResult.setAction(BaseRestResult.ACTION_OPENHTML);
		return messageRestResult;
	}

	public static MessageRestResult openIframe(String url) {
		MessageRestResult messageRestResult = new MessageRestResult(url);
		messageRestResult.setAction(BaseRestResult.ACTION_OPENIFRAME);
		return messageRestResult;
	}

	public static MessageRestResult openNewPage(String url) {
		MessageRestResult messageRestResult = new MessageRestResult(url);
		messageRestResult.setAction(BaseRestResult.ACTION_OPENNEWPAGE);
		return messageRestResult;
	}

	public static MessageRestResult refresh() {
		MessageRestResult messageRestResult = new MessageRestResult("");
		messageRestResult.setAction(BaseRestResult.ACTION_REFRESH);
		return messageRestResult;
	}

	public static MessageRestResult topRefresh() {
		MessageRestResult messageRestResult = new MessageRestResult("");
		messageRestResult.setAction(BaseRestResult.ACTION_TOPREFRESH);
		return messageRestResult;
	}

	public static MessageRestResult eval(String eval) {
		MessageRestResult messageRestResult = new MessageRestResult(eval);
		messageRestResult.setAction(BaseRestResult.ACTION_EVAL);
		return messageRestResult;
	}

	public static MessageRestResult popHtmlFragmentUrl(String url) {
		MessageRestResult messageRestResult = new MessageRestResult(url);
		messageRestResult.setAction(BaseRestResult.ACTION_POP_HTML_FRAGMENT_URL);
		return messageRestResult;
	}

	public static BaseRestResult<FieldError[]> filedErrors(FieldError[] filedErrors) {
		BaseRestResult<FieldError[]> result = new BaseRestResult<FieldError[]>(filedErrors);
		result.setAction(BaseRestResult.ACTION_FILEDSERROR);
		return result;
	}

	public BaseRestResult(T data) {
		this.data = data;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
