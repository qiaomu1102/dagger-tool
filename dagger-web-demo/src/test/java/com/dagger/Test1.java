package com.dagger;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: kexiong
 * @date: 2020/12/18 20:32
 * @Description: TODO
 */
public class Test1 {

    @Test
    public void func11() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:");
        Object bean = applicationContext.getBean("");
        ApplicationContext context = new ClassPathXmlApplicationContext("");

    }

}
