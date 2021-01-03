package common.core.web.monitor;

import javax.inject.Inject;

import common.core.app.monitor.status.ServiceStatusMonitor;
import common.core.app.monitor.status.ServiceStatus;
import common.core.common.http.HTTPResponse;
import common.core.common.http.HttpClients;

public class UrlMonitor implements ServiceStatusMonitor {

	@Inject
	private HttpClients httpClients;

	private String url;

	public HttpClients getHttpClients() {
		return httpClients;
	}

	public void setHttpClients(HttpClients httpClients) {
		this.httpClients = httpClients;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public ServiceStatus getServiceStatus() throws Exception {
		HTTPResponse resp = httpClients.getAndResponse(url, null);
		if (resp.statusCode().isSuccess()) {
			return ServiceStatus.UP;
		} else {
			throw new RuntimeException("Http response status code: " + resp.statusCode().code());
		}
	}

	@Override
	public String getServiceName() {
		return url;
	}

}
