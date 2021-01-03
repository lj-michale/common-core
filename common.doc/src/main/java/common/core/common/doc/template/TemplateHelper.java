package common.core.common.doc.template;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import common.core.app.lang.CharSet;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.core.Configurable;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateHelper {

	public static void processClassPathTempate(String tempateClassPath, Map<String, Object> data, Writer writer) {
		// 通过指定模板名获取FreeMarker模板实例
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_24);
		configuration.setTemplateLoader(new ClassTemplateLoader(TemplateHelper.class, "/"));
		Properties settings = getSettings();
		Template template;
		try {
			configuration.setSettings(settings);
			template = configuration.getTemplate(tempateClassPath, "utf-8");
			// FreeMarker通过Map传递动态数据
			template.process(data, writer);
		} catch (IOException | TemplateException e) {
			throw new TemplateProcessException(e.getMessage(), e);
		}
	}

	public static String processClassPathTempate(String tempateClassPath, Map<String, Object> data) {
		StringWriter stringWriter = new StringWriter();
		TemplateHelper.processClassPathTempate(tempateClassPath, data, stringWriter);
		return stringWriter.toString();
	}

	public static String processStringTemplate(String tempateString, Map<String, Object> data) {
		// 通过指定模板名获取FreeMarker模板实例
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_24);
		StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
		stringTemplateLoader.putTemplate(tempateString, tempateString);
		configuration.setTemplateLoader(stringTemplateLoader);
		Properties settings = getSettings();
		Template template;
		StringWriter stringWriter = new StringWriter();
		try {
			configuration.setSettings(settings);
			template = configuration.getTemplate(tempateString);
			// FreeMarker通过Map传递动态数据
			template.process(data, stringWriter);
		} catch (IOException | TemplateException e) {
			throw new TemplateProcessException(e.getMessage(), e);
		}
		return stringWriter.toString();
	}

	private static Properties getSettings() {
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
		return settings;
	}
}
