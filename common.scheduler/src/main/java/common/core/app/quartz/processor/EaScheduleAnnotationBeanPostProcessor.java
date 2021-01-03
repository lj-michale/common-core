package common.core.app.quartz.processor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import common.core.app.quartz.annotation.EaJob;
import common.core.app.quartz.annotation.EaScheduled;
import common.core.app.quartz.annotation.EaSchedules;
import common.core.app.quartz.common.EaBaseJob;
import common.core.app.quartz.utils.JobHandle;

/**
 * Created by user on 2016/12/29.
 */
@Component
public class EaScheduleAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered, EmbeddedValueResolverAware, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent>, DisposableBean {

	private Object scheduler;

	private StringValueResolver embeddedValueResolver;

	private ApplicationContext applicationContext;

	public Object getScheduler() {
		return scheduler;
	}

	public void setScheduler(Object scheduler) {
		this.scheduler = scheduler;
	}

	public StringValueResolver getEmbeddedValueResolver() {
		return embeddedValueResolver;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void destroy() throws Exception {

	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class<?> targetClass = AopUtils.getTargetClass(bean);

		if (targetClass.isAnnotationPresent(EaJob.class) && bean instanceof EaBaseJob) {
			EaJob job = targetClass.getAnnotation(EaJob.class);

			Map<Method, Set<EaScheduled>> annotatedMethods = MethodIntrospector.selectMethods(targetClass, new MethodIntrospector.MetadataLookup<Set<EaScheduled>>() {
				@Override
				public Set<EaScheduled> inspect(Method method) {
					Set<EaScheduled> scheduledMethods = AnnotationUtils.getRepeatableAnnotations(method, EaScheduled.class, EaSchedules.class);
					return (!scheduledMethods.isEmpty() ? scheduledMethods : null);
				}
			});

			JobHandle.registEaScheduleJobInfo((Class<? extends EaBaseJob>) targetClass, job, annotatedMethods);
		}
		/*
		 * Class<?> targetClass = AopUtils.getTargetClass(bean);
		 * if(targetClass.isAnnotationPresent(EaJob.class) && bean instanceof
		 * EaBaseJob) { EaJob job = targetClass.getAnnotation(EaJob.class);
		 * JobHandle.jobRegist(job.group() + "#" + job.value(),
		 * (EaBaseJob)bean); }
		 */

		return bean;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

	}

	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.embeddedValueResolver = resolver;
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
