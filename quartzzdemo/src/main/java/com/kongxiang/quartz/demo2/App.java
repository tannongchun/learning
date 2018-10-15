package com.kongxiang.quartz.demo2;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.quartz.demo2
 * @className: quartzzdemo
 * @author:谭农春
 * @createTime:2018/10/13 20:17
 */
public class App {

  // 创建文件服务器
  public static void main(String[] args) throws SchedulerException {
    // 1.创建调度器
    SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    Scheduler scheduler = schedulerFactory.getScheduler();
    // 2.创建执行job
    JobDetail job = newJob(DemoJob.class).withIdentity("job1", "group1").build();
    // 3.创建Cron调度时间 20秒 执行一次
    CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1").withSchedule(cronSchedule("0/20 * * * * ?"))
        .build();
    // 4.绑定 调度任务和调度时间 返回执行结束时间
    Date ft = scheduler.scheduleJob(job, trigger);
    System.err.println(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: "
        + trigger.getCronExpression());
    // 5.启动调度任务
    scheduler.start();
  }
}
