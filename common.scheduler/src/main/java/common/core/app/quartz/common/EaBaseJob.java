package common.core.app.quartz.common;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.app.quartz.model.EaScheduleJobInfo;
import common.core.common.util.StopWatch;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by user on 2016/12/21.
 */
public abstract class EaBaseJob implements Job {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public abstract void task();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		StopWatch stop        = new StopWatch();
		boolean   failureFlag = true;
		String    ip          = "127.0.0.1";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			logger.error("获取IP地址异常", e);
		}

		EaScheduleJobInfo job = (EaScheduleJobInfo) context.getMergedJobDataMap().get("eaScheduleJobInfo");
		logger.info("===========定时任务:JobGroup【{}】--Jobname【{}】--IP；【{}】【开始】===========", job.getJobGroup(), job.getJobName(), ip);

		try {
			task();
		} catch (Exception e) {
			failureFlag = false;
			logger.error("===========定时任务:JobGroup【{}】--Jobname【{}】IP；【{}】【失败】===========。异常信息【{}】",
					job.getJobGroup(), job.getJobName(), ip, e);
		}

		if (failureFlag) {
			long   time = stop.elapsedTime();
			String unit = "毫秒";
			if (time > 1000 && time < 6000) {
				time = time / 1000;
				unit = "秒";

			}
			if (time > 60000) {
				time = time / 60000;
				unit = "分钟";
			}
			if (time > 3600000) {
				time = time / 3600000;
				unit = "小时";
			}

			logger.info("===========定时任务:JobGroup【{}】--Jobname【{}】IP；【{}】【结束】,任务执行时间【{}】【{}】===========",
					job.getJobGroup(), job.getJobName(), ip, time, unit);
		}
	}
}
