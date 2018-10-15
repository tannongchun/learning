package com.kongxiang.quartz.demo2;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @version 1.0
 * @description:
 *  具体执行任务
 * @projectName: com.kongxiang.quartz.demo2
 * @className: quartzzdemo
 * @author:谭农春
 * @createTime:2018/10/13 20:16
 */
public class DemoJob implements Job {
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    System.err.println("hell "+ context.getJobDetail());
  }
}
