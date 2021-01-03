package common.core.app.monitor.status;

public interface ServiceStatusMonitor {
    ServiceStatus getServiceStatus() throws Exception;

    String getServiceName();
}
