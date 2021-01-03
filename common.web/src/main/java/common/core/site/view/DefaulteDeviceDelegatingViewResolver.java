package common.core.site.view;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.mobile.device.site.SitePreferenceUtils;
import org.springframework.mobile.device.util.ResolverUtils;
import org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.site.cookie.CookieDevice;
import common.core.web.context.RequestContext;

public class DefaulteDeviceDelegatingViewResolver extends LiteDeviceDelegatingViewResolver {
	private final Logger logger = LoggerFactory.getLogger(DefaulteDeviceDelegatingViewResolver.class);
	private ViewResolver delegate;
	private boolean autoSelectTemplate = true;

	public DefaulteDeviceDelegatingViewResolver(ViewResolver delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	@Override
	public String getDeviceViewNameInternal(String viewName) {
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		Assert.isInstanceOf(ServletRequestAttributes.class, attrs);
		HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
		Device device = DeviceUtils.getCurrentDevice(request);
		SitePreference sitePreference = SitePreferenceUtils.getCurrentSitePreference(request);
		String resolvedViewName = viewName;
		if (ResolverUtils.isNormal(device, sitePreference)) {
			resolvedViewName = getNormalPrefix() + viewName + getNormalSuffix();
		} else if (ResolverUtils.isMobile(device, sitePreference)) {
			resolvedViewName = getMobilePrefix() + viewName + getMobileSuffix();
		} else if (ResolverUtils.isTablet(device, sitePreference)) {
			resolvedViewName = getTabletPrefix() + viewName + getTabletSuffix();
		}

		// MOBILE-63 "redirect:/" and "forward:/" can result in the view name
		// containing multiple trailing slashes
		return this.stripTrailingSlash(resolvedViewName);
	}

	private String stripTrailingSlash(String viewName) {
		if (viewName.endsWith("//")) {
			return viewName.substring(0, viewName.length() - 1);
		}
		return viewName;
	}

	@Override
	public String getDeviceViewName(String viewName) {
		return super.getDeviceViewName(viewName);
	}

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		View view = delegate.resolveViewName(getDeviceViewName(viewName), locale);
		AssertErrorUtils.assertNotNull(view, "can't load template:{}", viewName);
		if (this.autoSelectTemplate && view == null) {
			Object backupDevice = RequestContext.getAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE);
			if (!CookieDevice.NORMAL_INSTANCE.equals(RequestContext.getAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE))) {
				RequestContext.setAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE, CookieDevice.NORMAL_INSTANCE);
				view = delegate.resolveViewName(getDeviceViewName(viewName), locale);
			}
			if (view == null && !CookieDevice.MOBILE_INSTANCE.equals(RequestContext.getAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE))) {
				RequestContext.setAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE, CookieDevice.MOBILE_INSTANCE);
				view = delegate.resolveViewName(getDeviceViewName(viewName), locale);
			}
			if (view == null && !CookieDevice.TABLET_INSTANCE.equals(RequestContext.getAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE))) {
				RequestContext.setAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE, CookieDevice.TABLET_INSTANCE);
				view = delegate.resolveViewName(getDeviceViewName(viewName), locale);
			}
			RequestContext.setAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE, backupDevice);
		}
		if (this.getEnableFallback() && view == null) {
			view = delegate.resolveViewName(viewName, locale);
		}
		logger.debug("Resolved View: " + view.toString());
		return view;
	}

}
