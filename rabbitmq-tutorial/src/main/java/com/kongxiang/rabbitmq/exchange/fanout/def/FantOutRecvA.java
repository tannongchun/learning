package com.kongxiang.rabbitmq.exchange.fanout.def;

import com.kongxiang.rabbitmq.until.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Fanout 模式
 *  1.没有申明queue也可以实现。
 *  2.只需要定义6步消费
 */
public class FantOutRecvA {

  /**
   *  1.定义：交换器的名称
   */
  private static  final String EXCHANGE_NAME ="exchange_fanout_demo";

  public static void main(String[] args) {
    try {
      // 2.创建Connection、通道
      Connection connection = ConnectionUtils.getConnection();
      Channel channel = connection.createChannel();
      // 3.声明交换器类型 fanout(扇出)
      channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
      // 4.获取队列名称
      String queueName = channel.queueDeclare().getQueue();
      // 5.绑定队列与交换器
      channel.queueBind(queueName, EXCHANGE_NAME, "");
      // 6.消费
      Consumer consumer = new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope,
                                   AMQP.BasicProperties properties, byte[] body) throws IOException {
          String message = new String(body, "UTF-8");
          System.out.println(" [A] Received '" + message + "'" + envelope.getDeliveryTag());
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      };
      // 自动应答模式
      channel.basicConsume(queueName, true, consumer);
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
