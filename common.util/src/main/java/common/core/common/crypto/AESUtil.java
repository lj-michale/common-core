package common.core.common.crypto;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import common.core.app.lang.CharSet;
import common.core.common.util.StringUtil;

public class AESUtil {

	/**
	 * 将byte[]转为各种进制的字符串
	 * 
	 * @param bytes
	 *            byte[]
	 * @param radix
	 *            可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	 * @return 转换后的字符串
	 */
	public static String binary(byte[] bytes, int radix) {
		return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
	}

	/**
	 * base 64 encode
	 * 
	 * @param bytes
	 *            待编码的byte[]
	 * @return 编码后的base 64 code
	 */
	public static String base64Encode(byte[] bytes) {
		return Base64Utils.encode(bytes);
	}

	/**
	 * base 64 decode
	 * 
	 * @param base64Code
	 *            待解码的base 64 code
	 * @return 解码后的byte[]
	 * @throws Exception
	 */
	public static byte[] base64Decode(String base64Code) {
		return StringUtil.isEmpty(base64Code) ? null : Base64Utils.decode(base64Code);
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	public static byte[] encryptToBytes(String content, String encryptKey) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// 防止linux下 随机生成key
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(encryptKey.getBytes(CharSet.DEFAULT_CHAR_SET_CLASS));
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes(CharSet.DEFAULT_CHAR_SET_CLASS);
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * AES加密为base 64 code
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */
	public static String encrypt(String content, String encryptKey) {
		if (StringUtil.isEmpty(content) || StringUtil.isEmpty(encryptKey))
			return content;
		return base64Encode(encryptToBytes(content, encryptKey));
	}

	/**
	 * AES解密
	 * 
	 * @param encryptBytes
	 *            待解密的byte[]
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	public static String decryptByBytes(byte[] encryptBytes, String decryptKey) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// 防止linux下 随机生成key
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(decryptKey.getBytes(CharSet.DEFAULT_CHAR_SET_CLASS));
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(encryptBytes);
			return new String(result, CharSet.DEFAULT_CHAR_SET_CLASS); // 加密
		} catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将base 64 code AES解密
	 * 
	 * @param encryptStr
	 *            待解密的base 64 code
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的string
	 * @throws Exception
	 */
	public static String decrypt(String encryptStr, String decryptKey) {
		if (StringUtil.isEmpty(encryptStr) || StringUtil.isEmpty(decryptKey))
			return encryptStr;
		return decryptByBytes(base64Decode(encryptStr), decryptKey);
	}
}
