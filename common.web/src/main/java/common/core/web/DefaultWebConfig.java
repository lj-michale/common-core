package common.core.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.xml.transform.Source;

import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.annotation.ErrorsMethodArgumentResolver;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.MapMethodProcessor;
import org.springframework.web.method.annotation.RequestHeaderMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestPartMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import common.core.app.context.ConfigContext;
import common.core.app.lang.CharSet;
import common.core.app.lang.ContentType;
import common.core.common.converter.BigDecimalConverter;
import common.core.common.converter.DoubleConverter;
import common.core.common.converter.FloatConverter;
import common.core.common.converter.IntegerConverter;
import common.core.common.converter.LongConverter;
import common.core.common.converter.SqlDateConverter;
import common.core.common.converter.SqlTimeConverter;
import common.core.common.converter.SqlTimestampConverter;
import common.core.common.converter.StringArrayConverter;
import common.core.common.converter.StringListConverter;
import common.core.common.converter.UtilDateConverter;
import common.core.site.resolver.DefaultExceptionHandler;
import common.core.site.resolver.DefaultExceptionHandlerExceptionResolver;
import common.core.site.resolver.DefaultHandlerExceptionResolver;
import common.core.site.resolver.ExceptionHandler;
import common.core.site.scheme.SchemeEnforceInterceptor;
import common.core.site.session.SessionFilter;
import common.core.site.tag.AutocompleteTag;
import common.core.site.tag.BodyTag;
import common.core.site.tag.CdnTag;
import common.core.site.tag.CssTag;
import common.core.site.tag.DateTag;
import common.core.site.tag.FileUploadTag;
import common.core.site.tag.HtmlEditorTag;
import common.core.site.tag.ImageTag;
import common.core.site.tag.IncludeTag;
import common.core.site.tag.JsRenderTag;
import common.core.site.tag.JsTag;
import common.core.site.tag.MasterTag;
import common.core.site.tag.NumberFormatTag;
import common.core.site.tag.PaginationTag;
import common.core.site.tag.TagNames;
import common.core.site.tag.UrlTag;
import common.core.site.tag.VersionCdnTag;
import common.core.site.view.DefaultFreeMarkerConfigurer;
import common.core.site.view.DefaultFreemarkerView;
import common.core.site.view.DefaultFreemarkerViewResolver;
import common.core.site.view.DefaultLiteDeviceResolver;
import common.core.site.view.DefaultRequestResponseBodyMethodProcessor;
import common.core.site.view.DefaulteDeviceDelegatingViewResolver;
import common.core.web.context.ContextFilter;
import common.core.web.context.WebSetting;
import common.core.web.converter.JsonHttpMessageConverter;
import common.core.web.converter.XmlHttpMessageConverter;
import common.core.web.form.AnnotationFormArgumentResolver;
import common.core.web.http.HttpStatus;
import common.core.web.interceptor.ActionInterceptor;
import common.core.web.interceptor.AnnotationProcessor;
import common.core.web.interceptor.AnnotationProcessorInterceptor;
import common.core.web.interceptor.DefaultDeviceResolverHandlerInterceptor;
import common.core.web.interceptor.HandlerInterceptor;
import common.core.web.interceptor.QueueRequiredAnnotationInterceptor;
import common.core.web.p3p.P3pAnnotationProcessor;
import freemarker.core.Configurable;
import freemarker.template.Configuration;

public abstract class DefaultWebConfig implements WebMvcConfigurer {
	@Inject
	ConfigurableBeanFactory beanFactory;

