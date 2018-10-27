package com.kongxiang.rabbitmq.work.roundrobin;

import com.kongxiang.rabbitmq.until.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @version 1.0
 * @description:
 *   生产者 -> 五步走
 * @projectName: com.kongxiang.rabbitmq.simple
 * @className: rabbitmq-tutorial
 * @author:谭农春
 * @createTime:2018/10/26 9:53
 */
public class WorkSend {
  private static  final String QUEUE_NAME ="work_queue_name1";

  public static void main(String[] args) {
    // 1.创建Connection
    Connection connection = ConnectionUtils.getConnection();
    Channel channel = null;
    try {
      // 2.创建Channel
      channel = connection.createChannel();
      // 3.声明队列
      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      // 发送多条信息
      for(int i =1; i<=60;i++) {
        String message = " i send msg  " +i;
        System.out.println(" [x] Sent '" + message + "'");
        // 4.发送消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        // 休眠10ms
        Thread.sleep(1000);
      }

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
