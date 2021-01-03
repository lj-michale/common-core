package common.core.common.util;

import java.nio.charset.Charset;

import common.core.app.lang.CharSet;

/**
 * provide wrapper of common codec for convenience
 *
 */
public final class DigestUtils {
	public static String md5(String text) {
		return md5(text.getBytes(Charset.forName(CharSet.DEFAULT_CHAR_SET_STRING)));
	}

	public static String md5With16(String text) {
		return DigestUtils.md5(text).toString().substring(8, 24);
	}

	public static String md5With8(String text) {
		return StringUtil.convertString16To32(DigestUtils.md5With16(text));
	}

	public static String md5(byte[] bytes) {
		return org.apache.commons.codec.digest.DigestUtils.md5Hex(bytes);
	}

	public static String sha512(String text) {
		return org.apache.commons.codec.digest.DigestUtils.sha512Hex(text);
	}

	private DigestUtils() {
	}
}
