package common.core.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.converters.StringConverter;
import org.springframework.util.StringUtils;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.exception.ConvertException;

import jodd.util.RandomStringUtil;

public class StringUtil extends StringUtils {

	public static final String TYPE_NUMBER = "N";
	public static final String DEFAULT_DELIMITER = "[,;]";
	static final String DELIM_STR = "{}";
	static StringConverter STRING_CONVERTER = new StringConverter();
	public static String[] CHAR_32 = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v" };

	@SuppressWarnings("unchecked")
	public static <T> T convert(String value, Class<T> type) {
		if (type.equals(String.class)) {
			return (T) value;
		}
		if (StringUtil.isEmpty(value))
			return null;
		if (Integer.class.equals(type) || Integer.TYPE == type) {
			return (T) Integer.valueOf(value);
		} else if (Long.class.equals(type) || Long.TYPE == type) {
			return (T) Long.valueOf(value);
		} else if (Double.class.equals(type) || Double.TYPE == type) {
			return (T) Double.valueOf(value);
		} else if (Float.class.equals(type) || Float.TYPE == type) {
			return (T) Float.valueOf(value);
		} else if (Boolean.class.equals(type) || Boolean.TYPE == type) {
			return (T) Boolean.valueOf(value);
		} else if (BigDecimal.class.equals(type)) {
			return (T) new BigDecimal(value);
		}
		throw new ConvertException("can't convert " + value + " to " + type.getName());
	}

	public static String[] split(String str) {
		return StringUtil.splitWithRegx(str, DEFAULT_DELIMITER);
	}

	public static String[] split(String toSplit, String delimiter) {
		if (!hasLength(toSplit) || !hasLength(delimiter)) {
			return null;
		}
		StringTokenizer stringTokenizer = new StringTokenizer(toSplit, delimiter);
		List<String> result = new ArrayList<>();
		while (stringTokenizer.hasMoreTokens()) {
			result.add(stringTokenizer.nextToken());
		}
		return result.toArray(new String[result.size()]);
	}

	public static String[] splitWithRegx(String str, String regx) {
		if (null == str) {
			return null;
		}
		return str.split(regx);
	}

	public static String randomSimpleString(int count) {
		return RandomStringUtil.random(count, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
	}

	public static boolean isNotBlank(String str) {
		return !StringUtil.isBlank(str);
	}

	public static boolean isBlank(String str) {
		return StringUtil.isEmpty(StringUtil.trimBlank(str));
	}

	public static String ascii2Native(String str) {
		char[] in = str.toCharArray();
		int off = 0;
		int len = in.length;
		char aChar;
		char[] out = new char[len];
		int outLen = 0;
		int end = off + len;

		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = in[off++];
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char) value;
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = aChar;
			}
		}
		return new String(out, 0, outLen);
	}

	/**
	 * @description 生成len位数字短信验证码
	 * @param int
	 *            len 长度
	 * @param string
	 *            type 格式， N-数字，其它--字母+数字
	 * @return String
	 */
	public static String getValidateCode(int len, String type) {
		String valCode = "";
		String[] beforeShuffle = null;
		if (TYPE_NUMBER.equalsIgnoreCase(type)) {
			beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		} else {
			beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		}
		List<String> list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		valCode = afterShuffle.substring(9, 9 + len);
		return valCode;
	}

	public static boolean isNull(String str) {
		if (str == null) {
			return true;
		} else if ("".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串转换成日期 --毫秒数
	 * 
	 * @param str
	 *            格式 ：yyyy-MM-dd
	 * @return date
	 */
	public static Long StrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(date);
		return str;
	}

	/**
	 * 判断str是否为纯数字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static String convertString16To32(String hexString) {
		StringBuffer shortBuffer = new StringBuffer();
		int lenth = hexString.length() / 2;
		for (int i = 0; i < lenth; i++) {
			String str16 = hexString.substring(i * 2, i * 2 + 2);
			String char32 = convertChar16To32(str16);
			shortBuffer.append(char32);
		}
		return shortBuffer.toString();
	}

	public static String convertChar16To32(String str16) {
		int x = Integer.parseInt(str16, 16);
		String char32 = CHAR_32[x % 32];
		return char32;
	}

	final public static String format(final String messagePattern, Object... arguments) {
		StringBuffer result = new StringBuffer();
		int start = 0;
		int pos = 0;
		for (int i = 0; i < arguments.length; i++) {
			Object object = arguments[i];
			pos = messagePattern.indexOf(DELIM_STR, start);
			AssertErrorUtils.assertTrue(pos >= 0, messagePattern + " can't find the " + i + " pattern");
			result.append(messagePattern.substring(start, pos)).append(object.toString());
			start = pos + 2;
			AssertErrorUtils.assertTrue(start <= messagePattern.length(), messagePattern + " can't find the " + (i + 1) + " pattern");
		}
		result.append(messagePattern.substring(start));
		return result.toString();
	}

	public static String trimBlank(String source) {
		if (null == source) {
			return null;
		}
		return source.replaceAll("(^[\\t]+)|([\\t]+$)/g", "").trim();
	}

	public static String integerToString(Integer value) {
		if (value == null)
			return "";
		return value.toString();
	}

	public static String bigDecimalToString(BigDecimal value) {
		if (value == null)
			return "";
		return new DecimalFormat("#0").format(value);
	}

	public static String dateToString(Date value) {
		if (value == null)
			return "";
		return value.toString();
	}

	public static String nullToString(String value) {
		if(StringUtils.isEmpty(value)){
			return "";
		}
		return value;
	}
}
