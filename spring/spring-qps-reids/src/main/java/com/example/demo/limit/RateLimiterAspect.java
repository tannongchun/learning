package com.example.demo.limit;

import com.example.demo.ServieException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @description:
 * @projectName: com.example.demo.limit
 * @className: spring-qps-reids
 * @author:谭农春
 * @createTime:2018/10/22 20:18
 */
@Component
@Aspect
public class RateLimiterAspect {

  // 调用springboot 默认配置
  @Autowired
  private  StringRedisTemplate stringRedisTemplate;
  /**
   *  计数
   */
  private final static  String COUNT_KEY ="COUNT_KEY";
  /**
   *  计时
   */
  private final static  String TIME_KEY ="TIME_KEY";

  // 定义注解的切入点
  @Pointcut("@annotation(com.example.demo.limit.RateLimiter)")
  public  void pointcut(){}

  // 切入点解析
  @Before("pointcut()")
  public  void beforHandler(JoinPoint point) throws Exception {
    Signature signature = point.getSignature();
    MethodSignature methodSignature = (MethodSignature)signature;
    Method targetMethod = methodSignature.getMethod();
    RateLimiter rateLimiter = (RateLimiter) targetMethod.getAnnotation(RateLimiter.class);
    // 获取限制数
     int limit = rateLimiter.limit();
    // 请求相关参数
      RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    ServletRequestAttributes sra = (ServletRequestAttributes) ra;
    HttpServletRequest request = sra.getRequest();
    String url = request.getRequestURL().toString();
    String method = request.getMethod();
    String uri = request.getRequestURI();
    String queryString = request.getQueryString();
      // 主要步骤就是：
//       1.配置 一个计数器，一个计时器
    if(!stringRedisTemplate.hasKey(TIME_KEY)) {
      stringRedisTemplate.opsForValue().set(TIME_KEY,"0",(long)15, TimeUnit.SECONDS);
      //时间到就重新初始化为0 2秒
      stringRedisTemplate.opsForValue().set(COUNT_KEY,"0",(long)17, TimeUnit.SECONDS);
    }
    // 请求自动 +1
    else if(stringRedisTemplate.hasKey(TIME_KEY)&&stringRedisTemplate.boundValueOps(COUNT_KEY).increment(1) > limit) {
       throw  new ServieException("15秒内，调用频率过快,");
    }

   String key=  MessageFormat.format("请求开始, 各个参数, url: {0}, method: {1}, uri: {2}, params: {3} ", url, method, uri, queryString);
   System.out.print(key);
  }

}
