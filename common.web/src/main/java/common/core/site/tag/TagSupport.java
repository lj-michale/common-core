package common.core.site.tag;

import java.util.HashMap;
import java.util.Map;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.StringUtil;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;

public abstract class TagSupport implements TemplateDirectiveModel {
	void assertNoBody(TemplateDirectiveBody body) {
		if (body != null)
			throw new TemplateExceptoin(String.format("%s directive does not allow body", getClass().getSimpleName()));
	}

	void assertHasBody(TemplateDirectiveBody body) {
		if (body == null)
			throw new TemplateExceptoin(String.format("%s directive should have body", getClass().getSimpleName()));
	}

	@SuppressWarnings("rawtypes")
	protected static String getRequiredStringParam(Map params, String key) {
		Object value = params.get(key);
		AssertErrorUtils.assertNotNull(value, "{} param is required", key);
		if (value instanceof String)
			return (String) value;
		if (!(value instanceof SimpleScalar))
			throw new TemplateExceptoin(String.format("%s param is required by %s, and must be string", key, TagSupport.class.getSimpleName()));
		return ((SimpleScalar) value).getAsString();
	}

	@SuppressWarnings("rawtypes")
	protected static String getStringParam(Map params, String key) {
		Object value = params.get(key);
		if (value == null)
			return null;
		return getRequiredStringParam(params, key);
	}

	protected static Map<String, String> buildAttributeValueMap(@SuppressWarnings("rawtypes") Map params, String defaultClass, String defaultId) {
		Map<String, String> attrValMap = new HashMap<>();
		for (Object key : params.keySet()) {
			String attr = key.toString();
			String val = HtmlEditorTag.getStringParam(params, attr);
			if (null == val)
				val = "";
			attrValMap.put(attr, val);
		}

		if (StringUtil.hasText(defaultClass)) {
			String clz = attrValMap.get("class");
			if (null == clz) {
				clz = "";
			}
			attrValMap.put("class", clz + " " + defaultClass);
		}

		if (StringUtil.hasText(defaultId)) {
			if (StringUtil.isEmpty(attrValMap.get("id"))) {
				attrValMap.put("id", defaultId);
			}
		}

		return attrValMap;
	}

	protected static String buildAttributeHtml(Map<String, String> attributeValueMap) {
		StringBuffer html = new StringBuffer();
		for (Map.Entry<String, String> item : attributeValueMap.entrySet()) {
			html.append(" ").append(item.getKey()).append("=\"").append(null == item.getValue() ? "" : item.getValue()).append("\"");
		}

		return html.toString();
	}
}
