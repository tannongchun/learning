package com.example.demo.controller;

import com.example.demo.limit.RateLimiter;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @description:
 * @projectName: com.example.demo.controller
 * @className: spring-qps-reids
 * @author:谭农春
 * @createTime:2018/10/22 20:16
 */
@RestController
@RequestMapping("/api/qps/")
public class RedisqpsController {


  @RateLimiter
  @RequestMapping(value = "/allusers")
  public String requestToAllUser(){
    return "请求成功";
  }

}
