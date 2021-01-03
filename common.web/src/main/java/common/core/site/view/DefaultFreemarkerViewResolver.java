package common.core.site.view;

import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

public class DefaultFreemarkerViewResolver extends FreeMarkerViewResolver {

	public DefaultFreemarkerViewResolver() {
		super();
	}

	@Override
	public void setPrefix(String prefix) {
		super.setPrefix(prefix);
	}

	@Override
	protected String getPrefix() {
		return super.getPrefix();
	}

}
