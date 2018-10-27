package com.kongxiang;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang
 * @className: springboot-custom-starter
 * @author:谭农春
 * @createTime:2018/10/27 13:50
 */
public class HelloService {

  private String msg;

  public String sayHello(){
    return  msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
