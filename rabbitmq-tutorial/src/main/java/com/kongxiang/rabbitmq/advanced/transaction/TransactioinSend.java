package com.kongxiang.rabbitmq.advanced.transaction;

import com.kongxiang.rabbitmq.until.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @version 1.0
 * @description:
 *    生产者 消息保障->事务机制
 * @projectName: com.kongxiang.rabbitmq.simple
 * @className: rabbitmq-tutorial
 * @author:谭农春
 * @createTime:2018/10/26 9:53
 */
public class TransactioinSend {

  private static  final String QUEUE_NAME ="queue.transactioin.name";

  public static void main(String[] args) {
    // 1.创建Connection
    Connection connection = ConnectionUtils.getConnection();
    Channel channel = null;
    try {
      // 2.创建Channel
      channel = connection.createChannel();
      // 3.声明队列
      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      // 启动事务
      channel.txSelect();
      try {
        String message = "hello ,world  transactioin";
        // 4.发送消息 PERSISTENT_TEXT_PLAIN 明文传输
        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        // 抛出异常
        // 1.消费者是否还能消费？ 不消费
        // 2.队列里面是否会有这条消息呢？ // 队列不添加消息
         int i = 1/0;
        // 增加4次网络请求
        channel.txCommit();
      }catch ( Exception e){
        // 消息事务回滚
        channel.txRollback();
        e.printStackTrace();
      }
    }catch (Exception e){
        e.printStackTrace();
    }
    finally{
      // 5.关闭连接、通道
      try {
        if(null != channel) {
          channel.close();
        }
        if(null != connection){
          connection.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (TimeoutException e) {
        e.printStackTrace();
      }

    }

  }
}