	static {
		System.setProperty("org.apache.tomcat.util.http.ServerCookie.ALLOW_HTTP_SEPARATORS_IN_V0", "true");
		System.setProperty("org.apache.tomcat.util.http.ServerCookie.ALLOW_EQUALS_IN_VALUE", "true");
		System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new LongConverter());
		registry.addConverter(new IntegerConverter());
		registry.addConverter(new FloatConverter());
		registry.addConverter(new DoubleConverter());
		registry.addConverter(new BigDecimalConverter());
		registry.addConverter(new UtilDateConverter());
		registry.addConverter(new SqlDateConverter());
		registry.addConverter(new SqlTimestampConverter());
		registry.addConverter(new SqlTimeConverter());
		registry.addConverter(new StringArrayConverter());
		registry.addConverter(new StringListConverter());
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.addAll(messageConverters());
	}

	@Override
	public Validator getValidator() {
		return null;
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorParameter(true);
		configurer.favorPathExtension(true);
		configurer.ignoreAcceptHeader(true);
		configurer.mediaType("xml", MediaType.APPLICATION_XML);
		configurer.mediaType("json", MediaType.APPLICATION_JSON);
		configurer.parameterName("_format");
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
	}

	@Bean
	public DefaultRequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor() {
		DefaultRequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor = new DefaultRequestResponseBodyMethodProcessor(messageConverters());
		return requestResponseBodyMethodProcessor;
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		returnValueHandlers.add(requestResponseBodyMethodProcessor());
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(exceptionHandlerExceptionResolver());
		exceptionResolvers.add(defaultHandlerExceptionResolver());
		exceptionResolvers.add(simpleMappingExceptionResolver());
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		return null;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(actionInterceptor());
		registry.addInterceptor(deviceResolverHandlerInterceptor());
		registry.addInterceptor(schemeEnforceInterceptor());
		registry.addInterceptor(annotationProcessorInterceptor());
		registry.addInterceptor(queueRequiredAnnotationInterceptor());
		registry.addInterceptor(handlerInterceptor());
		
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/robots.txt").addResourceLocations("/");
		registry.addResourceHandler("/favicon.ico").addResourceLocations("/");

		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	}

	@Bean
	public DefaultExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver() {
		DefaultExceptionHandlerExceptionResolver defaultExceptionHandlerExceptionResolver = new DefaultExceptionHandlerExceptionResolver();
		defaultExceptionHandlerExceptionResolver.setMessageConverters(messageConverters());
		defaultExceptionHandlerExceptionResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return defaultExceptionHandlerExceptionResolver;
	}

	@Bean
	public DefaultHandlerExceptionResolver defaultHandlerExceptionResolver() {
		DefaultHandlerExceptionResolver defaultHandlerExceptionResolver = new DefaultHandlerExceptionResolver();
		defaultHandlerExceptionResolver.setOrder(Ordered.LOWEST_PRECEDENCE + 100);
		return defaultHandlerExceptionResolver;
	}

	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
		simpleMappingExceptionResolver.setDefaultStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
		simpleMappingExceptionResolver.setDefaultErrorView(WebSetting.get().getErrorView());
		simpleMappingExceptionResolver.setOrder(Ordered.HIGHEST_PRECEDENCE - 100);
		// HIGHEST_PRECEDENCE
		Properties mappings = new Properties();
		// 处理404
		mappings.setProperty(NoHandlerFoundException.class.getName(), WebSetting.get().getNotFoundPageView());
		mappings.setProperty(Throwable.class.getName(), WebSetting.get().getErrorView());
		simpleMappingExceptionResolver.setExceptionMappings(mappings);

		Properties statusCodes = new Properties();
		statusCodes.setProperty(WebSetting.get().getNotFoundPageView(), "404");
		simpleMappingExceptionResolver.setStatusCodes(statusCodes);

		return simpleMappingExceptionResolver;
	}

	@Bean
	public FreeMarkerConfigurer defaultFreeMarkerConfigurer() {
		FreeMarkerConfigurer freeMarkerConfigurer = new DefaultFreeMarkerConfigurer();
		Properties settings = new Properties();
		settings.setProperty(Configuration.DEFAULT_ENCODING_KEY, CharSet.DEFAULT_CHAR_SET_STRING);
		settings.setProperty(Configurable.URL_ESCAPING_CHARSET_KEY, CharSet.DEFAULT_CHAR_SET_STRING);
		settings.setProperty(Configurable.NUMBER_FORMAT_KEY, "0.######");
		settings.setProperty(Configurable.TEMPLATE_EXCEPTION_HANDLER_KEY, "rethrow");
		settings.setProperty(Configuration.LOCALIZED_LOOKUP_KEY, "false");
		settings.setProperty(Configurable.DATE_FORMAT_KEY, "yyyy-MM-dd");
		settings.setProperty(Configurable.DATETIME_FORMAT_KEY, "yyyy-MM-dd HH:mm:ss");
		settings.setProperty(Configurable.TIME_FORMAT_KEY, "yyyy-MM-dd HH:mm:ss");
		settings.setProperty(Configurable.CLASSIC_COMPATIBLE_KEY, "true");
		freeMarkerConfigurer.setFreemarkerSettings(settings);
		freeMarkerConfigurer.setTemplateLoaderPath("classpath:/templates");
		freeMarkerConfigurer.setFreemarkerVariables(buildFreemarkerVariables());
		return freeMarkerConfigurer;
	}

	protected Map<String, Object> buildFreemarkerVariables() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(TagNames.TAG_BODY, new BodyTag());
		variables.put(TagNames.TAG_CDN, new CdnTag());
		variables.put(TagNames.TAG_CSS, new CssTag());
		variables.put(TagNames.TAG_IMAGE, new ImageTag());
		variables.put(TagNames.TAG_JS, new JsTag());
		variables.put(TagNames.TAG_JS_RENDER, new JsRenderTag());
		variables.put(TagNames.TAG_MASTER, new MasterTag());
		variables.put(TagNames.TAG_URL, new UrlTag());
		variables.put(TagNames.TAG_INCLUDE, new IncludeTag());
		variables.put(TagNames.TAG_VERSION_CDN, new VersionCdnTag());
		variables.put(TagNames.TAG_SELECT, new common.core.site.tag.select.SelectTag());
		variables.put(TagNames.TAG_PAGINATION, new PaginationTag());
		variables.put(TagNames.TAG_DATE, new DateTag());
		variables.put(TagNames.TAG_AUTOCOMPLETE, new AutocompleteTag());
		variables.put(TagNames.TAG_FILEUPLOAD, new FileUploadTag());
		variables.put(TagNames.TAG_NUMBER_FORMAT, new NumberFormatTag());
		variables.put(TagNames.TAG_HTML_EDITOR, new HtmlEditorTag());
		return variables;
	}

	@Bean
	public ViewResolver viewResolver() {
		FreeMarkerViewResolver freeMarkerViewResolver = new DefaultFreemarkerViewResolver();
		// freeMarkerViewResolver.setPrefix("/WEB-INF/templates/");
		// freeMarkerViewResolver.setSuffix(".ftl");
		freeMarkerViewResolver.setContentType(ContentType.TEXT_HTML_UTF8);
		freeMarkerViewResolver.setViewClass(DefaultFreemarkerView.class);
		freeMarkerViewResolver.setExposeSpringMacroHelpers(false);
		freeMarkerViewResolver.setExposeRequestAttributes(true);
		freeMarkerViewResolver.setAllowRequestOverride(true);
		freeMarkerViewResolver.setExposeSessionAttributes(false);
		freeMarkerViewResolver.setCache(ConfigContext.getBooleanValue("view.cache"));
		LiteDeviceDelegatingViewResolver resolver = new DefaulteDeviceDelegatingViewResolver(freeMarkerViewResolver);
		resolver.setNormalPrefix("/default/");
		resolver.setMobilePrefix("/mobile/");
		resolver.setTabletPrefix("/mobile/");
		resolver.setNormalSuffix(".ftl");
		resolver.setMobileSuffix(".mob");
		resolver.setTabletSuffix(".mob");
		return resolver;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

		// Annotation-based argument resolution
		argumentResolvers.add(new RequestParamMethodArgumentResolver(beanFactory, false));
		argumentResolvers.add(new RequestParamMapMethodArgumentResolver());
		argumentResolvers.add(new PathVariableMethodArgumentResolver());
		argumentResolvers.add(new ServletModelAttributeMethodProcessor(false));
		argumentResolvers.add(new DefaultRequestResponseBodyMethodProcessor(messageConverters()));
		argumentResolvers.add(new RequestPartMethodArgumentResolver(messageConverters()));
		argumentResolvers.add(new RequestHeaderMethodArgumentResolver(beanFactory));
		argumentResolvers.add(new RequestHeaderMapMethodArgumentResolver());
		argumentResolvers.add(new ExpressionValueMethodArgumentResolver(beanFactory));
		argumentResolvers.add(new AnnotationFormArgumentResolver());

		// Type-based argument resolution
		argumentResolvers.add(new MapMethodProcessor());
		argumentResolvers.add(new ErrorsMethodArgumentResolver());
	}

	public List<HttpMessageConverter<?>> messageConverters() {
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setWriteAcceptCharset(false);
		ArrayList<MediaType> textTypes = new ArrayList<>();
		textTypes.add(MediaType.TEXT_PLAIN);
		textTypes.add(MediaType.TEXT_HTML);
		textTypes.add(MediaType.TEXT_XML);
		textTypes.add(MediaType.APPLICATION_XML);
		textTypes.add(MediaType.APPLICATION_JSON);
		stringConverter.setSupportedMediaTypes(textTypes);
		converters.add(stringConverter);
		converters.add(new JsonHttpMessageConverter());
		converters.add(new ByteArrayHttpMessageConverter());
		converters.add(new ResourceHttpMessageConverter());
		converters.add(new SourceHttpMessageConverter<Source>());
		converters.add(new AllEncompassingFormHttpMessageConverter());
		converters.add(new XmlHttpMessageConverter());
		// converters.add(new Jaxb2RootElementHttpMessageConverter());
		// MappingJackson2HttpMessageConverter jsonConverter = new
		// MappingJackson2HttpMessageConverter();
		// jsonConverter.setObjectMapper(JsonBinder.getObjectMapper());
		// converters.add(jsonConverter);
		// converters.add(new JavaScriptHttpMessageConverter());
		return converters;
	}

	@Bean
	public DefaultLiteDeviceResolver defaultLiteDeviceResolver() {
		DefaultLiteDeviceResolver defaultLiteDeviceResolver = new DefaultLiteDeviceResolver(ConfigContext.getStringValue("web.forceDevice"));
		return defaultLiteDeviceResolver;
	}

	@Bean
	public DefaultDeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
		DefaultDeviceResolverHandlerInterceptor defaultDeviceResolverHandlerInterceptor = new DefaultDeviceResolverHandlerInterceptor(defaultLiteDeviceResolver());
		return defaultDeviceResolverHandlerInterceptor;
	}

	@Bean
	public HandlerInterceptor handlerInterceptor() {
		return new HandlerInterceptor();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public AnnotationProcessorInterceptor annotationProcessorInterceptor() {
		AnnotationProcessorInterceptor annotationProcessorInterceptor = new AnnotationProcessorInterceptor();

		AnnotationProcessor p3pAnnotationProcessor = new P3pAnnotationProcessor();
		annotationProcessorInterceptor.addAnnotationProcessor(p3pAnnotationProcessor);
		return annotationProcessorInterceptor;
	}

	public SchemeEnforceInterceptor schemeEnforceInterceptor() {
		return new SchemeEnforceInterceptor();
	}

	@Bean
	public ActionInterceptor actionInterceptor() {
		return new ActionInterceptor();
	}

	@Bean
	public QueueRequiredAnnotationInterceptor queueRequiredAnnotationInterceptor() {
		return new QueueRequiredAnnotationInterceptor();
	}
	
	@Bean
	public ExceptionHandler exceptionHandler() {
		return new DefaultExceptionHandler();
	}

	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE)
	public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setForceEncoding(true);
		characterEncodingFilter.setEncoding(CharEncoding.UTF_8);

		FilterRegistrationBean<CharacterEncodingFilter> registration = new FilterRegistrationBean<>();
		registration.setOrder(1);
		registration.addUrlPatterns("/*");
		registration.setFilter(characterEncodingFilter);
		return registration;
	}

	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE)
	public FilterRegistrationBean<ContextFilter> contextFilter() {
		FilterRegistrationBean<ContextFilter> registration = new FilterRegistrationBean<>();
		registration.setOrder(2);
		registration.addUrlPatterns("/*");
		registration.setFilter(new ContextFilter());
		return registration;
	}

	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE)
	public FilterRegistrationBean<SessionFilter> sessionFilter() {
		FilterRegistrationBean<SessionFilter> registration = new FilterRegistrationBean<>();
		registration.setOrder(3);
		registration.addUrlPatterns("/*");
		registration.setFilter(new SessionFilter());
		return registration;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		return new org.springframework.web.multipart.commons.CommonsMultipartResolver();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#
	 * addCorsMappings(org.springframework.web.servlet.config.annotation.
	 * CorsRegistry)
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).allowedHeaders("Origin, X-Requested-With, Content-Type, Accept").allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS").maxAge(3600);
		WebMvcConfigurer.super.addCorsMappings(registry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#
	 * extendHandlerExceptionResolvers(java.util.List)
	 */
	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.extendHandlerExceptionResolvers(resolvers);
	}

}
