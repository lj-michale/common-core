package common.core.app.monitor.warn;

public class ServiceWarnDetail {

	private String name;
	private WarnStatus warnStatus = WarnStatus.NONE;
	private String warnMessage;
	private long elapsedTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWarnMessage() {
		return warnMessage;
	}

	public void setWarnMessage(String warnMessage) {
		this.warnMessage = warnMessage;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public WarnStatus getWarnStatus() {
		return warnStatus;
	}

	public void setWarnStatus(WarnStatus warnStatus) {
		this.warnStatus = warnStatus;
	}

}
