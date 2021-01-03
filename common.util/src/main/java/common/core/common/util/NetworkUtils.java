package common.core.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class NetworkUtils {
	private static String localIP;
	private static String localHostName;

	public static String localIP() {
		if (localIP == null)
			try {
				String localIP = InetAddress.getLocalHost().getHostAddress();
				NetworkUtils.localIP = localIP;
				return localIP; // return temporary variable for lock free
			} catch (UnknownHostException e) {
				throw new RuntimeException(e);
			}
		return localIP;
	}

	public static String localHostName() {
		if (localHostName == null)
			try {
				String localHostName = InetAddress.getLocalHost().getHostName();
				NetworkUtils.localHostName = localHostName;
				return localHostName; // return temporary variable for lock free
			} catch (UnknownHostException e) {
				throw new RuntimeException(e);
			}
		return localHostName;
	}

	private NetworkUtils() {
	}
}
