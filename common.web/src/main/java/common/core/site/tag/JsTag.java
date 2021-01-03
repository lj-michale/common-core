package common.core.site.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import common.core.common.assertion.util.AssertErrorUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class JsTag extends VersionCdnTag {

	private static final String SPLIT_CHAR = ";";
	private static final String HREF_KEY = "src";
	private static final String CHARSET_KEY = "charset";

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
		if (null != templateDirectiveBody) {
			buildJsBlock(env, templateDirectiveBody);
		} else {
			buildJsLink(env, params);
		}
	}

	private void buildJsLink(Environment env, @SuppressWarnings("rawtypes") Map params) throws TemplateModelException, IOException {
		AssertErrorUtils.assertNull(params.get("value"), "Please change js tag value to src");
		String value = getRequiredStringParam(params, JsTag.HREF_KEY);
		String attributes = buildAttributes(params);
		for (String url : value.trim().split(JsTag.SPLIT_CHAR)) {
			JsWriter jsWriter = new JsLink(this.buildCdnUrl(url), attributes);
			write(env, jsWriter);
		}
	}

	private String buildAttributes(@SuppressWarnings("rawtypes") Map params) {
		StringBuffer attributes = new StringBuffer();
		boolean hasCharset = false;
		for (Object key : params.keySet()) {
			if (JsTag.HREF_KEY.equals(key))
				continue;
			if(CHARSET_KEY.equals(key)){
				hasCharset = true;
			}
			attributes.append(" ").append(key).append("=\"").append(JsTag.getRequiredStringParam(params, (String) key)).append("\"");
		}
		//如果没有设置字符集，加入默认字符集
		if(!hasCharset){
			attributes.append(" ").append(CHARSET_KEY).append("=\"").append("UTF-8").append("\"");
		}
		return attributes.toString();
	}

	private void buildJsBlock(Environment env, TemplateDirectiveBody templateDirectiveBody) throws IOException, TemplateException {
		Writer writer = new StringWriter();
		templateDirectiveBody.render(writer);
		JsWriter jsWriter = new JsBlock(writer.toString());
		write(env, jsWriter);
	}

	private void write(Environment env, JsWriter jsWriter) throws IOException {
		JsRenderTag.getJsWriters(true).add(jsWriter);
	}

}
