package com.kongxiang.delayqueue.method2;


public class DelayTaskApp {

  public static void main(String[] args) throws InterruptedException {
    // 获取运行环境
    DelayContext context = DelayContext.getInstance();
    // 初始化环境
    context.init();
    // 添加队列
    context.add(new DelayTask<MyServiceTask>(60L,new MyServiceTask(" id123456789")));
  }

}
