package common.core.common.util;

import java.util.UUID;

import common.core.common.assertion.util.AssertErrorUtils;

public class UuidUtil {
	public static String[] CHAR_62 = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UuidUtil.generateUuidWithoutLine();
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(CHAR_62[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

	public static String generateShortUuid(int number) {
		AssertErrorUtils.assertTrue(number > 0, "number must >0");
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < number; i++) {
			result.append(UuidUtil.generateShortUuid());
		}
		return result.toString();
	}

	public static String generateUuidWithoutLine() {
		return UuidUtil.generateFullUuid().replace("-", "");
	}

	public static String generateUuid() {
		return UuidUtil.generateUuidWithoutLine();
	}

	public static String generateFullUuid() {
		return UUID.randomUUID().toString();
	}

	public static String generateShortUuidWithTime() {
		return new StringBuffer().append(System.currentTimeMillis()).append(UuidUtil.generateShortUuid()).toString();
	}

	public static String generateUuid16() {
		String uuid = UuidUtil.generateUuidWithoutLine();
		return StringUtil.convertString16To32(uuid);
	}

	public static String generateUuid8WithTimeAndMd5() {
		return new StringBuffer().append(System.currentTimeMillis()).append(DigestUtils.md5With8(UuidUtil.generateFullUuid())).toString();
	}

	public static String generateUuid8WithMd5() {
		return DigestUtils.md5With8(UuidUtil.generateFullUuid());
	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		System.out.println(Long.toHexString(System.currentTimeMillis()));
		System.out.println(StringUtil.convertString16To32(Long.toHexString(System.currentTimeMillis())));
		System.out.println(StringUtil.convertString16To32(Long.toHexString(System.currentTimeMillis())));
		System.out.println('0' + 0);
		System.out.println('a' + 0);
		System.out.println('A' + 0);
		System.out.println(DigestUtils.md5With16(UuidUtil.generateFullUuid()));
	}
}
