package common.core.site.view;

import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;

import common.core.common.util.ApplicationContextUtil;

public class ViewResolverUtil {

	public static ViewResolver getMasterViewResolver() {
		ViewResolver viewResolver = null;
		ApplicationContext context = ApplicationContextUtil.getApplicationContext();
		// Find all ViewResolvers in the ApplicationContext, including ancestor
		// contexts.
		Map<String, ViewResolver> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, ViewResolver.class, true, false);
		for (ViewResolver item : matchingBeans.values()) {
			if (isMasterViewResolver(item)) {
				viewResolver = item;
				break;
			}
		}
		if (null == viewResolver) {
			try {
				viewResolver = context.getBean(DispatcherServlet.VIEW_RESOLVER_BEAN_NAME, ViewResolver.class);
			} catch (NoSuchBeanDefinitionException ex) {
				// Ignore, we'll add a default ViewResolver later.
			}
		}

		return viewResolver;
	}

	private static boolean isMasterViewResolver(ViewResolver item) {
		return item instanceof DefaulteDeviceDelegatingViewResolver;
	}
}
