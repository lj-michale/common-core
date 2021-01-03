package common.core.app.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;

public class FilterMessagePatternLayoutEncoder extends PatternLayoutEncoderBase<ILoggingEvent> {
	@Override
	public void start() {
		FilterMessagePatternLayout patternLayout = new FilterMessagePatternLayout();
		patternLayout.setContext(context);
		patternLayout.setPattern(getPattern());
		patternLayout.start();
		this.layout = patternLayout;
		super.start();
	}
}
