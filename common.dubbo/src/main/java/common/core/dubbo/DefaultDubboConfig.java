
package common.core.dubbo;

import org.springframework.context.annotation.Bean;

import com.alibaba.dubbo.config.spring.AnnotationBean;

public abstract class DefaultDubboConfig {

	static {
		// 设置dubbo使用slf4j来记录日志
		System.setProperty("dubbo.application.logger", "slf4j");
	}

	@Bean
	public static AnnotationBean annotationBean() {
		AnnotationBean annotationBean = new AnnotationBean();
		AbstractConfigUtil.appendProperties(annotationBean);
		return annotationBean;
	}

	@Bean
	public ApplicationBean applicationBean() {
		return new ApplicationBean();
	}

	@Bean
	public ProtocolBean protocolBean() {
		return new ProtocolBean();
	}

	@Bean
	public RegistryBean registryBean() {
		return new RegistryBean();
	}

	@Bean
	public MonitorBean monitorBean() {
		return new MonitorBean();
	}

	@Bean
	public ModuleBean moduleBean() {
		return new ModuleBean();
	}

	@Bean
	public ConsumerBean consumerBean() {
		return new ConsumerBean();
	}

	@Bean
	public ProviderBean providerBean() {
		return new ProviderBean();
	}

}