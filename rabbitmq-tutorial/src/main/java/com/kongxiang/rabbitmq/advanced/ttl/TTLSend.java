package com.kongxiang.rabbitmq.advanced.ttl;

import com.kongxiang.rabbitmq.until.ConnectionUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.util.HashMap;
import java.util.Map;


public class TTLSend {



  public static void main(String[] args)  {
    try {
      // 2.创建Connection、通道
      Connection connection = ConnectionUtils.getConnection();
      Channel channel = connection.createChannel();
      // 设置死信队列
      channel.exchangeDeclare("exchange.dlx", BuiltinExchangeType.DIRECT , false);
      // 正常业务处理
      channel.exchangeDeclare( "exchange.normal" , BuiltinExchangeType.FANOUT , false);
      // 配置TTL->DLX
      Map<String, Object> argMap = new HashMap<String, Object>();
      // 设置队列过期时间 dlx  10s
      argMap.put("x-message-ttl", 10000);
      argMap.put("x-dead-letter-exchange","exchange.dlx");
      argMap.put("x-dead-letter-routing-key","routingkey");
      // 配置路由
      channel.queueDeclare("queue.normal" ,false ,false ,false , argMap);
      channel.queueBind("queue.normal" , "exchange.normal" , "routingkey");
      // 死信消息=>私信交换器
      channel.queueDeclare("queue.d1x", true, false, false, null) ;
      channel.queueBind("queue.d1x", "exchange.dlx", "routingkey");
      String messsage =" TTL DEMO TEST ..... ";
      // 正常消息发送的位置 存文本发送
      channel.basicPublish("exchange.normal", "routingkey", MessageProperties.PERSISTENT_TEXT_PLAIN, messsage.getBytes("utf-8")) ;
      System.out.println(" [ x]  send " + messsage);
      // 5.关闭连接、通道
      channel.close();
      connection.close();
    }catch (Exception e){
      e.printStackTrace();
    }
    }

}
