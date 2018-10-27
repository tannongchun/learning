package com.kongxiang.delayqueue.method2;

import java.util.Date;
import java.util.concurrent.Delayed;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.delayqueue.method2
 * @className: delayqueue
 * @author:谭农春
 * @createTime:2018/10/27 20:55
 */
public class MyServiceTask implements  Runnable {
  private String id ;

  public MyServiceTask(String id) {
    this.id = id;
  }

  @Override
  public void run() {
    System.err.println( " at "+ new Date()+ " delete id " + id );
  }
}
