package com.example.demo;

/**
 * @version 1.0
 * @description:
 * @projectName: com.example.demo
 * @className: spring-qps-reids
 * @author:谭农春
 * @createTime:2018/10/22 21:55
 */
public class ServieException extends  RuntimeException {
  private String msg ;

  public ServieException(String msg) {
    super(msg);
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
