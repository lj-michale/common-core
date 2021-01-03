package common.core.common.collection;

import java.util.Date;

import common.core.common.util.StringUtil;

public class Key<T> {
	static final String ERROR_MESSAGE_NAME_CANNOT_BE_EMPTY = "name cannot be empty";
	static final String ERROR_MESSAGE_TARGET_CLASS_CANNOT_BE_NULL = "targetClass cannot be null";
	static final String ERROR_MESSAGE_TARGET_CLASS_CANNOT_BE_PRIMITIVE = "targetClass cannot be primitive, use wrapper class instead, e.g. Integer.class for int.class";
	static final String ERROR_MESSAGE_TARGET_CLASS_CANNOT_BE_INTERFACE = "targetClass cannot be interface, use concrete class instead with JAXB annotation";

	public static <T> Key<T> key(String name, Class<T> targetClass) {
		return new Key<>(name, targetClass);
	}

	public static Key<Integer> intKey(String name) {
		return key(name, Integer.class);
	}

	public static Key<Long> longKey(String name) {
		return key(name, Long.class);
	}

	public static Key<Double> doubleKey(String name) {
		return key(name, Double.class);
	}

	public static Key<String> stringKey(String name) {
		return key(name, String.class);
	}

	public static Key<Date> dateKey(String name) {
		return key(name, Date.class);
	}

	public static Key<Boolean> booleanKey(String name) {
		return key(name, Boolean.class);
	}

	final String name;
	final Class<? extends T> targetClass;

	Key(String name, Class<? extends T> targetClass) {
		if (!StringUtil.hasText(name))
			throw new IllegalArgumentException(ERROR_MESSAGE_NAME_CANNOT_BE_EMPTY);
		if (targetClass == null)
			throw new IllegalArgumentException(ERROR_MESSAGE_TARGET_CLASS_CANNOT_BE_NULL);
		if (targetClass.isPrimitive()) {
			throw new IllegalArgumentException(ERROR_MESSAGE_TARGET_CLASS_CANNOT_BE_PRIMITIVE);
		}

		if (targetClass.isInterface()) {
			throw new IllegalArgumentException(ERROR_MESSAGE_TARGET_CLASS_CANNOT_BE_INTERFACE);
		}

		this.name = name;
		this.targetClass = targetClass;
	}

	public String name() {
		return name;
	}

	public Class<? extends T> targetClass() {
		return targetClass;
	}

	@Override
	public String toString() {
		return String.format("[name=%s, class=%s]", name, targetClass.getName());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Key))
			return false;

		@SuppressWarnings("rawtypes")
		Key that = (Key) o;

		return name.equals(that.name) && targetClass.equals(that.targetClass);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + targetClass.hashCode();
		return result;
	}
}