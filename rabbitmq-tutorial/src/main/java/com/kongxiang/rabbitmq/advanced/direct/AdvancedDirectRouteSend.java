package com.kongxiang.rabbitmq.advanced.direct;

import com.kongxiang.rabbitmq.until.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;


public class AdvancedDirectRouteSend {

  /**
   *  生成者发送找不到路由不到
   */
  private static  final String EXCHANGE_NAME ="exchange_direct_demo1";

 private static  final  String route_key1 ="direct_demo_key_demo";


  public static void main(String[] args)  {
    try {
      // 2.创建Connection、通道
      Connection connection = ConnectionUtils.getConnection();
      Channel channel = connection.createChannel();
      // 3.声明交换器类型 DIRECT
      channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
      // ==>路由不到的情况处理
      channel.addReturnListener(new ReturnListener() {
        @Override
        public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
          String msg = new String(body,"utf-8");
          System.err.println( " routingKey = " + routingKey + " 消息不可达 =>  " + msg);
        }
      });

      // 连续发送
      for(short i= 1;i<=2;i++) {
        String message = i+" i send direct" ;
        // mandatory = true
          channel.basicPublish(EXCHANGE_NAME, route_key1,true, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
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
