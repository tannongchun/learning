package com.kongxiang.delayqueue.method1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *  JDK 实现延时队列
 *   总共 5步
 *   - 1、实现 Delayed 接口
 *   - 2、重写 compareTo 方法 比较getDelay 大小
 *   - 3、重写 getDelay 判断是否过期
 *   - 4、任务放到延时队列
 *   - 5、取出任务执行
 */
// 1、实现 Delayed 接口
public class DelayTask implements Delayed{

  /**
   *  业务id
   *  超时
   */
  private String orderId;
  private long timeout;


  public DelayTask(String orderId, long timeout) {
    this.orderId = orderId;
    // 设置过期的时间
    this.timeout = timeout + System.nanoTime();
  }

  // 2、重写 compareTo 方法
  @Override
  public int compareTo(Delayed delayed) {
    if(delayed == this) {
      return 0;
    }
    DelayTask t = (DelayTask) delayed;

    long d = (getDelay(TimeUnit.NANOSECONDS) - t.getDelay(TimeUnit.NANOSECONDS));

    return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
  }

  // 3、重写 getDelay 判断是否过期
  @Override
  public long getDelay(TimeUnit unit) {
    // <0 过期
    // 获取剩余时间
    return unit.convert(timeout - System.nanoTime(), TimeUnit.NANOSECONDS );
  }

  void print() {
    System.out.println(orderId + " order will being delete...");
  }

  public static void main(String[] args) {
    List<String> list = new ArrayList<String>();
    list.add("001");
    list.add("002");
    list.add("003");
    list.add("004");
    list.add("005");
    // 4.执行任务放置延时队列
    DelayQueue<DelayTask> queue = new DelayQueue<DelayTask>();
    long start = System.currentTimeMillis();
    String  id= "";
    for(int i = 0; i < 5; i++) {
      id = list.get(i);
      queue.put(new DelayTask(id,
          TimeUnit.NANOSECONDS.convert(3, TimeUnit.SECONDS)));
      try {
        // 5、只有在延迟期满时才能从中提取元素
        queue.take().print();
        System.out.println("after: " + (System.currentTimeMillis() - start)
            + " milliSeconds");

      } catch (Exception e) {
      }
    }
  }

}