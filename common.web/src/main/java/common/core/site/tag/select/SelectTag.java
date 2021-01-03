package common.core.site.tag.select;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.ApplicationContextUtil;
import common.core.common.util.StringEscapeUtil;
import common.core.common.util.StringUtil;
import common.core.site.tag.TagSupport;
import common.core.site.view.ViewContext;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class SelectTag extends TagSupport {
	private SelectDataService selectDataService;

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3) throws TemplateException, IOException {
		Writer out = env.getOut();
		String body = buildSelect(env, params);
		if (null != body)
			out.write(body);
	}

	@SuppressWarnings("unchecked")
	protected String buildSelect(Environment env, @SuppressWarnings("rawtypes") Map params) throws IOException {
		List<Object[]> dataSouce = null;
		String dataSourceName = getStringParam(params, "dataSouce");
		if (StringUtil.hasText(dataSourceName)) {
			dataSouce = (List<Object[]>) ViewContext.get(dataSourceName);
			if (null == dataSouce) {
				dataSouce = new ArrayList<Object[]>(0);
			}
		} else {
			if (null == selectDataService) {
				selectDataService = ApplicationContextUtil.getBean(SelectDataService.class);
				AssertErrorUtils.assertTrue(null != selectDataService, "please put selectDataService to ApplicationContex");
			}
			String dataSouceType = getRequiredStringParam(params, "dataSouceType");
			String dataSouceFrom = getStringParam(params, "dataSouceFrom");
			dataSouce = selectDataService.getDataSouce(dataSouceType, dataSouceFrom);
		}

		AssertErrorUtils.assertTrue(null != dataSouce, "can't load dataSouce from ViewContext");
		String renderonly = getStringParam(params, "renderonly");
		String type = getStringParam(params, "type");
		if ("true".equals(renderonly) && !"checkbox".equals(type)) {
			return buildRenderonly(dataSouce, params);
		}

		if ("radio".equals(type)) {
			return buildRadioBody(params, dataSouce);
		} else if ("checkbox".equals(type)) {
			return buildCheckboxBody(params, dataSouce);
		} else {
			return buildSelectBody(params, dataSouce);
		}

	}

	private String buildSelectBody(@SuppressWarnings("rawtypes") Map params, List<Object[]> dataSouce) {
		@SuppressWarnings("rawtypes")
		Iterator items = params.entrySet().iterator();
		StringBuffer body = new StringBuffer();
		body.append("<select ");
		String value = null;
		while (items.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry item = (Entry) items.next();
			if ("dataSouce".equals(item.getKey()) || "dataSouceType".equals(item.getKey()) || "dataSouceFrom".equals(item.getKey()) || null == item.getValue()) {
				continue;
			}
			if ("value".equals(item.getKey())) {
				value = item.getValue().toString();
				continue;
			}
			body.append(item.getKey()).append("=\"").append(item.getValue()).append("\" ");
		}
		body.append(">");
		body.append("<option value=\"\">请选择</option>");
		for (Object[] objs : dataSouce) {
			body.append("<option value=\"").append(objs[0].toString()).append("\"");
			if (objs[0].toString().equals(value)) {
				body.append(" selected=\"selected\"");
			}
			body.append(">").append(StringEscapeUtil.escapeHtml4(String.valueOf(objs[1]))).append("</option>");
		}
		body.append("</select>");

		return body.toString();
	}

	private String buildRadioBody(@SuppressWarnings("rawtypes") Map params, List<Object[]> dataSouce) {
		String value = getStringParam(params, "value");
		String name = getStringParam(params, "name");
		StringBuffer body = new StringBuffer();
		for (Object[] objs : dataSouce) {
			body.append("<label><input  type=\"radio\" name=\"").append(name).append("\" value=\"").append(objs[0].toString()).append("\" ");
			if (objs[0].toString().equals(value)) {
				body.append(" checked=\"checked\" ");
			}
			body.append(" >").append(StringEscapeUtil.escapeHtml4(String.valueOf(objs[1]))).append("</label>");
		}
		return body.toString();
	}

	@SuppressWarnings("rawtypes")
	private String buildCheckboxBody(Map params, List<Object[]> dataSouce) {
		String name = getStringParam(params, "name");
		String renderonly = getStringParam(params, "renderonly");
		StringBuffer body = new StringBuffer();
		Object value = params.get("value");
		String[] values = {};
		if (null != value) {
			if (value instanceof SimpleScalar) {
				value = value.toString();
			}
			if (value instanceof String) {
				values = StringUtil.split((String) value);
			} else if (value instanceof String[]) {
				values = (String[]) value;
			} else if (value instanceof List) {
				List<?> items = (List) value;
				values = new String[items.size()];
				for (int i = 0; i < values.length; i++) {
					values[i] = items.get(i).toString();
				}
			}
		}
		for (Object[] objs : dataSouce) {
			body.append("<label><input  type=\"checkbox\" name=\"").append(name).append("\" value=\"").append(objs[0].toString()).append("\" ");
			for (String valueItem : values) {
				if (valueItem.equals(objs[0].toString())) {
					body.append(" checked=\"checked\" ");
					continue;
				}
			}
			if ("true".equals(renderonly)) {
				body.append(" disabled=\"disabled\" ");
			}
			body.append(" >").append(StringEscapeUtil.escapeHtml4(String.valueOf(objs[1]))).append("</label>");
		}
		return body.toString();
	}

	private String buildRenderonly(List<Object[]> dataSouce, @SuppressWarnings("rawtypes") Map params) {
		@SuppressWarnings("rawtypes")
		Iterator items = params.entrySet().iterator();
		String value = null;
		while (items.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry item = (Entry) items.next();
			if ("value".equals(item.getKey().toString())) {
				value = item.getValue().toString();
				break;
			}
		}
		AssertErrorUtils.assertNotNull(value, "please put attrute value to select tag when open renderonly");
		for (Object[] objs : dataSouce) {
			if (objs[0].toString().equals(value)) {
				return StringEscapeUtil.escapeHtml4(objs[1].toString());
			}
		}
		return null;
	}

}
