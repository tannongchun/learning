package com.kongxiang.delayqueue.wheeltimer.custom.simple;

import java.util.*;
/**
 * 简单时间轮
 * 简单时间轮只有一个轮子，所以任务的时间间隔不能大于轮子大小,执行任务大于轮子大小，按最大周期来执行
 */
public class SimpleWheel implements IWheel{

  /** 轮子定时任务 */
  private Timer timer;
  /** 轮子 */
  private Set<ITask>[] wheel;
  /** 定时频率间隔(秒)*/
  private int interval;
  /** 轮子大小 */
  private int wheelSize;
  /** 第一次运行隔几个偏移（新加任务隔几个偏移后执行）*/
  private int firstOffset;
  /** 当前执行偏移数 */
  private int currentOffset;
  /**
  * 构造轮子函数 wheelSize:轮子大小，即最大任务周期；interval:轮子行进速度，即指针转动速度;firstOffset:新任务执行偏移量，就是每次任务添加进来后要隔多久才开始执行
  */
  public SimpleWheel(int wheelSize, int interval, int firstOffset) throws Exception{
    if(wheelSize < 0 || interval < 0 || firstOffset < 0){
      throw new Exception("轮子大小、频率、首次间隔必须大于0");
    }
    this.wheelSize = wheelSize;
    this.interval = interval;
    this.firstOffset =firstOffset;
    this.wheel = new HashSet[wheelSize];
    for(int i=0;i<wheelSize;i++){
      this.wheel[i] = new HashSet<ITask>();
    }
    timer = new Timer();
    timer.schedule(new WheelTimerTask(), 0,interval*1000);
  }

  @Override
  public void addTask(ITask task){
    //新任务添加到时间轮的位置，通过首次偏移量加上现在指针的位置加上任务下一次执行的时间的对轮子大小进行求余
    int n = (firstOffset + currentOffset+task.getNextOffset()) % wheelSize;
    wheel[n].add(task);
  }

  @Override
  public void destoryWheel(){
          //取消轮子
          timer.cancel();
          //因为档次的timer会继续执行，所以这样处理后，会尽可能多的中断任务。
           wheel = null;
  }
        //执行任务
  private void processTask(ITask task,Long currentTime,int currentOffset){
    try {
      task.exec(task);
    }catch (Exception e){
      e.printStackTrace();
    }

  }

  // 时间轮维护任务
  class WheelTimerTask extends TimerTask {

    @Override
    public void run() {
      Long currentTime = System.currentTimeMillis();
      int n = currentOffset;
      currentOffset++;
      currentOffset = currentOffset % wheelSize;
      if(wheel[n].size() == 0){
        return;
      }
      // 每隔一秒执行一次
      Set<ITask> tasks = wheel[n];
      Iterator<ITask> it = tasks.iterator();
      while(it.hasNext()){
        processTask(it.next(),currentTime,n);
        it.remove();
      }
    }
  }
}

