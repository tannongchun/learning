package com.kongxiang.rabbitmq.simple;

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
public class Recv {
  // 注意：生产者消费者 队列名称要一致
  private static  final String QUEUE_NAME ="simple_queue_name1";


  public static void main(String[] args) {
    // 1.创建Connection
    Connection connection = ConnectionUtils.getConnection();
    Channel channel = null;
    try {
      // 2.创建Channel
      channel = connection.createChannel();
      // 3.声明队列
      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      // 4.创建消费者
     final Consumer consumer = new DefaultConsumer(channel){
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope,
                                                AMQP.BasicProperties properties, byte[] body)
            throws IOException {
          String message = new String(body, "UTF-8");
          System.out.println(" [x] Received '" + message + "'");
        }
      };
      //5.绑定
      channel.basicConsume(QUEUE_NAME,true,consumer);


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
