package com.kongxiang.delayqueue.wheeltimer.custom.simple;

/**
 *  执行任务接口
 */
public interface ITask {
  /**
   * 下次执行时间间隔
   * @return
   */
  int getNextOffset();

  /**
   * 判断访问次数是否达到了上限
   * @return
   */
  boolean reachTop();

  /**
   * 执行任务
   */
  void exec(ITask task);

}
