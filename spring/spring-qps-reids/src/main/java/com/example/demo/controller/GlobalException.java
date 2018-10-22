package com.example.demo.controller;

import com.example.demo.ServieException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

/**
 * @version 1.0
 * @description:
 *      配置全局异常
 * @projectName: com.example.demo.controller
 * @className: spring-qps-reids
 * @author:谭农春
 * @createTime:2018/10/22 21:40
 */
@ControllerAdvice
public class GlobalException {

  // ServieException 自定义异常
  @ExceptionHandler(value = {ServieException.class})
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public String handleExceptions(final ServieException ex, final WebRequest req) {
    System.err.println("exception  enter " + ex.getMsg());
    return  ex.getMsg()+"";
  }
}
