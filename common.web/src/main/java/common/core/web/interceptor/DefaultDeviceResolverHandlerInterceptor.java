package common.core.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.site.cookie.CookieDevice;

public class DefaultDeviceResolverHandlerInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = LoggerFactory.getLogger(DefaultDeviceResolverHandlerInterceptor.class);

	private final DeviceResolver deviceResolver;

	/**
	 * Create a device resolving {@link HandlerInterceptor}.
	 * 
	 * @param deviceResolver
	 *            the device resolver to delegate to in
	 *            {@link #preHandle(HttpServletRequest, HttpServletResponse, Object)}
	 *            .
	 */
	public DefaultDeviceResolverHandlerInterceptor(DeviceResolver deviceResolver) {
		this.deviceResolver = deviceResolver;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (null != request.getAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE))
			return true;

		Device device = deviceResolver.resolveDevice(request);
		
		//中焯
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.hasText(userAgent) && userAgent.indexOf("www.tzt.cn") >= 0) {
			device = CookieDevice.MOBILE_INSTANCE;
		}
		
		logger.debug("deviceResolver={}", device);
		request.setAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE, device);
		return true;
	}

}
