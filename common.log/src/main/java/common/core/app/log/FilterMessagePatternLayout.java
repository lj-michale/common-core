package common.core.app.log;

import ch.qos.logback.classic.PatternLayout;

import java.util.Map;

public class FilterMessagePatternLayout extends PatternLayout {
	public FilterMessagePatternLayout() {
		super();
		Map<String, String> converters = getInstanceConverterMap();
		converters.put("m", FilterMessageConverter.class.getName());
		converters.put("msg", FilterMessageConverter.class.getName());
		converters.put("message", FilterMessageConverter.class.getName());
	}
}
