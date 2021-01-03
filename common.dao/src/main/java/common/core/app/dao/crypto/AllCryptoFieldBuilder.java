package common.core.app.dao.crypto;

import java.lang.reflect.Field;

import common.core.app.context.RSABindConfig;

public class AllCryptoFieldBuilder implements CryptoFieldBuilder {

	private CryptoFieldBuilder[] encodeCryptoFieldBuilders = null;

	private CryptoFieldBuilder[] decodeCryptoFieldBuilders = null;

	private static final AllCryptoFieldBuilder ME = new AllCryptoFieldBuilder();

	public static AllCryptoFieldBuilder get() {
		return ME;
	}

	private AllCryptoFieldBuilder() {
		super();
		RSABindConfig rsaConfig = new RSABindConfig();
		encodeCryptoFieldBuilders = new CryptoFieldBuilder[] { new AesFieldBuilder(rsaConfig), new MixFieldBuilder(rsaConfig) };
		decodeCryptoFieldBuilders = new CryptoFieldBuilder[] { new AesFieldBuilder(rsaConfig) };
	}

	@Override
	public Object encode(Object obj, Field field, Object fieldValue) {
		Object result = fieldValue;
		for (CryptoFieldBuilder cryptoFieldBuilder : encodeCryptoFieldBuilders) {
			result = cryptoFieldBuilder.encode(obj, field, result);
		}
		return result;
	}

	@Override
	public Object decode(Object obj, Field field, Object fieldValue) {
		Object result = fieldValue;
		for (CryptoFieldBuilder cryptoFieldBuilder : decodeCryptoFieldBuilders) {
			result = cryptoFieldBuilder.decode(obj, field, result);
		}
		return result;
	}

}
