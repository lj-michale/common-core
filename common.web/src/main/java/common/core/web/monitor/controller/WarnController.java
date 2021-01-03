package common.core.web.monitor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.app.monitor.status.ServiceStatus;
import common.core.app.monitor.status.ServiceStatusMonitor;
import common.core.app.monitor.warn.ServerWarn;
import common.core.app.monitor.warn.ServiceWarnDetail;
import common.core.app.monitor.warn.ServiceWarnMonitor;
import common.core.app.monitor.warn.Warn;
import common.core.app.monitor.warn.WarnStatus;
import common.core.common.util.ApplicationContextUtil;
import common.core.common.util.StopWatch;
import common.core.site.controller.BaseRestController;
import common.core.site.view.BaseRestResult;

@RestController
public class WarnController extends BaseRestController {
	private final static Logger LOGGER = LoggerFactory.getLogger(WarnController.class);

	@RequestMapping(value = "/system-admin/monitor/warn", method = RequestMethod.GET)
	@ResponseBody
	public BaseRestResult<Warn> monitorStatus() {
		Warn warn = new Warn();
		List<ServiceWarnDetail> serviceDetails = new ArrayList<ServiceWarnDetail>();
		warn.setServiceDetails(serviceDetails);
		warn.setWarnStatus(WarnStatus.NONE);

		buildStatus(warn);
		buildWarn(warn);

		BaseRestResult<Warn> result = BaseRestResult.buildRestResult(warn);
		result.setAction(warn.getWarnStatus().name());
		return result;
	}

	private void buildWarn(Warn warn) {
		List<ServiceWarnMonitor> monitors = ApplicationContextUtil.getBeans(ServiceWarnMonitor.class);
		for (ServiceWarnMonitor monitor : monitors) {
			StopWatch watch = new StopWatch();
			ServiceWarnDetail detail = new ServiceWarnDetail();
			detail.setName(monitor.getServiceName());
			try {
				ServerWarn serverWarn = monitor.getServerWarn();
				detail.setWarnMessage(serverWarn.getWarnMessage());
				detail.setWarnStatus(serverWarn.getWarnStatus());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				detail.setWarnMessage(e.getClass().getName() + " " + e.getMessage());
				detail.setWarnStatus(WarnStatus.ERROR);
			} finally {
				detail.setElapsedTime(watch.elapsedTime());
			}

			addServiceWarnDetail(warn, detail);
		}
	}

	private void buildStatus(Warn warn) {
		List<ServiceStatusMonitor> monitors = ApplicationContextUtil.getBeans(ServiceStatusMonitor.class);
		for (ServiceStatusMonitor monitor : monitors) {
			StopWatch watch = new StopWatch();
			ServiceWarnDetail detail = new ServiceWarnDetail();
			detail.setName(monitor.getServiceName());
			try {
				ServiceStatus serviceStatus = monitor.getServiceStatus();
				detail.setWarnMessage(monitor.getServiceName());
				detail.setWarnStatus(ServiceStatus.DOWN.equals(serviceStatus) ? WarnStatus.ERROR : WarnStatus.DEBUG);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				detail.setWarnMessage(e.getClass().getName() + " " + e.getMessage());
				detail.setWarnStatus(WarnStatus.ERROR);
			} finally {
				detail.setElapsedTime(watch.elapsedTime());
			}

			addServiceWarnDetail(warn, detail);
		}
	}

	private void addServiceWarnDetail(Warn warn, ServiceWarnDetail detail) {
		// 增加
		warn.getServiceDetails().add(detail);
		if (detail.getWarnStatus().compareTo(warn.getWarnStatus()) > 0) {// 警告级别提高则取最后级别
			warn.setWarnStatus(detail.getWarnStatus());
		}
	}

}
