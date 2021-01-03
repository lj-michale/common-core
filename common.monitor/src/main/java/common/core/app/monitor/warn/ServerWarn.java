package common.core.app.monitor.warn;

public class ServerWarn {
	private String warnMessage;
	private WarnStatus warnStatus = WarnStatus.NONE;

	public String getWarnMessage() {
		return warnMessage;
	}

	public void setWarnMessage(String warnMessage) {
		this.warnMessage = warnMessage;
	}

	public WarnStatus getWarnStatus() {
		return warnStatus;
	}

	public void setWarnStatus(WarnStatus warnStatus) {
		this.warnStatus = warnStatus;
	}

}
