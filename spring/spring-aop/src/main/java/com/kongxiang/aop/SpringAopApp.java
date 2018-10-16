package com.kongxiang.aop;

import com.kongxiang.aop.config.AspectConfig;
import com.kongxiang.aop.service.MathCalcu;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class SpringAopApp
{
    public static void main( String[] args )
    {
        // 加载配置文件
      AnnotationConfigApplicationContext  applicationContext = new AnnotationConfigApplicationContext(AspectConfig.class);
       MathCalcu  service =  applicationContext.getBean(MathCalcu.class);
       service.Div(1,1);
      applicationContext.close();
    }
}
