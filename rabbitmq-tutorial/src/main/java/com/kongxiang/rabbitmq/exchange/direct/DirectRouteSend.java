package com.kongxiang.rabbitmq.exchange.direct;

import com.kongxiang.rabbitmq.until.ConnectionUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


public class DirectRouteSend {

  /**
   *  1.定义：交换器的名称
   */
  private static  final String EXCHANGE_NAME ="exchange_direct_demo";

 private static  final  String route_key1 ="direct_demo_key1";
 private static  final  String route_key2 ="direct_demo_key2";

  public static void main(String[] args)  {
    try {
      // 2.创建Connection、通道
      Connection connection = ConnectionUtils.getConnection();
      Channel channel = connection.createChannel();
      // 3.声明交换器类型 DIRECT
      channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
      // 连续发送
      for(short i= 1;i<=10;i++) {

        String message = i+" i send direct" ;
          // 4.发送消息
        if( (i & 1)==0) {
          channel.basicPublish(EXCHANGE_NAME, route_key1, null, message.getBytes());
         }
        else {
          channel.basicPublish(EXCHANGE_NAME, route_key2, null, message.getBytes());
        }
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
