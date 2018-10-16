package com.kongxiang.aop.service;

/**
 * @version 1.0
 * @description:
 *      服务类
 * @projectName: com.kongxiang.aop.service
 * @className: spring-aop
 * @author:谭农春
 * @createTime:2018/10/16 23:04
 */
public class MathCalcu {

  public int Div(int i, int y) {
    int a = i/y;
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return a;
  }
}
