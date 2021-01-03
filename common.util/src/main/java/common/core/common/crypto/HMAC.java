package common.core.common.crypto;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import common.core.common.assertion.util.AssertErrorUtils;

/**
 * http://en.wikipedia.org/wiki/Message_authentication_code
 *
 */
public class HMAC {
	private byte[] key;
	private Hash hash = Hash.MD5;

	public byte[] digest(String message) {
		AssertErrorUtils.assertNotNull(key, "key should not be null");
		try {
			String algorithm = getAlgorithm();
			Mac mac = Mac.getInstance(algorithm);

			SecretKey secretKey = new SecretKeySpec(key, algorithm);
			mac.init(secretKey);
			return mac.doFinal(message.getBytes(Charset.forName("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public byte[] generateKey() {
		try {
			KeyGenerator generator = KeyGenerator.getInstance(getAlgorithm());
			generator.init(128);
			return generator.generateKey().getEncoded();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	private String getAlgorithm() {
		return "Hmac" + hash.value;
	}

	public void setSecretKey(byte[] key) {
		this.key = key;
	}

	public void setHash(Hash hash) {
		this.hash = hash;
	}

	public static enum Hash {
		MD5("MD5"), SHA1("SHA1"), SHA256("SHA256"), SHA512("SHA512");

		Hash(String value) {
			this.value = value;
		}

		final String value;
	}
}
