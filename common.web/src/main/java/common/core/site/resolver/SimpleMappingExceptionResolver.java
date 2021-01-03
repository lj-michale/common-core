package common.core.site.resolver;

import javax.servlet.http.HttpServletRequest;

import common.core.web.context.WebSetting;

public class SimpleMappingExceptionResolver extends org.springframework.web.servlet.handler.SimpleMappingExceptionResolver {
	private ExceptionHandler exceptionHandler = new DefaultExceptionHandler();

	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		exceptionHandler.handler(ex);
	}

	public SimpleMappingExceptionResolver() {
		super();
		for (int statusCode = 400; statusCode < 450; statusCode++) {
			this.addStatusCode(WebSetting.get().getNotFoundPageView(), statusCode);
		}
		for (int statusCode = 500; statusCode < 550; statusCode++) {
			this.addStatusCode(WebSetting.get().getErrorView(), statusCode);
		}
	}

}
