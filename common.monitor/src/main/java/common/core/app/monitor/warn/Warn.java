package common.core.app.monitor.warn;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Warn {
	@XmlElement(name = "warnStatus")
	private WarnStatus warnStatus;

	@XmlElementWrapper(name = "services")
	@XmlElement(name = "service")
	private List<ServiceWarnDetail> serviceDetails;

	public WarnStatus getWarnStatus() {
		return warnStatus;
	}

	public void setWarnStatus(WarnStatus warnStatus) {
		this.warnStatus = warnStatus;
	}

	public List<ServiceWarnDetail> getServiceDetails() {
		return serviceDetails;
	}

	public void setServiceDetails(List<ServiceWarnDetail> serviceDetails) {
		this.serviceDetails = serviceDetails;
	}

}
