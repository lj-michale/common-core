package common.core.app.log;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

public class TraceAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
	private String logFolder;
	private PatternLayout layout;

	@Override
	public void start() {
		TraceLogger logger = TraceLogger.get();
		logger.setLayout(layout);
		logger.setLogFolder(logFolder);
		super.start();
	}

	@Override
	public void stop() {
		TraceLogger.get().clearAll();
		super.stop();
	}

	@Override
	protected void append(ILoggingEvent event) {
		try {
			TraceLogger.get().process(event);
		} catch (Exception e) {
			addError("failed to write log", e);
		}
	}

	// set on logback.xml
	public void setLogFolder(String logFolder) {
		this.logFolder = logFolder;
	}

	// set on logback.xml
	public void setLayout(PatternLayout layout) {
		this.layout = layout;
	}
}
