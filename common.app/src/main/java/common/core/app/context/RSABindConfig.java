package common.core.app.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import common.core.app.exception.InitException;
import common.core.common.crypto.RSAUtils;
import common.core.common.util.IoUtil;

public class RSABindConfig {

	private static final String DATA_CONFIG_PUBLIC_KEY_TXT = "/data/config/rsa_public_key.pem";
	private static final String DATA_CONFIG_SECRET_KEY_TXT = "/data/config/pkcs8_rsa_private_key.pem";
	private Map<String, String> privateKeyMap = new HashMap<String, String>();
	private Map<String, String> publicKeyMap = new HashMap<String, String>();

	/**
	 * 获取密钥
	 * 
	 * @param decoder
	 * @return
	 */
	public String getPrivateKey(String privateKeyConfigName) {
		if (privateKeyMap.containsKey(privateKeyConfigName))
			return privateKeyMap.get(privateKeyConfigName);
		String privateKeyFile = privateKeyConfigName;
		if (privateKeyFile.indexOf(":") < 0)
			privateKeyFile = ConfigContext.getStringValue(privateKeyConfigName, DATA_CONFIG_SECRET_KEY_TXT);
		String result = getResult(privateKeyFile);
		privateKeyMap.put(privateKeyConfigName, result);
		return result;
	}

	/**
	 * 获取公钥
	 * 
	 * @param encoder
	 * @return
	 */
	public String getPublicKey(String publicKeyConfigName) {
		if (publicKeyMap.containsKey(publicKeyConfigName))
			return publicKeyMap.get(publicKeyConfigName);
		String publicKeyFile = publicKeyConfigName;
		if (publicKeyFile.indexOf(":") < 0)
			publicKeyFile = ConfigContext.getStringValue(publicKeyConfigName, DATA_CONFIG_PUBLIC_KEY_TXT);
		String result = getResult(publicKeyFile);
		publicKeyMap.put(publicKeyConfigName, result);
		return result;
	}

	private String getResult(String keyFile) {
		String result = null;
		InputStream inputStream = null;
		try {
			if (keyFile.startsWith("classpath:") || keyFile.startsWith("file:") || keyFile.startsWith("http:")
					|| keyFile.startsWith("https:")) {
				Resource resource = new PathMatchingResourcePatternResolver().getResource(keyFile);
				inputStream = resource.getInputStream();
			} else if (new File(keyFile).exists()) {
				inputStream = new FileInputStream(new File(keyFile));
			}

			if (null != inputStream) {
				result = inputStream2String(inputStream);
			}
		} catch (Exception e) {
			throw new InitException("init secret key err", e);
		} finally {
			IoUtil.close(inputStream);
		}
		return result;
	}

	public static String inputStream2String(InputStream is) throws Exception {
		return RSAUtils.buildKeyFromPemContext(IoUtil.text(is));
	}

}
