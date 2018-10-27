package com.kongxiang.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
  读取配置文件
 */
@ConfigurationProperties(prefix = "hello")
public class HelloConfigProperteis {

  private String msg  ="default starter demo ";

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
