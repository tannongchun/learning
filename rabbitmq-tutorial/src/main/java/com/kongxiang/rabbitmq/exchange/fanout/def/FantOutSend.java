package com.kongxiang.rabbitmq.exchange.fanout.def;

import com.kongxiang.rabbitmq.until.ConnectionUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


public class FantOutSend {

  /**
   *  1.定义：交换器的名称
   */
  private static  final String EXCHANGE_NAME ="exchange_fanout_demo";

  public static void main(String[] args)  {
    try {
      // 2.创建Connection、通道
      Connection connection = ConnectionUtils.getConnection();
      Channel channel = connection.createChannel();
      // 3.声明交换器类型 fanout(扇出)
      channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
      // 连续发送
      for(int i= 1;i<=10;i++) {
        String message = i+" i send message" ;
        // 4.发送消息
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        Thread.sleep(500);
      }
      // 5.关闭连接、通道
      channel.close();
      connection.close();
    }catch (Exception e){
      e.printStackTrace();
    }
    }

}
