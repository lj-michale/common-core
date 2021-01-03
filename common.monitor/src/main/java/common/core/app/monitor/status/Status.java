package common.core.app.monitor.status;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Status {
	@XmlElement(name = "server")
	private ServiceStatus server;

	@XmlElementWrapper(name = "services")
	@XmlElement(name = "service")
	private List<ServiceStatusDetail> serviceDetails;

	public ServiceStatus getServer() {
		return server;
	}

	public void setServer(ServiceStatus server) {
		this.server = server;
	}

	public List<ServiceStatusDetail> getServiceDetails() {
		return serviceDetails;
	}

	public void setServiceDetails(List<ServiceStatusDetail> serviceDetails) {
		this.serviceDetails = serviceDetails;
	}

}
