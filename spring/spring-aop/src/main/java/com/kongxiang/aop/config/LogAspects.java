package com.kongxiang.aop.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.aop.config
 * @className: spring-aop
 * @author:谭农春
 * @createTime:2018/10/16 23:06
 */
// 指定切面Bean
@Aspect
public class LogAspects {

  // 1. 抽取公共的切入点表达式
  @Pointcut("execution(public int com.kongxiang.aop.service.MathCalcu.* (..))")
  public void pointCut(){}

 // 2.定义前置通知
  @Before("pointCut()")
  public  void logBefore(JoinPoint joinPoint){
    System.out.println("执行前置通知........ 方法名称：" + joinPoint.getSignature().getName());
  }

  // 3.定义后置通知
  @After("pointCut()")
  public  void logAfter(JoinPoint joinPoint){
    System.out.println("执行后置通知........ 方法名称：" + joinPoint.getSignature().getName());
  }
  // 4.定义返回通知
  @AfterReturning(value = "pointCut()",returning = "result")
  public  void logAfterReturn(JoinPoint joinPoint,Object result){
    System.out.println( result + " asdfapsodkfopsdk 返回通知........ 方法名称：" + joinPoint.getSignature().getName());
  }
  // 4.定义异常通知
  @AfterThrowing(value = "pointCut()",throwing = "exception")
  public  void logAfterReturn(JoinPoint joinPoint,Exception exception){
    System.out.println( joinPoint.getSignature().getName() + " 异常通知........ 方法名称：" + exception);
  }
//  // 6.环绕通知
  @Around(value = "pointCut()")
  public  Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    long startM = System.currentTimeMillis();
    System.out.println( " around start ");
    Object num =   joinPoint.proceed();
    System.out.println( " around end ");
    long endM = System.currentTimeMillis();
    System.err.println(" take time is " + (endM -startM ));
    return  num;
  }
}
