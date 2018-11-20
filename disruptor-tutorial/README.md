# Disruptor 高性能并发框架

### 为啥快？
+ 基于无锁CAS  
+ > ProducerType.SINGLE和ProducerType.MULTI 用来指定数据生成者有一个还是多个
+ > **BlockingWaitStrategy** 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现；
    **SleepingWaitStrategy** 的性能表现跟 BlockingWaitStrategy 差不多，对 CPU 的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景；
    **YieldingWaitStrategy** 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于 CPU 逻辑核心数的场景中，推荐使用此策略；
                例如，CPU开启超线程的特性。 
                
## ProducerType 
```
 public enum ProducerType
   {
       /**
        *  一个publisher 维护一个 一个ringbuffer 
        */
       SINGLE,
   
       /**
        * 多个event pulisher 维护多个 publisher
        */
       MULTI
   }

````

### 汽车停车场问题


