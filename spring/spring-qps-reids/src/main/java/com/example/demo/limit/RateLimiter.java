package com.example.demo.limit;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @description:
 * @projectName: com.example.demo.limit
 * @className: spring-qps-reids
 * @author:谭农春
 * @createTime:2018/10/22 20:13
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RateLimiter {
  /**
   *  默认请求数 是1秒1次
   * @return
   */
  public int limit()  default 1;
}
