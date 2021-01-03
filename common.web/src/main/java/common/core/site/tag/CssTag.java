package common.core.site.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import common.core.app.context.ConfigContext;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.site.view.ViewContext;
import common.core.web.context.RequestContext;
import common.core.web.context.WebSetting;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class CssTag extends VersionCdnTag {

	private static final String SPLIT_CHAR = ";";

	public static final String HREF_KEY = "href";

	public static final String APPEND_CSS = "appendCss";

	public static final String CSS_HREF_MERGE_KEY="css_href_merge";
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3) throws TemplateException, IOException {
		AssertErrorUtils.assertNull(params.get("value"), "Please change css tag value to href");
		String value = getRequiredStringParam(params, CssTag.HREF_KEY);
		String attributes = buildAttributes(params);
		List<String> appendCssList = new ArrayList<>(Arrays.asList(value.trim().split(CssTag.SPLIT_CHAR)));

		Object appendCssObject = ViewContext.get(CssTag.APPEND_CSS);
		if (null != appendCssObject) {
			String appendCss = appendCssObject.toString();
			if (StringUtils.hasText(appendCss)) {
				appendCssList.addAll(Arrays.asList(appendCss.trim().split(CssTag.SPLIT_CHAR)));
			}
		}

		render(env, attributes, appendCssList);
	}

	private String buildAttributes(@SuppressWarnings("rawtypes") Map params) {
		StringBuffer attributes = new StringBuffer();
		for (Object key : params.keySet()) {
			if (CssTag.HREF_KEY.equals(key))
				continue;
			attributes.append(" ").append(key).append("=\"").append(CssTag.getRequiredStringParam(params, (String) key)).append("\"");
		}
		return attributes.toString();
	}

	private void render(Environment env, String attributes, List<String> cssArray) throws IOException, TemplateModelException {
		String cssMerge=ConfigContext.getStringValue(CSS_HREF_MERGE_KEY,null);
		StringBuilder render = new StringBuilder("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
		render.append(this.getCdnDomain()).append("/??");
		Writer output = env.getOut();
		for (String url : cssArray) {
			String link = this.buildCdnUrl(url);
			
			//只有中山自己的css，才可以合并
			boolean canMerge = link.contains("eascs.com")||link.contains("zss.cn");
			
			if(!"open".equals(cssMerge)||!canMerge){
				output.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
				output.write(this.buildCdnUrl(url));
				output.write("\"");
				if (StringUtils.hasText(attributes)) {
					output.append(" ");
					output.append(attributes);
				}
				output.write("/>");
			}else{
				render.append(dealUrl(link.trim())).append(",");
			}
		}
		//",".equals(render.charAt(render.length()-1))
		//open打开且有数据则进行输出
		if("open".equals(cssMerge)){
			render.deleteCharAt(render.length()-1);
			render.append("\"/>");
			output.write(render.toString());
		}
	}
	private String dealUrl(String link){
		return link.substring(link.indexOf("/",7)+1);
	}
	protected String getCdnDomain() {
		//Random random = new Random(System.currentTimeMillis());
		String[] cdns = RequestContext.isSecure() ? WebSetting.get().getHttpsCdns() : WebSetting.get().getHttpCdns();
		return cdns[0];
		//return cdns[Math.abs(random.nextInt()) % cdns.length];
	}
}
