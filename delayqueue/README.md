
## 延时队列 

####  DelayQueue 查看method1包,method2包 

 - 1、实现 Delayed 接口
 - 2、重写 compareTo 方法 比较getDelay 大小
 - 3、重写 getDelay 判断是否过期 
 - 4、任务放到延时队列
 - 5、取出任务执行 

#### 时间轮算法 

 - https://blog.csdn.net/wltsysterm/article/details/79335906
 - https://blog.csdn.net/yang_chen_shi_wo/article/details/46520279
 
####  定期轮询（数据库等）
Timer
ScheduledExecutorService
时间轮(kafka)
RabbitMQ
Quartz
Redis Zset
