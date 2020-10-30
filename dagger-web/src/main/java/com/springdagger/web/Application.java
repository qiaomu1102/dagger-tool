package com.springdagger.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: kexiong
 * @date: 2020/10/26 16:48
 * @Description: TODO
 */
@SpringBootApplication
@MapperScan("com.springdagger.web.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
