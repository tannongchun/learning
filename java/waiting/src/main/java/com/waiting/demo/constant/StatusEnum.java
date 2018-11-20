package com.waiting.demo.constant;

/**
 * @version 1.0
 * @description:
 * @projectName: com.waiting.demo.constant
 * @className: waiting
 * @author:谭农春
 * @createTime:2018/11/12 20:52
 */
public enum StatusEnum {
     NO("否定"), YES("肯定"),;
     private final  String msg ;

     StatusEnum(String msg) {
          this.msg = msg;
     }

     public String getMsg() {
          return msg;
     }
}
