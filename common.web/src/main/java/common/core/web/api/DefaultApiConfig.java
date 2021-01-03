package common.core.web.api;

import org.springframework.context.annotation.Bean;

import common.core.app.context.ConfigContext;
import common.core.web.api.config.ApiSetting;
import common.core.web.api.interceptor.ApiAnnotationProcessorInterceptor;

public abstract class DefaultApiConfig {

	@Bean
	public ApiAnnotationProcessorInterceptor apiAnnotationProcessorInterceptor() {
		ApiAnnotationProcessorInterceptor apiAnnotationProcessorInterceptor = new ApiAnnotationProcessorInterceptor();
		this.addApiAnnotationProcessors(apiAnnotationProcessorInterceptor);
		return apiAnnotationProcessorInterceptor;
	}

	@Bean
	public ApiSetting apiSetting() {
		ConfigContext.bindConfigObject(ApiSetting.get());
		return ApiSetting.get();
	}

	protected void addApiAnnotationProcessors(ApiAnnotationProcessorInterceptor apiAnnotationProcessorInterceptor) {
	}
}
