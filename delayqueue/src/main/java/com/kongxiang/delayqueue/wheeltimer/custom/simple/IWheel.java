package com.kongxiang.delayqueue.wheeltimer.custom.simple;

/**
 * 时间轮接口
 */
public interface IWheel {

  /** 添加任务 */
  void addTask(ITask task);
  /** 注销轮子 */
  void destoryWheel();
}
