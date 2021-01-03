package common.core.site.view;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.StringUtil;
import common.core.site.tag.TagNames;

public class ViewContext {

	private final static ThreadLocal<Map<String, Object>> ME = new ThreadLocal<>();
	protected final static String[] IGNORE_KEY;
	static {
		Field[] fields = TagNames.class.getDeclaredFields();
		IGNORE_KEY = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			try {
				IGNORE_KEY[i] = (String) fields[i].get(TagNames.class);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void initContext() {
		ME.set(new HashMap<String, Object>());
	}

	public static void cleanContext() {
		if (null != ME.get())
			ME.get().clear();
		ME.remove();
	}

	public static void put(String key, Object value) {
		for (String tagName : IGNORE_KEY) {
			AssertErrorUtils.assertFalse(tagName.equals(key), "key must not in {}", StringUtil.arrayToCommaDelimitedString(IGNORE_KEY));
		}
		ME.get().put(key, value);
	}

	public static Object get(String key) {
		return ME.get().get(key);
	}

	public static Map<String, Object> currentValues() {
		return ME.get();
	}

}
