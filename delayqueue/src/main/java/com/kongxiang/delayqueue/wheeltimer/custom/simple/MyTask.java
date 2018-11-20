package com.kongxiang.delayqueue.wheeltimer.custom.simple;

import java.util.Date;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.delayqueue.wheeltimer.custom.simple
 * @className: delayqueue
 * @author:谭农春
 * @createTime:2018/10/28 21:36
 */
public class MyTask implements  ITask {
  @Override
  public int getNextOffset() {
    return 0;
  }

  @Override
  public boolean reachTop() {
    return false;
  }

  @Override
  public void exec(ITask task) {
    System.err.println( "run at " + new Date());
  }
}
