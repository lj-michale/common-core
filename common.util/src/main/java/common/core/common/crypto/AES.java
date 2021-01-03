package common.core.common.crypto;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	private static final String ALGORITHM_AES = "AES";
	private byte[] key;

	public byte[] encrypt(byte[] plainMessage) {
		try {
			Cipher cipher = createCipher(ENCRYPT_MODE);
			return cipher.doFinal(plainMessage);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
			throw new IllegalStateException(e);
		}
	}

	public byte[] decrypt(byte[] encryptedMessage) {
		try {
			Cipher cipher = createCipher(DECRYPT_MODE);
			return cipher.doFinal(encryptedMessage);
		} catch (IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * generate the AES key, for using AES256 requires to update jdk with JCE
	 * Unlimited Strength Jurisdiction Policy Files
	 *
	 * @param keySize
	 *            the size, use 128 or 256 for AES128/AES256
	 * @return the key content
	 */
	public byte[] generateKey(int keySize) {
		try {
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM_AES);
			generator.init(keySize);
			return generator.generateKey().getEncoded();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	private Cipher createCipher(int encryptMode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM_AES);
		Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
		cipher.init(encryptMode, keySpec, new SecureRandom());
		return cipher;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}
}
