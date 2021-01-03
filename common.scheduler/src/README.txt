1.在AppConfig中注入SchedulerFactoryBean
2.定时任务需要继承EaBaseJob，并在类上加上@EaJob注解
3.实现EaBaseJob的task方法，在该方法中实现业务逻辑，并加上@EaScheduled(cron = "*/5 * * * * ?")注解。
暂时只支持cron表达式
