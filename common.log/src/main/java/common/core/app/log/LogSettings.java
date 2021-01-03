package common.core.app.log;

import javax.xml.bind.annotation.XmlElement;

import common.core.app.context.ConfigContext;

public class LogSettings {
	private static final LogSettings INSTANCE = new LogSettings();

	static {
		ConfigContext.bindConfigObject(INSTANCE);
	}

	@XmlElement(name = "log.enableTraceLog")
	private boolean enableTraceLog = true;

	@XmlElement(name = "log.alwaysWriteTraceLog")
	private boolean alwaysWriteTraceLog = false;

	private LogMessageFilter logMessageFilter;

	@XmlElement(name = "log.trackMaxTime")
	private long trackMaxTime = Long.MAX_VALUE;

	public static LogSettings get() {
		return INSTANCE;
	}

	public long getTrackMaxTime() {
		return trackMaxTime;
	}

	public void setTrackMaxTime(long trackMaxTime) {
		this.trackMaxTime = trackMaxTime;
	}

	public boolean isEnableTraceLog() {
		return enableTraceLog;
	}

	public void setEnableTraceLog(boolean enableTraceLog) {
		this.enableTraceLog = enableTraceLog;
	}

	public boolean isAlwaysWriteTraceLog() {
		return alwaysWriteTraceLog;
	}

	public void setAlwaysWriteTraceLog(boolean alwaysWriteTraceLog) {
		this.alwaysWriteTraceLog = alwaysWriteTraceLog;
	}

	public LogMessageFilter getLogMessageFilter() {
		return logMessageFilter;
	}

	public void setLogMessageFilter(LogMessageFilter logMessageFilter) {
		this.logMessageFilter = logMessageFilter;
	}
}
