package com.example.demo.controller;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @version 1.0
 * @description:
 *     QPS 单个应用 令牌桶算法
 * @projectName: com.9.demo.controller
 * @className: spring-qps-single
 * @author:tannongchun
 * @createTime:2018/10/18 21:43
 */
@RestController
@RequestMapping("/api/qps")
public class QPSController {
  // permitsPerSecond表示每秒钟新增的令牌数，warmupPeriod表示从冷启动速率过渡到平均速率所需要的时间间隔
  //  RateLimiter.create(double permitsPerSecond, long warmupPeriod, TimeUnit unit)
  // 每5秒一个请求
  private static final RateLimiter limiter = RateLimiter.create(0.2) ;

  @GetMapping("say")
  public  String sayQps(){
//    double acquire = limiter.acquire();
      if(limiter.tryAcquire()) {
        // 当前系统毫秒数
        long time = System.currentTimeMillis() / 1000;
        String s = "say => " + time;
        System.out.println(s);
        return s;
      }
      else{
        return  " 操作太频繁了。。。";
      }

  }

}
