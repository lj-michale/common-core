package common.core.site.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import common.core.common.util.StringEscapeUtil;
import common.core.common.util.StringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class FileUploadTag extends TagSupport {

	private static final String ACTION_UPLOADER_CLASS = " action-uploader-input ";
	private static final String SWF_FILE = "/widget/baidu-webuploader/dist/Uploader.swf";

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3) throws TemplateException, IOException {
		String body = buildBody(params);
		Writer out = env.getOut();
		out.write(body);
	}

	public static String buildBody(@SuppressWarnings("rawtypes") Map params) {
		@SuppressWarnings("rawtypes")
		Iterator items = params.entrySet().iterator();
		String title = getStringParam(params, "title");
		if (StringUtil.isEmpty(title))
			title = "选择文件...";
		StringBuffer body = new StringBuffer();
		body.append("<div ");

		String itemClass = ACTION_UPLOADER_CLASS;
		while (items.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry item = (Entry) items.next();
			if ("class".equals(item.getKey())) {
				itemClass = ACTION_UPLOADER_CLASS + (null == item.getValue() ? "" : item.getValue());
				continue;
			}
			body.append(item.getKey()).append("=\"").append(item.getValue()).append("\" ");
		}
		body.append(" class=\"").append(itemClass).append("\"");
		body.append(" swf=\"").append(CdnTag.buildUrl(SWF_FILE)).append("\" ");
		body.append(">").append(StringEscapeUtil.escapeHtml4(title)).append("</div>");
		return body.toString();
	}

}
