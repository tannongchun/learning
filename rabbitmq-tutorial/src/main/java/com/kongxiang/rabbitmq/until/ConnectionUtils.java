package com.kongxiang.rabbitmq.until;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @version 1.0
 * @description:
 *     线程安全单例模式
 * @projectName: com.kongxiang.rabbitmq.until
 * @className: rabbitmq-tutorial
 * @author:谭农春
 * @createTime:2018/10/26 9:55
 */
public class ConnectionUtils {


  // 内部类来做到延迟加载对象，
  // JLS(Java Language Sepcification)会保证线程安全
  private static class SingletonHolder
  {
    public final static ConnectionUtils instance = new ConnectionUtils();
  }

  // 构建私有构造函数
  private ConnectionUtils(){
    // idea 快捷键 soutv
    System.out.println("connection");
//     // 创建对象
    ConnectionFactory factory = new ConnectionFactory();
    // 配置连接信息
    factory.setHost("localhost");
    factory.setVirtualHost("/");
    factory.setPort(5672);
    factory.setUsername("guest");
    factory.setPassword("guest");

  }


  /**
   *  获得请求
   * @return
   */
 public  static Connection getConnection(){

   ConnectionFactory factory = new ConnectionFactory();
   // 配置连接信息
   factory.setHost("localhost");
   factory.setVirtualHost("/");
   factory.setPort(5672);
   factory.setUsername("guest");
   factory.setPassword("guest");
   try {
     return  factory.newConnection();
   } catch (IOException e) {
     e.printStackTrace();
   } catch (TimeoutException e) {
     e.printStackTrace();
   }
   return null;
 }

}
