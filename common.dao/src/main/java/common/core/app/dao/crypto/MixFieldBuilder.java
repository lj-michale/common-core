package common.core.app.dao.crypto;

import java.lang.reflect.Field;

import common.core.app.context.RSABindConfig;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.DigestUtils;

public class MixFieldBuilder implements CryptoFieldBuilder {

	RSABindConfig rsaConfig;

	public String getFieldValueString(Object obj, Field field) {
		try {
			if (!field.isAccessible())
				field.setAccessible(true);
			Object value = field.get(obj);
			if (value == null) {
				return null;
			} else {
				return value.toString();
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object encode(Object obj, Field field, Object fieldValue) {
		MixField mixField = field.getAnnotation(MixField.class);
		if (null == mixField || null == fieldValue)
			return fieldValue;

		StringBuffer valueBuffer = new StringBuffer();
		valueBuffer.append(fieldValue);

		// build value with assemblyFields
		for (String assemblyFieldName : mixField.assemblyFields()) {
			try {
				Field assemblyField = obj.getClass().getDeclaredField(assemblyFieldName);
				String assemblyFieldValue = getFieldValueString(obj, assemblyField);
				AssertErrorUtils.assertHasText(assemblyFieldValue, "can't get value of field:{}", assemblyField);
				valueBuffer.append(assemblyFieldValue);
			} catch (NoSuchFieldException | SecurityException e) {
				throw new RuntimeException(e);
			}
		}

		// build value with marker key
		String privateKeyFileConfig = mixField.privateKeyFileConfig();
		String privateKey = rsaConfig.getPrivateKey(privateKeyFileConfig);
		valueBuffer.append(privateKey);

		return DigestUtils.md5(valueBuffer.toString());
	}

	@Override
	public Object decode(Object obj, Field field, Object fieldValue) {
		return fieldValue;
	}

	public MixFieldBuilder(RSABindConfig rsaConfig) {
		super();
		this.rsaConfig = rsaConfig;
	}

}
