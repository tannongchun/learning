package com.kongxiang.delayqueue.wheeltimer.netty;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.Date;
import java.util.concurrent.TimeUnit;

// 基于Netty 框架实现时间轮片算法
public class WheelTimerApp {

  public static void main(String[] args) throws InterruptedException {
    // 创建时间轮片
    HashedWheelTimer timer = new HashedWheelTimer(12, //时间轮一圈的长度
        TimeUnit.SECONDS,
        12);//时间轮的度刻
    System.out.println( " at " + new Date() + " start");
    timer.start();
    //创建任务
//    for(int i =0 ;i<10 ;i ++ ) {

      // 3 秒执行 提交任务
      int finalI = 0;
      System.out.println( finalI + " add " + new Date() + "任务执行" );
    // 每隔12秒执行一次
      timer.newTimeout(new TimerTask() {
        @Override
        public void run(Timeout timeout) throws Exception {
          System.out.println( finalI + " run " + new Date() + "任务执行" );
        }
      }, 0, TimeUnit.SECONDS);

//    }
    // 停止
  }
}
