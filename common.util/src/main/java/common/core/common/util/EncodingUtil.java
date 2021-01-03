package common.core.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Base64;

import common.core.app.lang.CharSet;

/***
 * @desc 编码解码公用工具类
 * @author GANCHUNGEN
 * @since 2015年1月24日上午10:39:15
 */
public final class EncodingUtil {

	public static final Charset DEFAULT_CHARSET_CLASS = Charset.forName("UTF-8");

	/**
	 * @description base64 encode
	 * @param bytes
	 *            []
	 * @return String
	 * @author GANCHUNGEN
	 */
	public static String base64(byte[] bytes) {
		return new String(Base64.getEncoder().encode(bytes), DEFAULT_CHARSET_CLASS);
	}

	/**
	 * @description base64 encode
	 * @param String
	 * @return byte[]
	 * @author GANCHUNGEN
	 */
	public static byte[] base64(String text) {
		return Base64.getEncoder().encode(text.getBytes(DEFAULT_CHARSET_CLASS));
	}

	/**
	 * @description base64 decode
	 * @param byte[]
	 * @return String
	 * @author GANCHUNGEN
	 */
	public static String decodeBase64(byte[] base64Bytes) {
		return new String(Base64.getDecoder().decode(base64Bytes), DEFAULT_CHARSET_CLASS);
	}

	/**
	 * @description base64 decode
	 * @param String
	 * @return byte[]
	 * @author GANCHUNGEN
	 */
	public static byte[] decodeBase64(String base64Text) {
		return Base64.getDecoder().decode(base64Text.getBytes(DEFAULT_CHARSET_CLASS));
	}

	/**
	 * @description URL encode
	 * @param String
	 * @return String
	 * @author GANCHUNGEN
	 */
	public static String urlEncode(String text) {
		try {
			return URLEncoder.encode(text, CharSet.DEFAULT_CHAR_SET_STRING);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}

	}

	/**
	 * @description URL decode
	 * @param String
	 * @return String
	 * @author GANCHUNGEN
	 */
	public static String urlDecode(String text) {
		try {
			return URLDecoder.decode(text, CharSet.DEFAULT_CHAR_SET_STRING);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * @description base URL encode
	 * @param byte[]
	 * @return String
	 * @author GANCHUNGEN
	 */
	public static String base64URLencode(byte[] textBytes) {
		return new String(Base64.getUrlEncoder().encode(textBytes), Charset.forName(CharSet.DEFAULT_CHAR_SET_STRING));
	}

	/**
	 * @description base URL encode
	 * @param String
	 * @return String
	 * @author GANCHUNGEN
	 */
	public static String base64URLencode(String text) {
		return new String(base64URLencode(text.getBytes(Charset.forName(CharSet.DEFAULT_CHAR_SET_STRING))));
	}

	/**
	 * @description base URL encode
	 * @param byte[]
	 * @return String
	 * @author GANCHUNGEN
	 */
	public static String base64URLdecode(byte[] bytes) {
		return new String(Base64.getUrlDecoder().decode(bytes), Charset.forName(CharSet.DEFAULT_CHAR_SET_STRING));
	}

	/**
	 * @description base URL encode
	 * @param String
	 * @return String
	 * @author GANCHUNGEN
	 */
	public static String base64URLdecode(String text) {
		return new String(base64URLdecode(text.getBytes(Charset.forName(CharSet.DEFAULT_CHAR_SET_STRING))));
	}

	public static void main(String[] args) {
		String text = "A";
		System.out.println("base64:" + base64(text));
		System.out.println("decodeBase64:" + decodeBase64(base64(text)));
		System.out.println("urlEncode:" + urlEncode(text));
		System.out.println("urlDecode:" + urlDecode(urlEncode(text)));
		System.out.println("base64URLencode:" + base64URLencode(text));
		System.out.println("base64URLdecode:" + base64URLdecode(base64URLencode(text)));
	}
}
