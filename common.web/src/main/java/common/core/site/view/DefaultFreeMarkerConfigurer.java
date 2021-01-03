package common.core.site.view;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.cache.TemplateLoader;

public class DefaultFreeMarkerConfigurer extends FreeMarkerConfigurer {

	public DefaultFreeMarkerConfigurer() {
		super();
		this.setPreferFileSystemAccess(false);
	}

	@Override
	protected TemplateLoader getTemplateLoaderForPath(String templateLoaderPath) {
		TemplateLoader loader = super.getTemplateLoaderForPath(templateLoaderPath);
		return new HTMLEscapeTemplateLoader(loader);
	}
}
