package common.core.site.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import common.core.app.context.ConfigContext;
import common.core.web.context.RequestContext;
import common.core.web.context.WebSetting;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class JsRenderTag extends TagSupport {

	public static final String JS_RENDER_DATA_KEY = JsRenderTag.class.getName();
	public static final String JS_SRC_MERGE_KEY="js_src_merge";
	public static final int MERGE_MAX = 15;
	@SuppressWarnings("unchecked")
	public static List<JsWriter> getJsWriters() {
		return (List<JsWriter>) RequestContext.getAttribute(JsRenderTag.JS_RENDER_DATA_KEY);
	}

	public static List<JsWriter> getJsWriters(boolean canCreate) {
		List<JsWriter> jsList = JsRenderTag.getJsWriters();
		if (null == jsList && canCreate) {
			jsList = new ArrayList<>();
			RequestContext.setAttribute(JsRenderTag.JS_RENDER_DATA_KEY, jsList);
		}
		return jsList;
	}

	public static void clearJsWriters() {
		RequestContext.removeAttribute(JsRenderTag.JS_RENDER_DATA_KEY);
	}

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
		assertNoBody(templateDirectiveBody);
		List<JsWriter> jsList = JsRenderTag.getJsWriters();
		if (null == jsList)
			return;
	
		List<StringBuilder> rs = initMergeList(jsList.size());
		Writer writer = env.getOut();
		String jsMerge=ConfigContext.getStringValue(JS_SRC_MERGE_KEY,null);
		StringBuilder render = null;
		String link = null;
		int count = 0;
		String attributes = null;
		for (JsWriter jsWriter : jsList) {
			if (jsWriter instanceof JsLink) {
				count++;
				link = ((JsLink) jsWriter).getLink();
				attributes = ((JsLink) jsWriter).getAttributes();
				//只有中山自己的js，才可以合并
				boolean canMerge = link.contains("eascs.com")||link.contains("zss.cn");
				if(!"open".equals(jsMerge)||!canMerge){
					JsLink jsLink = (JsLink) jsWriter;
					jsLink.write(writer);
				}else{
					render = rs.get(mergeCount(count)-1);
					render.append(dealLink(link.trim()));
					render.append(",");
				}
			} else if (jsWriter instanceof JsBlock) {
				JsBlock jsBlock = (JsBlock) jsWriter;
				jsBlock.write(writer);
			}
			
		}
		if("open".equals(jsMerge)){
			for(StringBuilder sb:rs){
				//以??结尾说明没有加入新的元素
				if(sb.lastIndexOf("??")==(sb.length()-2)){
					continue;
				}
				sb.deleteCharAt(sb.length()-1);
				if(StringUtils.isEmpty(attributes)){
					sb.append("\"></script>");
				}else{
					sb.append("\" ");
					sb.append(attributes);
					sb.append("></script>");
				}
				writer.write(sb.toString());
			}
		}
		JsRenderTag.clearJsWriters();
	}
	
	private String dealLink(String link){
		//link的格式为：http://cdn1.test.zss.cn/static-git/assets/js/html5shiv.js
		return link.substring(link.indexOf("/",7)+1);
	}
	protected String getCdnDomain() {
		//Random random = new Random(System.currentTimeMillis());
		String[] cdns = RequestContext.isSecure() ? WebSetting.get().getHttpsCdns() : WebSetting.get().getHttpCdns();
		return cdns[0];
		//return cdns[Math.abs(random.nextInt()) % cdns.length];
	}
	/**
	 * 计算合并的数量
	 * @param linkSize
	 * @return
	 */
	private int mergeCount(int linkSize){
		int multiple = linkSize/MERGE_MAX;
		int remainder = linkSize%MERGE_MAX;
		if(remainder==0){
			return multiple;
		}
		return multiple+1;
	}
	/**
	 * 初始化合并列表
	 * @param linkSize
	 * @return
	 */
	private List<StringBuilder> initMergeList(int linkSize){
		int size = mergeCount(linkSize);
		List<StringBuilder> rs = new ArrayList<StringBuilder>();
		for(int i=0;i<size;i++){
			StringBuilder render = new StringBuilder("<script type=\"text/javascript\" src=\"");
			render.append(this.getCdnDomain()).append("/??");
			rs.add(render);
		}
		return rs;
	}
	
}
