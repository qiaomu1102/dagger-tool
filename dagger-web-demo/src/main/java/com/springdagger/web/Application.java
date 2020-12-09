package com.springdagger.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author: kexiong
 * @date: 2020/10/26 16:48
 * @Description: TODO
 */
@SpringBootApplication(scanBasePackages = "com.springdagger")
@MapperScan("com.springdagger.web.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
