package com.kongxiang.tomcat.servlet;
import javax.servlet.http.*;
/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.tomcat.servlet
 * @className: tomcat-servlet
 * @author:谭农春
 * @createTime:2018/10/23 21:41
 */
public class DemoHttpSessionBindingListenter implements   HttpSessionBindingListener {

  @Override
  public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
     // session 添加 属性
    String id = httpSessionBindingEvent.getSession().getId();
    System.err.println( System.currentTimeMillis() + " DemoHttpSessionBindingListenter => valueBound => " + id);
  }

  @Override
  public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
    // session  移除属性值
    String id = httpSessionBindingEvent.getSession().getId();
    System.err.println(System.currentTimeMillis() +  " DemoHttpSessionBindingListenter => valueUnbound => " + id);
  }
}
