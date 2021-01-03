/**
 * 
 */
package common.core.site.view;

import java.util.Locale;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.RequestContextUtils;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.ApplicationContextUtil;
import common.core.web.context.RequestContext;

/**
 * @author zhengmuwang
 *
 */
public class HtmlRestResult extends BaseRestResult<String> {
	private static final long serialVersionUID = -637338841424926724L;
	private static final Logger LOGGER = LoggerFactory.getLogger(HtmlRestResult.class);

	public HtmlRestResult(String template) {
		super(template);
		buildHtml(template);
		this.setAction(BaseRestResult.ACTION_HTML);
	}

	private void buildHtml(String template) {
		this.setData(HtmlRestResult.buildHtmlFromTemplate(template));
	}

	public static String buildHtmlFromTemplate(String template) {
		return buildHtmlFromTemplate(template, null);
	}

	public static String buildHtmlFromTemplate(String template, Device device) {
		if (null != device) {
			RequestContext.setAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE, device);
		}
		try {
			LOGGER.debug("build temple:{}", template);
			HtmlRestResultHttpServletResponse htmlRestResultHttpServletResponse = new HtmlRestResultHttpServletResponse();
			DefaulteDeviceDelegatingViewResolver defaulteDeviceDelegatingViewResolver = ApplicationContextUtil.getBean(DefaulteDeviceDelegatingViewResolver.class);
			Locale locale = RequestContextUtils.getLocale(RequestContext.get());
			View view = defaulteDeviceDelegatingViewResolver.resolveViewName(template, locale);
			view.render(ViewContext.currentValues(), RequestContext.get(), htmlRestResultHttpServletResponse);
			LOGGER.debug("build temple success");
			return htmlRestResultHttpServletResponse.getResponseText();
		} catch (Exception e) {
			throw new ViewException(e.getMessage(), e);
		}
	}

	public static HtmlRestResult html(String template) {
		HtmlRestResult htmlRestResult = new HtmlRestResult(template);
		htmlRestResult.setAction(BaseRestResult.ACTION_HTML);
		return htmlRestResult;
	}

	// public static HtmlRestResult openHtmlFragment(String template) {
	// HtmlRestResult htmlRestResult = new HtmlRestResult(template);
	// htmlRestResult.setAction(BaseRestResult.ACTION_OPENHTMLFRAGMENT);
	// return htmlRestResult;
	// }

	public static HtmlRestResult popHtmlFragment(String template) {
		HtmlRestResult htmlRestResult = new HtmlRestResult(template);
		htmlRestResult.setAction(BaseRestResult.ACTION_POP_HTML_FRAGMENT);
		return htmlRestResult;
	}

	public static HtmlRestResult replaceHtml(String template) {
		HtmlRestResult htmlRestResult = new HtmlRestResult(template);
		htmlRestResult.setAction(HtmlRestResult.ACTION_REPLACE_HTML);
		return htmlRestResult;
	}
}
