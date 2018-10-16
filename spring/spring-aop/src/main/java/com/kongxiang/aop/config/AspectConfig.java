package com.kongxiang.aop.config;

import com.kongxiang.aop.service.MathCalcu;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.aop
 * @className: spring-aop
 * @author:谭农春
 * @createTime:2018/10/16 23:03
 */
@EnableAspectJAutoProxy
@Configuration
public class AspectConfig {

  @Bean
  MathCalcu newMathCalcu(){
    return  new MathCalcu();
  }

  @Bean
  LogAspects newLogAspects(){
    return  new LogAspects();
  }

}
