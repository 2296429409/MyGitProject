package com.icis.test;

import com.icis.service.UserService;
import com.icis.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("Beans.xml");
        for (int i = 0; i < 10; i++) {
            UserService userService = (UserServiceImpl) context.getBean("userService");
            System.out.println(userService);
        }
    }
}
