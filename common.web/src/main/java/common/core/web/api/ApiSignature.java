package common.core.web.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.crypto.RSAUtils;
import common.core.common.util.ObjectUtil;
import common.core.common.util.StringUtil;

/**
 * 算法参照：http://blog.csdn.net/zaocha321/article/details/52221316，http://blog.csdn
 * .net/wyqlxy/article/details/50561145，http://zhuoyaopingzi.iteye.com/blog/
 * 1992205
 * 
 *
 */
public class ApiSignature {

	private final Logger logger = LoggerFactory.getLogger(ApiSignature.class);

	private String[] ignoreParamNames = { "sign" };

	public String[] getIgnoreParamNames() {
		return ignoreParamNames;
	}

	public void verify(ApiRequest apiRequest) {
		Map<String, Object> paramValues = ObjectUtil.toMap(apiRequest);
		String publicKey = ApiContext.getPublicKey(apiRequest.getAppId());
		if (null == publicKey) {// 不需要签名
			return;
		}
		String signatureValue = apiRequest.getSign();
		if (StringUtil.isBlank(signatureValue)) {
			throw new ApiException(ApiException.PARAM_IS_NOT_EMPTY, "PARAM[sign] must be not empty");
		}
		String signatureBody = buildSignatureBody(paramValues);
		logger.debug("verify signatureBody:{}", signatureBody);
		boolean success = RSAUtils.verify(signatureBody, publicKey, signatureValue);
		if (!success)
			throw new ApiException(ApiException.SIGNATURE_VERIFY_FALSE, "签名验签失败");
	}

	public void sign(ApiResponse apiResponse) {
		Map<String, Object> paramValues = ObjectUtil.toMap(apiResponse);
		String privateKey = ApiContext.getPrivateKey();
		if (null == privateKey)// 空文件
			return;
		String signatureBody = buildSignatureBody(paramValues);
		logger.debug("sign signatureBody:{}", signatureBody);
		String signatureValue = RSAUtils.sign(signatureBody, privateKey);
		apiResponse.setSign(signatureValue);
	}

	private String buildSignatureBody(Map<String, Object> paramValues) {
		List<String> paramNames = new ArrayList<String>(paramValues.size());
		paramNames.addAll(paramValues.keySet());

		// 删除忽略参数
		String[] ignoreParamNames = getIgnoreParamNames();
		if (ignoreParamNames != null && ignoreParamNames.length > 0) {
			for (String ignoreParamName : ignoreParamNames) {
				paramNames.remove(ignoreParamName);
			}
		}

		// 删除空参数值参数
		for (Map.Entry<String, Object> entry : paramValues.entrySet()) {
			if (StringUtil.isEmpty(entry.getValue())) {
				paramNames.remove(entry.getKey());
			}
		}

		Collections.sort(paramNames);

		StringBuilder stringBuilder = new StringBuilder();
		for (String paramName : paramNames) {
			if (stringBuilder.length() > 0)
				stringBuilder.append("&");
			stringBuilder.append(paramName).append("=").append(paramValues.get(paramName));
		}

		return stringBuilder.toString();
	}

}
