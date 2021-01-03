package common.core.web.api.view.api.method;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.StringUtil;

public class ApiVersionUtil {

	public static boolean compareVersionValue(String requestVesion, String apiVesion, String compareType) {
		String[] requestVesions = StringUtil.split(requestVesion, ".");
		String[] apiVesions = StringUtil.split(apiVesion, ".");
		AssertErrorUtils.assertTrue(requestVesions.length == apiVesions.length, "版本格式表达不一致[{}!={}]", requestVesion, apiVesion);
		Long requestVesionValue = 0L;
		Long apiVesionValue = 0L;
		for (int i = 0; i < requestVesions.length; i++) {
			requestVesionValue = requestVesionValue*100+ Long.valueOf(requestVesions[i]) ;
			apiVesionValue =apiVesionValue*100 +Long.valueOf(apiVesions[i]);
		}
		return compareValue(requestVesionValue, apiVesionValue, compareType);
	}

	public static boolean compareValue(long requestVesion, long apiVesion, String compareType) {
		if (">=".equals(compareType)) {
			return requestVesion >= apiVesion;
		} else if ("<=".equals(compareType)) {
			return requestVesion <= apiVesion;
		} else if ("=".equals(compareType)) {
			return requestVesion == apiVesion;
		}  else if ("==".equals(compareType)) {
			return requestVesion == apiVesion;
		} else if ("!=".equals(compareType)) {
			return requestVesion != apiVesion;
		} else if ("<>".equals(compareType)) {
			return requestVesion != apiVesion;
		}else if (">".equals(compareType)) {
			return requestVesion > apiVesion;
		} else if ("<".equals(compareType)) {
			return requestVesion < apiVesion;
		}
		return true;
	}

}
