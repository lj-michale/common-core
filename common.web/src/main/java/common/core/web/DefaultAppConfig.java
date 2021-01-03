package common.core.web;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import common.core.app.advice.DefaultAspectAdvice;
import common.core.app.context.Message;
import common.core.app.monitor.status.ServerStatus;
import common.core.common.http.HttpClients;
import common.core.web.monitor.UrlMonitorManager;

public class DefaultAppConfig {
	@Inject
	protected ConfigurableEnvironment env;
	@Inject
	protected ApplicationContext applicationContext;

	@Bean
	public DefaultAspectAdvice logAspectAdvice() {
		return new DefaultAspectAdvice();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placehodlerConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	Message messages() throws IOException {
		Resource[] messageResources = new PathMatchingResourcePatternResolver()
				.getResources("classpath*:messages/*.properties");
		Message messages = new Message();
		String[] baseNames = new String[messageResources.length];
		for (int i = 0, messageResourcesLength = messageResources.length; i < messageResourcesLength; i++) {
			Resource messageResource = messageResources[i];
			String filename = messageResource.getFilename();
			baseNames[i] = "messages/" + filename.substring(0, filename.indexOf('.'));
		}
		messages.setBasenames(baseNames);
		return messages;
	}

	@Bean
	public ServerStatus serverStatus() {
		return new ServerStatus();
	}

	@Bean
	public HttpClients httpClients() {
		return new HttpClients();
	}

	@Bean
	public UrlMonitorManager urlMonitorManager() {
		UrlMonitorManager urlMonitorManager = new UrlMonitorManager();
		urlMonitorManager.init(env.getProperty("monitor.urls"));
		return urlMonitorManager;
	}
}
