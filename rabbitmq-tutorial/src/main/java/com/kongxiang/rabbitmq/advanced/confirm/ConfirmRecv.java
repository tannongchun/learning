package com.kongxiang.rabbitmq.advanced.confirm;

import com.kongxiang.rabbitmq.until.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @version 1.0
 * @description:
 *   消费者 六步走
 * @projectName: com.kongxiang.rabbitmq.simple
 * @className: rabbitmq-tutorial
 * @author:谭农春
 * @createTime:2018/10/26 9:53
 */
public class ConfirmRecv {

  // 注意：生产者消费者 队列名称要一致
  private static  final String QUEUE_NAME ="queue.confirm.name";

  public static void main(String[] args) throws IOException {
    // 1.创建Connection
    Connection connection = ConnectionUtils.getConnection();
      // 2.创建Channel
    final   Channel channel = connection.createChannel();
      // 3.声明队列
      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      // 4.创建消费者
     final Consumer consumer = new DefaultConsumer(channel){
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties, byte[] body)
            throws IOException {

          String message = new String(body, "UTF-8");
          System.out.println(" [x] Received '" + message + "'");
          // 交换器没有匹配的队列,那么消息也会丢失。
          channel.basicAck(envelope.getDeliveryTag(),false);
        }
      };
      //5.绑定 手动确定
      channel.basicConsume(QUEUE_NAME,false,consumer);

  }
}
