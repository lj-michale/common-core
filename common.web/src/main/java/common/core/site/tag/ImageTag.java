package common.core.site.tag;

import java.io.IOException;
import java.util.Map;

import org.springframework.util.StringUtils;

import common.core.common.assertion.util.AssertErrorUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ImageTag extends CdnTag {

	public final static String PARAM_NAME_PLACEHOLDER = "placeholder";
	private String placeHolderClass = "place-holder";
	private String placeHolderImg;
	private static final String HREF_KEY = "src";

	public ImageTag() {
		super();
	}

	public ImageTag(String placeHolderClass, String placeHolderImg) {
		super();
		this.placeHolderClass = placeHolderClass;
		this.placeHolderImg = placeHolderImg;
	}

	public String getPlaceHolderClass() {
		return placeHolderClass;
	}

	public void setPlaceHolderClass(String placeHolderClass) {
		this.placeHolderClass = placeHolderClass;
	}

	public String getPlaceHolderImg() {
		return placeHolderImg;
	}

	public void setPlaceHolderImg(String placeHolderImg) {
		this.placeHolderImg = placeHolderImg;
	}

	@SuppressWarnings("rawtypes")
	private String getPlaceholderClass(Map params) {
		return placeHolderClass;
	}

	@SuppressWarnings("rawtypes")
	protected String getPlaceHolder(Map params) {
		String placeHolder = getStringParam(params, ImageTag.PARAM_NAME_PLACEHOLDER);
		return StringUtils.hasText(placeHolder) ? placeHolder : this.placeHolderImg;
	}

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3) throws TemplateException, IOException {
		env.getOut().write(buildBodyData(params));
	}

	@SuppressWarnings("rawtypes")
	protected String buildBodyData(Map params) {
		AssertErrorUtils.assertNull(params.get("value"), "Please change image tag value to src");
		String url = this.buildCdnUrl(getRequiredStringParam(params, ImageTag.HREF_KEY));
		StringBuffer data = new StringBuffer();
		String placeHoderUrl = this.buildCdnUrl(this.getPlaceHolder(params));
		if (StringUtils.hasText(placeHoderUrl)) {
			String temp = url;
			url = placeHoderUrl;
			placeHoderUrl = temp;
		}
		data.append("<img src=\"");
		data.append(url);
		data.append("\"");
		String classValue = null;
		for (Object key : params.keySet()) {
			if (ImageTag.HREF_KEY.equals(key) || "placeholder".equals(key))
				continue;
			String value = getRequiredStringParam(params, (String) key);
			if (null == value)
				continue;
			if ("class".equals(key)) {
				classValue = value;
				continue;
			}
			data.append(" ");
			data.append((String) key);
			data.append("=\"");
			data.append(value);
			data.append("\"");
		}

		if (StringUtils.hasText(placeHoderUrl)) {
			data.append(" data-placeholder=\"");
			data.append(placeHoderUrl);
			data.append("\"");
		}

		if (StringUtils.hasText(classValue) || StringUtils.hasText(placeHoderUrl)) {
			data.append(" class=\"");
			boolean hasClass = false;
			if (StringUtils.hasText(classValue)) {
				data.append(classValue);
				hasClass = true;
			}
			if (StringUtils.hasText(placeHoderUrl)) {
				if (hasClass)
					data.append(" ");
				data.append(this.getPlaceholderClass(params));
			}
			data.append("\"");
		}

		data.append(">");
		return data.toString();
	}
}
