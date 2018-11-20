# CH7 Redis的噩梦：阻塞

### 发现阻塞
  - ConnectException 
  - slowlog get{n}  获取最近的n条慢查询命令(默认对于执行超过10毫秒的命令)
  
###  阻塞

- 不合理地使用API或数据结构、CPU饱和、持久化阻塞
- CPU竞争、内存交换、网络问题等。

### 内在因素

#### 合理地使用API或数据结构

- 1）修改为低算法度的命令，如hgetall改为hmget等，禁用keys、sort等命令。
- 2）调整大对象：缩减大对象数据或把大对象拆分为多个小对象，防止一次命令操作过多的数据。
    - redis-cli --bigkeys 发现大对象 
    
#### CPU饱和 
- redis-cli --stat
````
    127.0.0.1:6379> info commandstats
    # Commandstats
    cmdstat_info:calls=15,usec=4815,usec_per_call=321.00
    cmdstat_slowlog:calls=2,usec=11,usec_per_call=5.50
    cmdstat_command:calls=2,usec=2662,usec_per_call=1331.00

````
#### 持久化阻塞
- 1.fork阻塞
    - info stats 命令获取到latest_fork_usec指标，表示Redis最近一次fork操作耗时，如果耗时很大，比如超过1秒，则需要做出优化调整，
- 2.AOF刷盘阻塞 
    - info persistence统计中的aof_delayed_fsync指标
    
    
### 外在因素 


