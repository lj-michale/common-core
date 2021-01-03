package common.core.site.view;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceType;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.util.StringUtils;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.site.cookie.CookieDevice;
import common.core.site.cookie.CookieSpec;
import common.core.web.context.RequestContext;

public class DefaultLiteDeviceResolver extends LiteDeviceResolver {
	private final Logger logger = LoggerFactory.getLogger(DefaultLiteDeviceResolver.class);

	public static final String SET_DEVICE_PARAM_NAME = "_device";

	public static final CookieSpec COOKIE_DEVICE = new CookieSpec(DefaultLiteDeviceResolver.SET_DEVICE_PARAM_NAME);

	private Device forceDevice;

	public DefaultLiteDeviceResolver(String forceDevice) {
		if (StringUtils.isEmpty(forceDevice))
			return;
		this.forceDevice = new CookieDevice(DeviceType.valueOf(forceDevice));
		logger.debug("set forceDevice to {}", forceDevice);
	}

	public DefaultLiteDeviceResolver(List<String> normalUserAgentKeywords) {
		super(normalUserAgentKeywords);
	}

	private void updateCookieByParam() {
		String value = RequestContext.getParameter(DefaultLiteDeviceResolver.SET_DEVICE_PARAM_NAME);
		if (StringUtils.hasText(value)) {
			logger.debug("update COOKIE_DEVICE={}", value);
			DefaultLiteDeviceResolver.COOKIE_DEVICE.setValue(value);
		}

	}

	@Override
	public Device resolveDevice(HttpServletRequest request) {
		if (null != forceDevice)
			return forceDevice;
		updateCookieByParam();
		Device device = resoleSetDevice(request);
		if (null != device)
			return device;
		return super.resolveDevice(request);
	}

	private Device resoleSetDevice(HttpServletRequest request) {
		String value = COOKIE_DEVICE.getValue();
		if (!StringUtils.hasText(value))
			return null;
		if (DeviceType.MOBILE.name().equalsIgnoreCase(value))
			return CookieDevice.MOBILE_INSTANCE;
		if (DeviceType.TABLET.name().equalsIgnoreCase(value))
			return CookieDevice.TABLET_INSTANCE;
		return CookieDevice.NORMAL_INSTANCE;
	}

}
