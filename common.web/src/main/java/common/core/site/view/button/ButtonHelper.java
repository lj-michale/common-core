package common.core.site.view.button;

import java.util.HashMap;
import java.util.Map;

import common.core.app.context.ConfigContext;
import common.core.common.util.StringEscapeUtil;
import common.core.common.util.StringUtil;
import common.core.site.tag.CdnTag;
import common.core.site.tag.FileUploadTag;
import common.core.web.sso.User;
import common.core.web.sso.UserHolder;

public class ButtonHelper {
	public static Button createAjaxButton(String title, String url) {
		Button button = new Button(title, url);
		button.setType(ButtonType.AJAX);
		return button;
	}

	public static Button createAjaxButton(String title, String url, String permissionCode) {
		Button button = new Button(title, url, permissionCode);
		button.setType(ButtonType.AJAX);
		return button;
	}

	public static Button createRedirectButton(String title, String url) {
		Button button = new Button(title, url);
		button.setType(ButtonType.REDIRECT);
		return button;
	}

	public static Button createRedirectButton(String title, String url, String permissionCode) {
		Button button = new Button(title, url, permissionCode);
		button.setType(ButtonType.REDIRECT);
		return button;
	}

	public static Button createOpenButton(String title, String url) {
		Button button = new Button(title, url);
		button.setType(ButtonType.OPEN);
		return button;
	}

	public static Button createOpenButton(String title, String url, String permissionCode) {
		Button button = new Button(title, url, permissionCode);
		button.setType(ButtonType.OPEN);
		return button;
	}

	public static Button createFileUpLoadButton(String title, String url) {
		Button button = new Button(title, url);
		button.setType(ButtonType.FILE_UPLOAD);
		return button;
	}

	public static Button createAjaxConfirmButton(String title, String url) {
		Button button = new Button(title, url);
		button.setType(ButtonType.AJAX_CONFIRM);
		return button;
	}

	public static Button createAjaxConfirmButton(String title, String url, String permissionCode) {
		Button button = new Button(title, url, permissionCode);
		button.setType(ButtonType.AJAX_CONFIRM);
		return button;
	}

	public static Button createAjaxConfirmButton(String title, String url, String permissionCode, String confirmMessage) {
		Button button = new Button(title, url, permissionCode);
		button.setType(ButtonType.AJAX_CONFIRM);
		button.setConfirmMessage(confirmMessage);
		return button;
	}

	public static Button createButton(String title, String url, ButtonType buttonType) {
		Button button = new Button(title, url);
		button.setType(buttonType);
		return button;
	}

	public static Button createButton(String title, String url, ButtonType buttonType, ShowButtonFilter showButtonFilter) {
		Button button = new Button(title, url);
		button.setType(buttonType);
		button.setShowButtonFilter(showButtonFilter);
		return button;
	}

	public static boolean hasPermission(String permissionCode) {
		if (StringUtil.isEmpty(permissionCode))
			return true;
		User user = UserHolder.getUser();
		if (user.isSuperAdministrator()) {
			return true;
		}
		Boolean permission = user.getPermissionCodeMap().get(permissionCode);
		if (null != permission && permission) {
			return true;
		}
		return false;
	}

	public static String buildButtonHtml(Button button, String addClass) {
		if (!hasPermission(button.getPermissionCode())) {
			return "";
		}

		StringBuffer html = new StringBuffer();
		String title = StringEscapeUtil.escapeHtml4(button.getTitle());
		if (ButtonType.AJAX.equals(button.getType())) {
			html.append("<a  class=\"action-ajax-btn ").append(null == addClass ? "" : addClass).append("\" ");
			ButtonHelper.appendWaitImg(html);
			html.append(" data-url=\"").append(button.getUrl()).append("\" href=\"javascript:;\" >").append(title).append("</a>");
		} else if (ButtonType.AJAX_CONFIRM.equals(button.getType())) {
			html.append("<a  class=\"action-ajax-btn-confirm ").append(null == addClass ? "" : addClass).append("\" ");
			ButtonHelper.appendWaitImg(html);
			html.append(" data-url=\"").append(button.getUrl()).append("\" href=\"javascript:;\" >").append(title).append("</a>");
		} else if (ButtonType.REDIRECT.equals(button.getType())) {
			html.append("<a  class=\"").append(null == addClass ? "" : addClass).append("\" href=\"").append(button.getUrl()).append("\">").append(title).append("</a>");
		} else if (ButtonType.OPEN.equals(button.getType())) {
			html.append("<a  class=\"").append(null == addClass ? "" : addClass).append("\" href=\"").append(button.getUrl()).append("\"  target=\"_blank\">").append(title).append("</a>");
		} else if (ButtonType.FILE_UPLOAD.equals(button.getType())) {
			Map<String, String> params = new HashMap<>();
			params.put("url", button.getUrl());
			params.put("title", button.getTitle());
			html.append(FileUploadTag.buildBody(params));
		}
		return html.toString();
	}

	public static void appendWaitImg(StringBuffer html) {
		String image = ConfigContext.getStringValue("img.wait", null);
		if (StringUtil.hasText(image)) {
			html.append(" data-ajax-wait-img=\"").append(CdnTag.buildUrl(image)).append("\" ");
		}
	}

	public static String buildButtonHtml(Button button) {
		return buildButtonHtml(button, null);
	}

}
