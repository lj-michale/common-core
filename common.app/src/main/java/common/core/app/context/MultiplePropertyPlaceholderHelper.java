package common.core.app.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import common.core.app.exception.InitException;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.crypto.RSAUtils;
import common.core.common.util.IoUtil;
import common.core.common.util.StringUtil;

public class MultiplePropertyPlaceholderHelper {

	private Map<String, String> privateFileMap = new ConcurrentHashMap<String, String>();

	public static final String RSA_END = "]:";
	public static final String RSA_START = "@rsa[";
	PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();

	public MultiplePropertyPlaceholderHelper() {
		super();
	}

	public String decodeValue(String value) {
		if (StringUtil.isBlank(value))
			return value;
		if (value.startsWith(RSA_START)) {
			int endIndex = value.indexOf(RSA_END);
			String privateKeyFile = value.substring(RSA_START.length(), endIndex);
			privateKeyFile = ConfigContext.getStringValue(privateKeyFile, privateKeyFile);
			String privateKeyText = this.getPrivateKeyText(privateKeyFile);
			value = value.substring(endIndex + RSA_END.length());
			return RSAUtils.decryptByPrivateKey(value, privateKeyText);
		}
		return value;
	}

	private String getPrivateKeyText(String privateKeyFile) {
		if (privateFileMap.containsKey(privateKeyFile))
			return privateFileMap.get(privateKeyFile);

		InputStream inputStream = null;
		try {
			if (privateKeyFile.startsWith("classpath:")) {
				Resource resource = pathMatchingResourcePatternResolver.getResource(privateKeyFile);
				inputStream = resource.getInputStream();
			} else {
				if (privateKeyFile.startsWith("file://"))
					privateKeyFile = privateKeyFile.substring("file://".length());
				else if (privateKeyFile.startsWith("file:"))
					privateKeyFile = privateKeyFile.substring("file:".length());
				inputStream = new FileInputStream(new File(privateKeyFile));
			}
			AssertErrorUtils.assertNotNull(inputStream, "Can't load file privateKeyFile={}", privateKeyFile);

			String result = RSABindConfig.inputStream2String(inputStream);
			AssertErrorUtils.assertHasText(result, "Load empty file privateKeyFile={}", privateKeyFile);

			privateFileMap.put(privateKeyFile, result);
			return result;
		} catch (Exception e) {
			throw new InitException("init secret key err", e);
		} finally {
			IoUtil.close(inputStream);
			inputStream = null;
		}
	}

}
