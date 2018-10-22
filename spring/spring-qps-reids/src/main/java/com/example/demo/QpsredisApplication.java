package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
// 启用切面注解
@EnableAspectJAutoProxy
public class QpsredisApplication {

	public static void main(String[] args) {
		SpringApplication.run(QpsredisApplication.class, args);
	}
}
