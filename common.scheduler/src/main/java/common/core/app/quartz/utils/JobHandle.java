package common.core.app.quartz.utils;

import common.core.app.quartz.annotation.EaJob;
import common.core.app.quartz.annotation.EaScheduled;
import common.core.app.quartz.common.EaBaseJob;
import common.core.app.quartz.model.EaScheduleJobInfo;
import common.core.common.util.DigestUtils;
import common.core.common.util.UuidUtil;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by user on 2016/12/30.
 */
public class JobHandle {
	private static List<EaScheduleJobInfo>                 jobInfoRepository  = new ArrayList<>();
	private static Map<String, Class<? extends EaBaseJob>> jobClassRepository = new HashMap<>();

	public static void jobClassRegist(String id, Class<? extends EaBaseJob> jobClass) {
		jobClassRepository.put(id, jobClass);
	}

	public static Map<String, Class<? extends EaBaseJob>> getJobClassRepository() {
		return jobClassRepository;
	}

	public static void registEaScheduleJobInfo(Class<? extends EaBaseJob> targetClass, EaJob jobAnnotation, Map<Method, Set<EaScheduled>> annotatedMethods) {
		String jobGroup = jobAnnotation.group();
		String jobName  = jobAnnotation.value();
		System.out.printf("jobGroup:" + jobGroup + "----jobName:" + jobName);

		if (!annotatedMethods.isEmpty()) {
			for (Map.Entry<Method, Set<EaScheduled>> entry : annotatedMethods.entrySet()) {
				Set<EaScheduled> set1 = entry.getValue();
				for (EaScheduled scheduled : set1) {
					EaScheduleJobInfo jobInfo = new EaScheduleJobInfo(jobName, jobGroup);
					jobInfo.setJobId(DigestUtils.md5With16(UuidUtil.generateFullUuid()));
					jobInfo.setCronExpression(scheduled.cron());
					jobInfo.setDesc(scheduled.desc());

					jobClassRegist(jobGroup + "#" + jobName, targetClass);
					jobInfoRepository.add(jobInfo);
				}
			}

		}
	}

	public static List<EaScheduleJobInfo> getJobInfos() {
		return jobInfoRepository;
	}
}
