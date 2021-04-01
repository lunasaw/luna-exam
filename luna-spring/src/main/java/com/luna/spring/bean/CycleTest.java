package com.luna.spring.bean;

import org.bytedeco.librealsense.context;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试类
 * 
 * @author LinJie
 * 
 */
public class CycleTest {
    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        Student student = (Student)context.getBean("student");
        // Bean的使用
        student.play();
        System.out.println(student);
        // 关闭容器
        ((AbstractApplicationContext)context).close();
    }
}
