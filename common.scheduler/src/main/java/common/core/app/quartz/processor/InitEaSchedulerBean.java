package common.core.app.quartz.processor;

import common.core.app.quartz.model.EaScheduleJobInfo;
import common.core.app.quartz.utils.JobHandle;
import org.quartz.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by user on 2016/12/14.
 */
@Component
public class InitEaSchedulerBean implements InitializingBean {
	@Autowired
	SchedulerFactoryBean schedulerFactoryBean;

	@Override
	public void afterPropertiesSet() throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		for(EaScheduleJobInfo job : JobHandle.getJobInfos()){
			TriggerKey  triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
			CronTrigger trigger    = (CronTrigger) scheduler.getTrigger(triggerKey);

			System.out.printf("=========================");
			System.out.printf(job.getJobGroup() + "#" + job.getJobName());
			System.out.printf("=========================");

			if(null == trigger){
				JobDetail jobDetail = JobBuilder.
						newJob(JobHandle.getJobClassRepository().get(job.getJobGroup() + "#" + job.getJobName())).
						withIdentity(job.getJobName(), job.getJobGroup()).build();

				jobDetail.getJobDataMap().put("eaScheduleJobInfo", job);

				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
				scheduler.scheduleJob(jobDetail, trigger);
			} else{
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		}
	}
}
