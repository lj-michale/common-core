package common.core.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

public class ClassUtil extends jodd.bean.BeanUtil {

	public static Object newInstance(String className) {
		try {
			return ClassUtil.newInstance(Class.forName(className));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T newInstance(Class<T> type) {
		try {
			return type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Class<?> getUserClass(Object instance) {
		return ClassUtils.getUserClass(instance);
	}

	public static Class<?> getUserClass(Class<?> clazz) {
		return ClassUtils.getUserClass(clazz);
	}

	public static Method getMostSpecificMethod(Method method, Class<?> targetClass) {
		return ClassUtils.getMostSpecificMethod(method, targetClass);
	}

	public static Annotation[] getAnnotations(Method method) {
		return AnnotationUtils.getAnnotations(method);
	}

	public static <A extends Annotation> A getAnnotation(Method method, Class<A> annotationType) {
		return AnnotationUtils.getAnnotation(method, annotationType);
	}

	public static <T extends Annotation> List<Method> findAnnotationMethods(Class<?> clazz, Class<T> annotationClass) {
		List<Method> methodList = new ArrayList<Method>();
		clazz = ClassUtil.getUserClass(clazz);
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (null != ClassUtil.getAnnotation(method, annotationClass))
				methodList.add(method);
		}
		for (Class<?> item : clazz.getInterfaces()) {
			methodList.addAll(ClassUtil.findAnnotationMethods(item, annotationClass));
		}
		// methodList.addAll(ClassUtil.findAnnotationMethods(clazz.getSuperclass(),
		// annotationClass));
		return methodList;
	}

	public static <T extends Annotation> List<Field> findAnnotationFields(Class<?> bean, Class<T> annotationClazz) {
		List<Field> fieldList = new ArrayList<Field>();
		Field[] fields = bean.getDeclaredFields();
		for (Field field : fields) {
			if (null != field.getAnnotation(annotationClazz)) {
				field.setAccessible(true);
				fieldList.add(field);
			}
		}
		if (!ClassUtil.isObjectSuperclass(bean)) {
			fieldList.addAll(ClassUtil.findAnnotationFields(bean.getSuperclass(), annotationClazz));
		}
		return fieldList;
	}

	public static <A extends Annotation> A findAnnotation(Class<?> clazz, Class<A> annotationType) {
		A annotation = AnnotationUtils.findAnnotation(clazz, annotationType);
		if (null == annotation)
			annotation = AnnotationUtils.findAnnotation(ClassUtil.getUserClass(clazz), annotationType);
		return annotation;
	}

	public static boolean isObjectSuperclass(Class<?> beanClass) {
		return null == beanClass.getSuperclass() || Object.class.equals(beanClass) || Object.class.equals(beanClass.getSuperclass());
	}

	/**
	 * @param type
	 * @return true when type is Primitive\boolean\string\date\number
	 */
	public static boolean isSimpaleType(Type type) {
		if (Boolean.class.equals(type) || Character.class.equals(type) || String.class.equals(type)) {
			return true;
		}
		if (Class.class.equals(type) || Type.class.equals(type))
			return true;
		if (type instanceof Class) {
			Class<?> classType = (Class<?>) type;
			return classType.isPrimitive() || Date.class.isAssignableFrom(classType) || Number.class.isAssignableFrom(classType) || Enum.class.isAssignableFrom(classType);
		}
		return false;
	}

	public static boolean isNumberType(Class<?> type) {
		if (Number.class.isAssignableFrom(type))
			return true;
		if (float.class.equals(type) || int.class.equals(type) || double.class.equals(type) || long.class.equals(type)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static Class getGenericTypeFromListField(Field field) {
		Class<?> fieldClazz = field.getType(); // 得到field的class及类型全路径

		if (isSimpaleType(fieldClazz))
			return null;

		if (List.class.isAssignableFrom(fieldClazz)) // 【2】
		{
			Type fc = field.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型
			return ClassUtil.getActualType(fc);
		}
		return null;
	}

	public static Class<?> getActualType(Type fc) {
		if (fc == null)
			return null;

		if (fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型
		{
			ParameterizedType pt = (ParameterizedType) fc;
			if (pt.getActualTypeArguments().length > 0 && pt.getActualTypeArguments()[0] instanceof Class)
				return (Class<?>) pt.getActualTypeArguments()[0];
		}
		return null;
	}

	public static Class<?> getArrayType(Type type) {
		if (!isArray(type))
			return null;
		return ((Class<?>) type).getComponentType();
	}

	@SuppressWarnings("rawtypes")
	public static boolean isArray(Type type) {
		return type instanceof Class && ((Class) type).isArray();
	}

	@SuppressWarnings("rawtypes")
	public static boolean isList(Type type) {
		return type instanceof Class && List.class.isAssignableFrom((Class) type);
	}

}
