package common.core.web.monitor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.app.monitor.status.ServiceStatusMonitor;
import common.core.app.monitor.status.ServiceStatus;
import common.core.app.monitor.status.ServiceStatusDetail;
import common.core.app.monitor.status.Status;
import common.core.common.util.ApplicationContextUtil;
import common.core.common.util.StopWatch;
import common.core.site.controller.BaseRestController;
import common.core.site.view.BaseRestResult;

@RestController
public class StatusController extends BaseRestController {
	private final Logger logger = LoggerFactory.getLogger(StatusController.class);

	@RequestMapping(value = "/system-admin/monitor/status", method = RequestMethod.GET)
	@ResponseBody
	public BaseRestResult<Status> monitorStatus() {
		Status status = new Status();
		List<ServiceStatusDetail> serviceDetails = new ArrayList<ServiceStatusDetail>();
		status.setServiceDetails(serviceDetails);
		status.setServer(ServiceStatus.UP);

		List<ServiceStatusMonitor> monitors = ApplicationContextUtil.getBeans(ServiceStatusMonitor.class);
		for (ServiceStatusMonitor monitor : monitors) {
			StopWatch watch = new StopWatch();
			ServiceStatusDetail detail = new ServiceStatusDetail();
			detail.setName(monitor.getServiceName());
			serviceDetails.add(detail);
			try {
				ServiceStatus serviceStatus = monitor.getServiceStatus();
				detail.setStatus(serviceStatus);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				detail.setErrorMessage(e.getClass().getName() + " " + e.getMessage());
				detail.setStatus(ServiceStatus.DOWN);
			} finally {
				detail.setElapsedTime(watch.elapsedTime());
			}
			if (!ServiceStatus.UP.equals(detail.getStatus())) {
				status.setServer(ServiceStatus.DOWN);
			}
		}
		BaseRestResult<Status> result = BaseRestResult.buildRestResult(status);
		result.setAction(status.getServer().name());
		return result;
	}

}
