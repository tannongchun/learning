package com.kongxiang.delayqueue.method2;


import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DelayContext {

  //缓存线程池
  ExecutorService executor = Executors.newCachedThreadPool();
  // 维护线程
  private Thread daemonThead;
 // 延迟队列
  private DelayQueue<DelayTask<MyServiceTask>> queues = new DelayQueue<DelayTask<MyServiceTask>>();
  private DelayContext(){}

  private static class  DelayContextHolder{
     private static  final  DelayContext instance = new DelayContext();
  }
 // 单例模式
  public static  DelayContext getInstance(){
    return  DelayContextHolder.instance;
  }
  // 启动维护线程

  public  void init(){
    daemonThead = new Thread(()->{
      execute();
    });
    // 启动维护线程
    daemonThead.start();
  }

  private void execute() {
    while (true){
      // 队列取出
      MyServiceTask mytask = null;
      try {
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        String name = Thread.currentThread().getState().name();
        System.out.println(String.format("线程数: %s -- 队列数: %s  -- 线程状态: %s",String.valueOf(map.size()),String.valueOf(queues.size()),name));
        mytask = queues.take().getTask();
        // 提交到线程池
        executor.execute(mytask);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


  public void add(DelayTask<MyServiceTask> myTask){
    queues.add(myTask);
  }

}
