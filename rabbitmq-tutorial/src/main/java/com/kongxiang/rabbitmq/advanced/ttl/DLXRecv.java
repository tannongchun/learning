package com.kongxiang.rabbitmq.advanced.ttl;

import com.kongxiang.rabbitmq.until.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Fanout 模式
 *  1.没有申明queue也可以实现。
 *  2.只需要定义6步消费
 */
public class DLXRecv {

  /**
   *  1.定义：交换器的名称、路由、队列
   */
  private static  final String EXCHANGE_NAME ="exchange.dlx";
  private static  final  String ROUTE_KEY ="routingkey";
  private static  final  String QUEUE_NAME ="queue.d1x";

  public static void main(String[] args) {
    try {
      //  2.创建Connection、 通道 DLX
      Connection connection = ConnectionUtils.getConnection();
      Channel channel = connection.createChannel();
      //  3.声明交换器类型DIRECT DLX
      channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
      // 创建队列
      channel.queueDeclare(QUEUE_NAME , true , false , false , null) ;
      //  4.绑定队列与交换器 queue -> exchange -> routekey
      channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTE_KEY);
      // 6.消费
      Consumer consumer = new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope,
                        AMQP.BasicProperties properties, byte[] body) throws IOException {
                 // 处理具体的业务
                 doWork(body);
        }
      };
      // 自动应答模式
      channel.basicConsume(QUEUE_NAME, true, consumer);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  private static void doWork(byte[] body) {
    String message = null;
    try {
      message = new String(body, "UTF-8");
      System.out.println(" [1] DLX Received '" + message + "'");
      Thread.sleep(1000);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
