package com.kongxiang.rabbitmq.work.roundrobin;

import com.kongxiang.rabbitmq.until.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @version 1.0
 * @description:
 *   消费者 六步走
 * @projectName: com.kongxiang.rabbitmq.simple
 * @className: rabbitmq-tutorial
 * @author:谭农春
 * @createTime:2018/10/26 9:53
 */
public class RecvA {

  // 注意：生产者消费者 队列名称要一致
  private static  final String QUEUE_NAME ="work_queue_name1";


  public static void main(String[] args) {

    try { // 1.创建Connection
      Connection connection = ConnectionUtils.getConnection();
      // 2.创建Channel
      final Channel channel  = connection.createChannel();
      // 3.声明队列
      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      // 4.创建消费者
      final Consumer consumer = new DefaultConsumer(channel){
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope,
                                   AMQP.BasicProperties properties, byte[] body)
            throws IOException {
          String message = new String(body, "UTF-8");
          System.out.println(" [A] Received '" + message + "'");
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      };
      // 自动应答模式 - 容易造成数据丢失
      boolean isAck = true;
      //5.绑定
      channel.basicConsume(QUEUE_NAME,isAck,consumer);
//      channel.close();
//      connection.close();
    }catch (Exception e){
      e.printStackTrace();
    }
    finally{

    }

  }
}
