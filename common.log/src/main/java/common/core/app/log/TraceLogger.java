package common.core.app.log;

import java.io.IOException;
import java.lang.reflect.Method;

import org.slf4j.MDC;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class TraceLogger {
	private static final TraceLogger INSTANCE = new TraceLogger();

	public static TraceLogger get() {
		return INSTANCE;
	}

	final InheritableThreadLocal<LoggingEventProcessor> loggingEventProcessor = new InheritableThreadLocal<>();
	private PatternLayout layout;
	private String logFolder;

	public LoggingEventProcessor getLoggingEventProcessor() {
		return loggingEventProcessor.get();
	}

	public void setLoggingEventProcessor(LoggingEventProcessor loggingEventProcessor) {
		this.loggingEventProcessor.set(loggingEventProcessor);
	}

	public void process(ILoggingEvent event) throws IOException {
		LoggingEventProcessor processor = this.getLoggingEventProcessor();

		// ignore if current thread log is not explicitly initialized, for
		// processor == null
		if (processor != null && LogSettings.get().isEnableTraceLog()) {
			processor.process(event);
		}
	}

	public void initialize() {
		MDC.clear();
		this.setLoggingEventProcessor(new LoggingEventProcessor(layout, logFolder));
	}

	public void cleanup(boolean forceFlushTraceLog) {
		try {
			LoggingEventProcessor processor = this.getLoggingEventProcessor();
			processor.cleanup(forceFlushTraceLog || LogSettings.get().isAlwaysWriteTraceLog());
		} catch (IOException e) {
			ExceptionTraceUtil.stackTrace(e);
			throw new RuntimeException(e);
		}
	}

	public void clearAll() {
		loggingEventProcessor.remove();
	}

	public void setLayout(PatternLayout layout) {
		this.layout = layout;
	}

	public void setLogFolder(String logFolder) {
		this.logFolder = logFolder;
	}

	public static void putContext(String key, String val) {
		MDC.put(key, val);
	}

	public static boolean isTimeOut(Long time, Method method) {
		long trackMaxTime = LogSettings.get().getTrackMaxTime();
		if (null != method) {
			TraceLoggerConfig traceLoggerConfig = method.getAnnotation(TraceLoggerConfig.class);
			if (traceLoggerConfig == null) {
				traceLoggerConfig = method.getDeclaringClass().getAnnotation(TraceLoggerConfig.class);
			}
			if (traceLoggerConfig != null) {
				trackMaxTime = traceLoggerConfig.trackMaxTime();
			}
		}
		if (time > trackMaxTime)
			return true;
		return false;
	}
}
