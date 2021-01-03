package common.core.app.quartz.test;

import common.core.app.quartz.annotation.EaJob;
import common.core.app.quartz.annotation.EaScheduled;
import common.core.app.quartz.common.EaBaseJob;
import org.springframework.stereotype.Component;

/**
 * Created by user on 2016/12/29.
*/
@EaJob("testTask")
@Component
public class TestTask extends EaBaseJob {
	@EaScheduled(cron = "*/5 * * * * ?")
	@Override
	public void task() {
		System.out.printf("==============TestTask startup============");
	}
}
