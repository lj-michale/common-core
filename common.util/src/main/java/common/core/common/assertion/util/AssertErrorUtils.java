package common.core.common.assertion.util;

import java.util.regex.Pattern;

import common.core.common.assertion.exception.ErrorAssertionException;
import common.core.common.util.StringUtil;

public class AssertErrorUtils {
	public static void assertNull(Object target, String message, Object... params) {
		if (target != null) {
			throwAssertionException(message, params);
		}
	}

	public static void assertNotNull(Object target, String message, Object... params) {
		if (target == null) {
			throwAssertionException(message, params);
		}
	}

	public static void assertTrue(boolean target, String message, Object... params) {
		if (!target) {
			throwAssertionException(message, params);
		}
	}

	public static void assertFalse(boolean target, String message, Object... params) {
		assertTrue(!target, message, params);
	}

	public static void assertHasText(String target, String message, Object... params) {
		if (!StringUtil.hasText(target)) {
			throwAssertionException(message, params);
		}
	}

	public static void assertMatches(String target, Pattern pattern, String message, Object... params) {
		if (!pattern.matcher(target).matches()) {
			throwAssertionException(message, params);
		}
	}

	public static void throwAssertionException(String message, Object... params) {
		String errorMessage = params == null ? message : StringUtil.format(message, params);
		throw new ErrorAssertionException(errorMessage);
	}

}
