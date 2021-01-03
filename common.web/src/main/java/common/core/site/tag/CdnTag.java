package common.core.site.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import common.core.app.context.ConfigContext;
import common.core.common.util.StringUtil;
import common.core.common.util.UrlUtil;
import common.core.web.context.RequestContext;
import common.core.web.context.WebSetting;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CdnTag extends TagSupport {

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3) throws TemplateException, IOException {
		String url = getRequiredStringParam(params, "value");
		url = buildCdnUrl(url);
		String assign = getStringParam(params, "assign");
		if (StringUtil.hasText(assign)) {
			env.setVariable(assign, new SimpleScalar(url));
			return;
		}
		Writer output = env.getOut();
		output.write(url);
	}

	public String buildCdnUrl(String url) {
		return CdnTag.buildUrl(url);
	}

	public static String buildUrl(String url) {
		if (null == url)
			return null;
		url = ConfigContext.getStringValue(url, url);
		String[] cdns = RequestContext.isSecure() ? WebSetting.get().getHttpsCdns() : WebSetting.get().getHttpCdns();
		if (UrlUtil.hasScheme(url) || null == cdns || cdns.length == 0)
			return url;
		int seq = url.hashCode();
		if (seq != Integer.MIN_VALUE) {
			seq = Math.abs(seq) % cdns.length;
		} else {
			seq = (Integer.MIN_VALUE % cdns.length) + cdns.length;
		}
		StringBuffer cdnUrl = new StringBuffer(cdns[seq]).append(url);
		return cdnUrl.toString();
	}
}
