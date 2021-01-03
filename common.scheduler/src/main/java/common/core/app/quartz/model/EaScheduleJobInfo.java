package common.core.app.quartz.model;

/**
 * Created by user on 2016/12/14.
 */
public class EaScheduleJobInfo {
	private String jobId;
	private String jobName;
	private String jobGroup;
	private String jobStatus;
	private String cronExpression;
	private String desc;

	public EaScheduleJobInfo(String jobName, String jobGroup) {
		this.jobName = jobName;
		this.jobGroup = jobGroup;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
