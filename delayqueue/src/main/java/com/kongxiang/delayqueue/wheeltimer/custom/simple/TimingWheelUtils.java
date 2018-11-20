package com.kongxiang.delayqueue.wheeltimer.custom.simple;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 时间轮工具类
 */
public class TimingWheelUtils {
    //保证线程安全 存储所有的轮子
   private static Map<String,IWheel> wheels = new ConcurrentHashMap<>();
   public static Map<String, IWheel> getWheels() {
      return wheels;
    }

  static {
    try {
         //类初始化时候自动创建时间轮
         createSimpleWheel("TIMEWHEELFOR180S",181,3,1);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 创建一个简单时间轮
   * @param wheelKey   时间轮key
   * @param wheelSize 轮子大小
   * @param interval 定时频率间隔(秒)
   * @param firstOffset 新任务首次执行隔几个偏移
   */
  public static void createSimpleWheel(String wheelKey,int wheelSize, int interval, int firstOffset) throws Exception {
    if(wheels.get(wheelKey) != null){
      throw new Exception("此键已存在时间轮,键名="+wheelKey);
    }
    SimpleWheel simpleWheel = new SimpleWheel(wheelSize,interval,firstOffset);
    wheels.put(wheelKey,simpleWheel);
  }

  /**
   * 添加任务
   * @param wheelKey 时间轮key
   * @param task  任务
   * @return
   */
  public static boolean addTask(String wheelKey, ITask task){
    if(wheels.get(wheelKey) == null){
      return false;
    }
    IWheel wheel = wheels.get(wheelKey);
    wheel.addTask(task);
    return true;
  }

  /**
   * 移除时间轮
   * @param wheelKey
   */
  public static void destory(String wheelKey){
    if(wheels.get(wheelKey) == null){
      return;
    }
    wheels.get(wheelKey).destoryWheel();
    wheels.remove(wheelKey);
  }

}
