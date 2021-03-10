package com.luna.springdemo.anno;

/**
 * 增加一个子类继承AnnotationTest 演示@Inherited注解允许继承
 *
 * @author zhangqh
 * @date 2018年4月23日
 */
public class ChildAnnotationTest extends AnnotationTest {
	public static void main(String[] args) {
		// 获取类上的注解MyAnTargetType
		MyAnTargetType t = ChildAnnotationTest.class.getAnnotation(MyAnTargetType.class);
		// 获取了父类注解上的值
		System.out.println("类上的注解值 === " + t.value());
	}
}