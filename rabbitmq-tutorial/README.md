# RabbitMQ Tutorials 实例

- 当前使用版本:RabbitMQ 3.6.12

### 安装 RabbitMQ 

- Web 管理界面 http://localhost:15672/  (默认)账号/密码:guest / guest
   
### 简单模式

 - 生成者 5 步  
 - 消费者  6 步 

### Work 工作组模式（集群消费模式）
 - Fair 模式
 
 > 处理快的先处理 
 
 - Round-robbin 轮询模式 
 
 > 轮流处理，处理快的需要等处理慢的先执行 
   
  
   
### Exchange 四种类型

####   direct 

####   topic 

####   headers 

####   fanout

    - 多个消费者 消费一样的数据 
    - 需要关联的多个消费者一起消费完成才算消费  
    - 会忽视掉routekey 
    
### Dead Letter Exchanges 实现延时队列

> 消息的TTL和死信Exchange
- 使用场景：超过30分钟未支付取消订单

### 消息不可达

> mandatory 和 immediate 是channel.basicPublish 方法中的两个参数，它们都有
  当消息传递过程中不可达目的地时将消息返回给生产者的功能。
 - ================================================================= 
> mandatory 参数告诉服务器至少将该消息路由到一个队列中， 否则将消息返
  回给生产者。
  
>  immediate 参数告诉服务器， 如果该消息关联的队列上有消费者， 则立刻投递:
  如果所有匹配的队列上都没有消费者，则直接将消息返还给生产者， 不用将消息存入队列而等
  待消费者了。
  >> RabbitMQ 3 .0 版本开始去掉了对imrnediate 参数的支持，对此RabbitMQ 官方解释是:
     imrnediate 参数会影响镜像队列的性能， 增加了代码复杂性，


### 过期时间 

#### 消息 TTL x-message-ttl
-  ttl 过期时间单位毫秒
-  ttl 的写法 
````
      第一种写法： 所有消息过期时间一致。消息过期，就会从队列中抹去
        Map<String, Object> args = new HashMap<String,Object>();
        args.put("x-message-ttl" ,6000);
        channel.queueDeclare(queueName,durable,exclusive,autoDelete,args);
        
     第二种写法： 消息过期时间不一致 。每条消息是否过期是在即将投递到消费者之前判定的。
         AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
         builder.deliveryMode(2);//持久化消息
         builder.expiration("60000");//设置TTL=60000ms
         AMQP.BasicProperties properties = builder.build();
         channel.basicPublish(exchangeName,routingKey,mandatory,properties,"ttlTestMessage".getBytes());
   
   第三种写法： 消息过期时间不一致 。每条消息是否过期是在即将投递到消费者之前判定的。
       AMQP.BasicProperties properties = newAMQP.BasicProperties();
       Properties.setDeliveryMode(2);
       properties.setExpiration("60000");
       channel.basicPublish(exchangeName,routingKey,mandatory,propert工es,"ttlTestMessage".getBytes());
            

````

### 队列过期 x-expires

- 说明

> 控制队列被自动删除前处于未使用状态的时间

- x-expires 用法 

````
    Map<String,Object> args = newHashMap<String,Object>();
     // 30分钟 
      args.put("x-expires",1800000);
        
````

### 死信队列 Dead-Letter-Exchange

- Dead-Letter-Exchange 用法

````
    // 创建DLX: dlx_exchange
    channel.exchangeDeclare("dlx_exchange","direct"); 
    Map<String, Object> args = new HashMap<String, Object>();
    // 指定死信队列
    args.put("x-dead-letter-exchange", " dlx exchange ");
    // 指定死信路由key
    args.put("x-dead-letter-routing-key", "dlx-routing-key");
    //为队列myqueue 添加DLX
    channel.queueDeclare("myqueue", false, false, false, args);
    
````    

####  TTL DLX 的完整用法

