package common.core.common.util;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;

/**
 * @description 签名验签公共工具类
 * @author GANCHUNGEN
 * @since 2015年1月24日下午2:46:51
 */
public class SignatureUtil {
	private static final String ALGORITHM_RSA = "RSA";
	private static final String ALGORITHM_SHA1_WITH_RSA = "SHA1withRSA";
	private static final String DIGEST_ALGORITHM = "SHA-512";
	public static final Charset DEFAULT_CHARSET_CLASS = Charset.forName("UTF-8");

	/**
	 * @description 验证签名
	 * @param Map
	 *            <String, String> paramMap
	 * @param signatureValue
	 * @return
	 */
	public static boolean verify(Map<String, ?> paramMap, String signatureValue, String publicKey) {
		String message = SignatureUtil.mapValue2String(paramMap);
		// byte[] messageBytes = EncodingUtil.decodeBase64(message);
		byte[] messageBytes = message.getBytes(DEFAULT_CHARSET_CLASS);
		return verify(messageBytes, signatureValue, publicKey);
	}

	/**
	 * @description 验证签名
	 * @param String
	 *            message
	 * @param signatureValue
	 * @return
	 */
	public static boolean verify(String message, String signatureValue, String publicKey) {
		// byte[] messageBytes = EncodingUtil.decodeBase64(message);
		byte[] messageBytes = message.getBytes(DEFAULT_CHARSET_CLASS);
		return verify(messageBytes, signatureValue, publicKey);
	}

	/**
	 * @description 验证签名
	 * @param byte[]
	 *            message
	 * @param signatureValue
	 * @return
	 */
	public static boolean verify(byte[] message, String signatureValue, String publicKey) {
		byte[] signBytes = EncodingUtil.decodeBase64(signatureValue);
		try {
			// 计算明文摘要
			byte[] digestData = digest(message).getBytes("utf-8");
			byte[] keyBytes = EncodingUtil.decodeBase64(publicKey);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			PublicKey pubKey = keyFactory.generatePublic(keySpec);
			java.security.Signature signature = java.security.Signature.getInstance(ALGORITHM_SHA1_WITH_RSA);
			signature.initVerify(pubKey);
			signature.update(digestData);
			return signature.verify(signBytes);
		} catch (NoSuchAlgorithmException | SignatureException e) {
			throw new IllegalStateException(e);
		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException(e);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * @description 签名
	 * @param Map
	 *            <String, String> paramMap
	 * @return String
	 */
	public static String sign(Map<String, ?> paramMap, String privateKey) {
		String message = SignatureUtil.mapValue2String(paramMap);
		// byte[] messageBytes = EncodingUtil.base64(message);
		@SuppressWarnings("unused")
		byte[] messageBytes = message.getBytes(DEFAULT_CHARSET_CLASS);
		// return sign(messageBytes, privateKey);
		return sign(message, privateKey);
	}

	/**
	 * @description 签名，,过滤指定的key。
	 * @param Map
	 *            <String, String> paramMap
	 * @return String
	 */
	public static String sign(Map<String, ?> paramMap, String privateKey, String filterKey) {
		String message = SignatureUtil.mapValue2String(paramMap, filterKey);
//		byte[] messageBytes = message.getBytes(DEFAULT_CHARSET_CLASS);
		// return sign(messageBytes, privateKey);
		return sign(message, privateKey);
	}

	/**
	 * @description 签名
	 * @param String
	 *            message
	 * @return String
	 */
	public static String sign(String message, String privateKey) {
		// byte[] messageBytes = EncodingUtil.base64(message);
		// byte[] messageBytes = EncodingUtil.decodeBase64(message);
		byte[] messageBytes = message.getBytes(DEFAULT_CHARSET_CLASS);
		return sign(messageBytes, privateKey);
	}

	/**
	 * @description 签名
	 * @param byte[]
	 *            message
	 * @return String
	 */
	public static String sign(byte[] message, String privateKey) {
		try {
			// 计算明文摘要
			byte[] digestData = digest(message).getBytes("utf-8");
			// 解密由base64编码的私钥
			byte[] keyBytes = EncodingUtil.decodeBase64(privateKey);
			// 构造PKCS8EncodedKeySpec对象
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			// KEY_ALGORITHM 指定的加密算法
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			// 取私钥匙对象
			PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
			java.security.Signature signature = java.security.Signature.getInstance(ALGORITHM_SHA1_WITH_RSA);
			signature.initSign(priKey);
			signature.update(digestData);
			return EncodingUtil.base64(signature.sign());
		} catch (NoSuchAlgorithmException | SignatureException e) {
			throw new IllegalStateException(e);
		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException(e);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * @description 用SHA-512算法计算摘要
	 * @param contents
	 * @return String
	 * @throws Exception
	 */
	private static String digest(byte[] contents) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance(DIGEST_ALGORITHM);
		byte[] digestbyte = messageDigest.digest(contents);
		return new String(Hex.encodeHex(digestbyte));
	}

	/**
	 * @description 将Map中的所有value值拼接成一个字符串
	 * @param paramMap
	 * @return String
	 */
	public static String mapValue2String(Map<String, ?> paramMap) {
		return mapValue2String(paramMap, "sign");
	}

	/**
	 * @description 将Map中的所有value值拼接成一个字符串,过滤指定的key。
	 * @param paramMap
	 * @return String
	 */
	public static String mapValue2String(Map<String, ?> paramMap, String filterKey) {
		StringBuffer strBuf = new StringBuffer();
		List<String> keys = new ArrayList<String>(paramMap.keySet());
		Collections.sort(keys);
		int i = 0;
		for (String key : keys) {
			if (!StringUtil.isNull(filterKey) && filterKey.equals(key)) {
				continue;
			}
			if (i++ > 0)
				strBuf.append("&");
			strBuf.append(key).append("=").append(String.valueOf(paramMap.get(key)));
		}
		return strBuf.toString();
	}
}
