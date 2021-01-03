package common.core.app.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class FilterMessageConverter extends ClassicConverter {
	@Override
	public String convert(ILoggingEvent event) {
		String message = event.getFormattedMessage();
		return filter(event.getLoggerName(), message);
	}

	private String filter(String loggerName, String message) {
		LogMessageFilter filter = LogSettings.get().getLogMessageFilter();
		if (filter != null) {
			return filter.filter(loggerName, message);
		}
		return message;
	}
}
