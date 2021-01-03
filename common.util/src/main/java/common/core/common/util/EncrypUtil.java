package common.core.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @description RSA加密&解密公共工具类
 * @author GANCHUNGEN
 * @since 2015年1月24日上午11:46:28
 */
public class EncrypUtil {
	private static final String ALGORITHM_RSA = "RSA";
	/** RSA最大加密明文大小 */
	private static final int MAX_ENCRYPT_SIZE = 245;
	/** RSA最大解密密文大小 */
	private static final int MAX_DECRYPT_SIZE = 256;

	/**
	 * @description RSA分段解密，每段256位长度
	 * @param decryptedMessage
	 *            需要解密的参数
	 * @param privateKey
	 *            解密私钥
	 * @return String 解密后返回的参数
	 */
	public static String decrypt(String decryptedMessage, String privateKey) {
		byte[] messageBytes = EncodingUtil.decodeBase64(decryptedMessage);
		return new String(decrypt(messageBytes, privateKey), EncodingUtil.DEFAULT_CHARSET_CLASS);
	}

	/**
	 * @description RSA分段解密，每段256位长度
	 * @param decryptedMessage
	 *            需要解密的参数
	 * @param privateKey
	 *            解密私钥
	 * @return byte[] 解密后返回的参数 @ 如果想返回String，直接对返回值进行new String(返回值)即可
	 */
	public static byte[] decrypt(byte[] decryptedMessage, String privateKey) {
		byte[] keyBytes = EncodingUtil.decodeBase64(privateKey);
		try {
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			PrivateKey priK = keyFactory.generatePrivate(keySpec);
			Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
			cipher.init(Cipher.DECRYPT_MODE, priK);
			int decryptedMessageLen = decryptedMessage.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (decryptedMessageLen - offSet > 0) {
				if (decryptedMessageLen - offSet > MAX_DECRYPT_SIZE) {
					cache = cipher.doFinal(decryptedMessage, offSet, MAX_DECRYPT_SIZE);
				} else {
					cache = cipher.doFinal(decryptedMessage, offSet, decryptedMessageLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_SIZE;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return decryptedData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			throw new IllegalStateException("failed to decrypt message, please check private key and message", e);
		} catch (InvalidKeyException | InvalidKeySpecException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * @description RSA分段加密，每段245位长度
	 * @param encryptedMessage
	 *            需要加密的参数
	 * @param publicKey
	 *            加密公钥
	 * @return String 加密后返回的参数
	 */
	public static String encrypt(String encryptedMessage, String publicKey) {
		byte[] messageBytes = encryptedMessage.getBytes(EncodingUtil.DEFAULT_CHARSET_CLASS);
		return EncodingUtil.base64(encrypt(messageBytes, publicKey));
	}

	/**
	 * @description RSA分段加密，每段245位长度
	 * @param encryptedMessage
	 *            需要加密的参数
	 * @param publicKey
	 *            加密公钥
	 * @return byte[] 加密后返回的参数 @ 如果想返回String，直接对返回值进行new String(返回值)即可
	 */
	public static byte[] encrypt(byte[] encryptedMessage, String publicKey) {
		byte[] keyBytes = EncodingUtil.decodeBase64(publicKey);
		try {
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			PublicKey pubK = keyFactory.generatePublic(x509KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, pubK);
			int encryptedMessageLen = encryptedMessage.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (encryptedMessageLen - offSet > 0) {
				if (encryptedMessageLen - offSet > MAX_ENCRYPT_SIZE) {
					cache = cipher.doFinal(encryptedMessage, offSet, MAX_ENCRYPT_SIZE);
				} else {
					cache = cipher.doFinal(encryptedMessage, offSet, encryptedMessageLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_SIZE;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return encryptedData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			throw new IllegalStateException(e);
		} catch (InvalidKeyException | InvalidKeySpecException e) {
			throw new IllegalArgumentException(e);
		}
	}
}