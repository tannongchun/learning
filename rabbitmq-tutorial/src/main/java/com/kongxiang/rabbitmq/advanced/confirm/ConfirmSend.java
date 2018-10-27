package com.kongxiang.rabbitmq.advanced.confirm;

import com.kongxiang.rabbitmq.until.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @version 1.0
 * @description: 生产者 消息保障-> 生成者确认机制
 * @projectName: com.kongxiang.rabbitmq.simple
 * @className: rabbitmq-tutorial
 * @author:谭农春
 * @createTime:2018/10/26 9:53
 */
public class ConfirmSend {

  private static final String QUEUE_NAME = "queue.confirm.name";

  public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
    // 1.创建Connection
    Connection connection = ConnectionUtils.getConnection();
    // 2.创建Channel
    final  Channel channel = connection.createChannel();
    // 3.声明队列
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    //将信道置为publisher confirm 模式
    channel.confirmSelect();
    String message ="";
     //
    for(int i=0;i<100;i++) {
       message = " I send confirm message" + i;
      // 4.发送消息 PERSISTENT_TEXT_PLAIN 明文传输
      channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
      Thread.sleep(2000);
      System.out.println("[confirm] send message  ==> " + message);
      if(i == 2 ){
        new Thread(new Runnable() {
          @Override
          public void run() {
            try {
              Thread.sleep(10000);
              channel.close();
            } catch (IOException e) {
              e.printStackTrace();
            } catch (TimeoutException e) {
              e.printStackTrace();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }).start();

      }
      // 确保发送到RabbitMQ服务器
      if (!channel.waitForConfirms()) {
        System.out.println("send message failed ==> " + message);
      }
    }

  }
}
