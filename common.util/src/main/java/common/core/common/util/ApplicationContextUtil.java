package common.core.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

public class ApplicationContextUtil {

	private static ApplicationContext APPLICATIONCONTEXT;
	private static Map<Class<?>, List<Method>> METHODS_MAP = new ConcurrentHashMap<Class<?>, List<Method>>();

	public static <T> T createBean(Class<T> beanClass) {
		return APPLICATIONCONTEXT.getAutowireCapableBeanFactory().createBean(beanClass);
	}


	public static ConfigurableEnvironment getConfigurableEnvironment(){
		if (APPLICATIONCONTEXT != null) {
			return (ConfigurableEnvironment) APPLICATIONCONTEXT.getEnvironment();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T initializeBean(T bean) {
		AutowireCapableBeanFactory beanFactory = APPLICATIONCONTEXT.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(bean);
		return (T) beanFactory.initializeBean(bean, bean.getClass().getName());
	}

	public static <T extends Annotation> List<Method> getAnnotationMethods(Class<T> annotationClass) {
		List<Method> methods = METHODS_MAP.get(annotationClass);
		if (null == methods) {
			methods = new ArrayList<Method>();
			Map<String, Object> beans = ApplicationContextUtil.getBeansOfType(Object.class);
			for (Object obj : beans.values()) {
				List<Method> classMethods = ClassUtil.findAnnotationMethods(obj.getClass(), annotationClass);
				if (null == classMethods || classMethods.isEmpty())
					continue;
				methods.addAll(classMethods);
			}
			METHODS_MAP.put(annotationClass, methods);
		}
		return methods;

	}

	public static <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		return APPLICATIONCONTEXT.getBeansOfType(type);
	}

	public static <T> List<T> getBeans(Class<T> type) {
		return new ArrayList<>(ApplicationContextUtil.getBeansOfType(type).values());
	}

	public static Environment getEnvironment() {
		return APPLICATIONCONTEXT.getEnvironment();
	}

	public static Object getBean(String name) throws BeansException {
		return APPLICATIONCONTEXT.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return APPLICATIONCONTEXT.getBean(name, requiredType);
	}

	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return APPLICATIONCONTEXT.getBean(requiredType);
	}

	public static Object getBean(String name, Object... args) throws BeansException {
		return APPLICATIONCONTEXT.getBean(name, args);
	}

	public static <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
		return APPLICATIONCONTEXT.getBean(requiredType, args);
	}

	public static String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
		return APPLICATIONCONTEXT.getBeanNamesForAnnotation(annotationType);
	}

	public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
		return APPLICATIONCONTEXT.getBeansWithAnnotation(annotationType);
	}

	public static <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException {
		return APPLICATIONCONTEXT.findAnnotationOnBean(beanName, annotationType);
	}

	public static void registerSingletonBean(String beanName, Object bean) {
		SingletonBeanRegistry registry = (SingletonBeanRegistry) ApplicationContextUtil.APPLICATIONCONTEXT.getAutowireCapableBeanFactory();
		registry.registerSingleton(beanName, bean);
	}

	public static void registerSingletonBean(String beanName, Class<?> beanClass) {
		GenericBeanDefinition definition = new GenericBeanDefinition();
		definition.setBeanClass(beanClass);
		definition.setScope(BeanDefinition.SCOPE_SINGLETON);
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ApplicationContextUtil.APPLICATIONCONTEXT.getAutowireCapableBeanFactory();
		registry.registerBeanDefinition(beanName, definition);
	}

	public static ApplicationContext getApplicationContext() {
		return ApplicationContextUtil.APPLICATIONCONTEXT;
	}

	public static void putApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextUtil.APPLICATIONCONTEXT = applicationContext;
	}

}
