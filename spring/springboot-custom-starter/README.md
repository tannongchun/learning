# 自定义 starter 开发
 - 仅限于Springboot 开发，共同模块
 
 ## starter 总结
 
 - 1. 在META-INF 中添加spring.factories 配置自定义配置类 
    - > org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.kongxiang.config.HelloConfig
 - 2. 编写starter pom 
 
 
 ## 参考说明 
####使用注解说明
>  @Configuration：标识此类为一个spring配置类        
   @EnableConfigurationProperties(value = HelloConfigProperteis.class):            
    启动配置文件，value用来指定我们要启用的配置类，可以有多个，多个时我们可以这么写value={xxProperties1.class,xxProperteis2.class....}     
 @ConditionalOnClass(HelloService.class):表示当classPath下存在HelloService.class文件时改配置文件类才有效    
 @ConditionalOnProperty(prefix = "hello", value = "enable", matchIfMissing = true):        
 表示只有我们的配置文件是否配置了以hello为前缀的资源项值，并且在该资源项值为enable，如果没有配置我们默认设置为enable       
>      
  


> @ConditionalOnBean（仅仅在当前上下文中存在某个对象时，才会实例化一个Bean）       
  @ConditionalOnClass（某个class位于类路径上，才会实例化一个Bean）         
  @ConditionalOnExpression（当表达式为true的时候，才会实例化一个Bean）      
  @ConditionalOnMissingBean（仅仅在当前上下文中不存在某个对象时，才会实例化一个Bean）      
  @ConditionalOnMissingClass（某个class类路径上不存在的时候，才会实例化一个Bean）     
  @ConditionalOnNotWebApplication（不是web应用）       
