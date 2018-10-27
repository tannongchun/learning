package com.kongxiang.delayqueue.method2;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.delayqueue.method2
 * @className: delayqueue
 * @author:谭农春
 * @createTime:2018/10/27 20:54
 */
public class DelayTask<T> implements Delayed {

  // 具体的业务任务
  private T task;

  //过期时长
  private long timeout;

  // timeout 是纳秒数
  public DelayTask(long timeout,T task) {
    this.task = task;
    this.timeout = TimeUnit.NANOSECONDS.convert(timeout, TimeUnit.SECONDS) + System.nanoTime();
  }

  @Override
  public long getDelay(TimeUnit unit) {
    return unit.convert(timeout - System.nanoTime(), TimeUnit.NANOSECONDS );
  }

  @Override
  public int compareTo(Delayed delayed) {
    if(delayed == this) {
      return 0;
    }
    DelayTask t = (DelayTask) delayed;

    long d = (getDelay(TimeUnit.NANOSECONDS) - t.getDelay(TimeUnit.NANOSECONDS));

    return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
  }


  public T  getTask() {
    return task;
  }

  public void setTask(T  task) {
    this.task = task;
  }
}
