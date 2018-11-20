package com.kongxiang.delayqueue.wheeltimer.custom.simple;

import java.util.Date;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.delayqueue.wheeltimer.custom.simple
 * @className: delayqueue
 * @author:谭农春
 * @createTime:2018/10/28 21:35
 */
public class SimpleWheelApp {

  public static void main(String[] args) {
    System.err.println( "add at " + new Date());
    ITask task = new MyTask();
    TimingWheelUtils.addTask("TIMEWHEELFOR180S",task);
  }

}
