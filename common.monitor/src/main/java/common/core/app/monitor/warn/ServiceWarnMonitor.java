package common.core.app.monitor.warn;

public interface ServiceWarnMonitor {
	ServerWarn getServerWarn() throws Exception;

	String getServiceName();
}
