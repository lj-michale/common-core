package common.core.site.view;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import common.core.common.util.IoUtil;

import freemarker.cache.TemplateLoader;

public class HTMLEscapeTemplateLoader implements TemplateLoader {
	private final TemplateLoader templateLoader;

	public HTMLEscapeTemplateLoader(TemplateLoader templateLoader) {
		this.templateLoader = templateLoader;
	}

	@Override
	public Object findTemplateSource(String name) throws IOException {
		return templateLoader.findTemplateSource(name);
	}

	@Override
	public long getLastModified(Object templateSource) {
		return templateLoader.getLastModified(templateSource);
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		Reader reader = templateLoader.getReader(templateSource, encoding);
		StringBuilder builder = new StringBuilder("<#escape x as x?html>");
		builder.append(IoUtil.text(reader));
		builder.append("</#escape>");
		return new StringReader(builder.toString());
	}

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		templateLoader.closeTemplateSource(templateSource);
	}
}
