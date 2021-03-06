package com.kongxiang.rabbitmq.exchange.direct;

import com.kongxiang.rabbitmq.until.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Fanout 模式
 *  1.没有申明queue也可以实现。
 *  2.只需要定义6步消费
 */
public class DirectRecvA {

  /**
   *  1.定义：交换器的名称
   */
  private static  final String EXCHANGE_NAME ="exchange_direct_demo";
  private static  final  String route_key1 ="direct_demo_key1";
  public static void main(String[] args) {
    try {
      // 2.创建Connection、 通道
      Connection connection = ConnectionUtils.getConnection();
      Channel channel = connection.createChannel();
      // 3.声明交换器类型DIRECT
      channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
      // 4.获取队列名称
      String queueName = channel.queueDeclare().getQueue();
      // 5.绑定队列与交换器 queue -> exchange -> routekey
      channel.queueBind(queueName, EXCHANGE_NAME, route_key1);
      // 6.消费
      Consumer consumer = new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope,
                        AMQP.BasicProperties properties, byte[] body) throws IOException {
          String message = new String(body, "UTF-8");
          System.out.println(" [1] Received '" + message + "'" + envelope.getDeliveryTag());
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
