package com.icis.utils;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetService {
    private static Object us;
    private static ApplicationContext context;
    static {
        context=new ClassPathXmlApplicationContext("Beans.xml");
    }

    public static Object getService(String name){
        us=context.getBean(name);
        return us;
    }
}
