package com.kongxiang.tomcat.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.tomcat.servlet
 * @className: tomcat-servlet
 * @author:谭农春
 * @createTime:2018/10/23 21:00
 */
public class TestServlet extends HttpServlet{


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    resp.setHeader("Content-type", "text/html;charset=UTF-8");
    // 设置字符编码
    resp.setCharacterEncoding("UTF-8");
    // 设置编码格式
   String contextStr =   this.getServletContext().getInitParameter("name");
    //如果当前reqeust中的HttpSession 为null，
    // 当create为true，就创建一个新的Session，否则返回null；
    HttpSession session = req.getSession();
    //单位为秒  15后对比两次请求的sessionid 是否一样
    session.setMaxInactiveInterval(15);
    // 绑定属性
    session.setAttribute("bountdemo",new DemoHttpSessionBindingListenter());
    // 返回数据到前端浏览器
    resp.getWriter().write(contextStr + session.getId());
  }

}
