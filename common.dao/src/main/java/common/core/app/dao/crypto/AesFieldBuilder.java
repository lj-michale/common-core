package common.core.app.dao.crypto;

import java.lang.reflect.Field;

import common.core.app.context.RSABindConfig;
import common.core.common.crypto.AESUtil;
import common.core.common.util.StringUtil;

public class AesFieldBuilder implements CryptoFieldBuilder {

	RSABindConfig rsaConfig;

	@Override
	public Object encode(Object obj, Field field, Object fieldValue) {
		AesField aesField = field.getAnnotation(AesField.class);
		if (null == aesField || StringUtil.isEmpty(fieldValue))
			return fieldValue;

		// getkey
		String privateKeyFileConfig = aesField.privateKeyFileConfig();
		String privateKey = rsaConfig.getPrivateKey(privateKeyFileConfig);

		return AESUtil.encrypt(fieldValue.toString(), privateKey);
	}

	@Override
	public Object decode(Object obj, Field field, Object fieldValue) {
		AesField aesField = field.getAnnotation(AesField.class);
		if (null == aesField || StringUtil.isEmpty(fieldValue))
			return fieldValue;

		// getkey
		String privateKeyFileConfig = aesField.privateKeyFileConfig();
		String privateKey = rsaConfig.getPrivateKey(privateKeyFileConfig);

		return AESUtil.decrypt(fieldValue.toString(), privateKey);
	}

	public AesFieldBuilder(RSABindConfig rsaConfig) {
		super();
		this.rsaConfig = rsaConfig;
	}

}