```
    // 设置死信队列
    channel.exchangeDeclare("exchange.dlx", "direct" , true);
    // 正常业务处理
    channel.exchangeDeclare( "exchange.normal " , " fanout " , true);
    // 配置TTL->DLX
    Map<String, Object> args = new HashMap<String, Object>();
    // 设置队列过期时间 dlx  10s 不可用x-expires
    args.put("x-message-ttl", 10000);
    args.put("x-dead-letter-exchange" , "exchange.dlx");
    args.put("x-dead-letter-routing-key" , "routingkey");
    // 配置路由
    channe1.queueDec1are("queue.norma1 " ,true ,fa1se , fa1se , args);
    channe1.queueBind("queue.normal " , "exchange.normal" , "");
    
    // 死信消息=>私信交换器 
    channe1.queueDec1are("queue.d1x" , true , false , false , null) ;
    channel.queueBind("queue.dlx" , "exchange.dlx " , "routingkey");
    
    // 正常消息发送的位置
    channel.basicPublish("exchange.normal", "routingkey",MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes()) ;

```

### 消息追踪

- 方便排查错误
- 步骤如下：
    - 停掉服务
    - 执行 rabbitmq-plugins enable rabbitmq_tracing（windows 下面若无权限写。先修改为写权限 默认位置：C:\Program Files\erl9.1\lib\rabbitmq_server-3.6.12）
    - 在web 页面多出tracing 栏目

### 生产者确认

#### 事务机制

- channel.txSelect 、channel.txCommit 和channel.txRollback
- 例子
 ```
 // 开启事务 
    channel.txSelect();
    try{
    channel.basicPublish(EXCHANGE NAME, ROUTING KEY , MessageProperties.PERSISTENT_TEXT_PLAIN,
    "transaction messages".getBytes());
    // 提交 
    channel.txCommit();
    }catch(Exception e){
    // 回滚
     channel.txRollback();
     e.printStackTrace();
    }
 
 ```

#### 发送方确认机制

> 在使用RabbitMQ 的时候，可以通过消息持久化操作来解决因为服务器的异常崩溃而导致
  的消息丢失，除此之外，我们还会遇到一个问题，当消息的生产者将消息发送出去之后，消息
  到底有没有正确地到达服务器呢?如果不进行特殊配置，默认情况下发送消息的操作是不会返
  回任何信息给生产者的，也就是默认情况下生产者是不知道消息有没有正确地到达服务器。如
  果在消息到达服务器之前己经丢失，持久化操作也解决不了这个问题，因为消息根本没有到达
  服务器，何谈持久化? 
  
  
- 交换器没有匹配的队列，那么消息也会丢失。
- 例子 
````
    try {
    	//将信道置为publisher confirm 模式
    	channel.confirmSelect() ; 
    	//之后正常发送消息
    	channel.basicPublish("exchange", "routingKey",null ,"publisher confirm test".getBytes());
         if(!channel.waitForConfirms()) {
            System.out.println( "send message failed" ) ;
        } 
    }
    catch (InterruptedException e) (
    	e.printStackTrace() ;
    }

````

### 消息保障机制
>  事务机制和publisher confirm 机制确保的是消息能够正确地发送至RabbitMQ ，这里的"发送至RabbitMQ" 的含义是指消息被正确地发往至RabbitMQ 的交换器，如果此交换器没有
  匹配的队列，那么消息也会丢失。所以在使用这两种机制的时候要确保所涉及的交换器能够有匹配的队列. 更进一步地讲，发送方要配合mandatory 参数或者备份交换器一起使用来提高
  消息传输的可靠性。
  
- 1) 消息生产者需要开启事务机制或者publisher confirm 机制，以确保消息可以可靠地传输到RabbitMQ 中。
- 2) 消息生产者需要配合使用mandatory 参数或者备份交换器来确保消息能够从交换器路由到队列中，进而能够保存下来而不会被丢弃。
- 3) 消息和队列都需要进行持久化处理，以确保RabbitMQ 。
- 4) 消费者在消费消息的同时需要将autoAck 设置为false ，然后通过手动确认服务器在遇到异常情况时不会造成消息丢失的方式去确认己经正确消费的消息，以避免在消费端引起不必要的消息丢失。

### FAQ

- 1.未持久化的，MQ服务重启之 Exchange 和 Queue 都不存在。

- 2.省略了诸如连接管理，错误处理，连接恢复，并发和度量收集之类的主题。

- 3.TTL 和 DLX 实现延时消息。比如：30分钟未支付取消。30分钟内手动支付该怎么处理呢？

### 参考

- RatbbitMQ 官网

- RabbitMQ 实战指南

