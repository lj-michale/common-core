package common.core.site.cookie;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceType;


public class CookieDevice implements Device {
	public static final CookieDevice NORMAL_INSTANCE = new CookieDevice(DeviceType.NORMAL);

	public static final CookieDevice MOBILE_INSTANCE = new CookieDevice(DeviceType.MOBILE);

	public static final CookieDevice TABLET_INSTANCE = new CookieDevice(DeviceType.TABLET);

	@Override
	public boolean isNormal() {
		return this.deviceType == DeviceType.NORMAL;
	}

	@Override
	public boolean isMobile() {
		return this.deviceType == DeviceType.MOBILE;	
	}

	@Override
	public boolean isTablet() {
		return this.deviceType == DeviceType.TABLET;
	}

	public DeviceType getDeviceType() {
		return this.deviceType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[CookieDevice ");
		builder.append("type").append("=").append(this.deviceType);
		builder.append("]");
		return builder.toString();
	}

	private final DeviceType deviceType;

	/**
	 * @param deviceType
	 */
	public CookieDevice(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

}
