package common.core.app.monitor.status;

public class ServiceStatusDetail {
	private String name;
    private ServiceStatus status;
    private long elapsedTime;
    private String errorMessage;

    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
