package com.example.rabbitmq.consumerdemo;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {SpringUtil.class})
public class ConsumerDemoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ConsumerDemoApplication.class, args);
    }
    
}
