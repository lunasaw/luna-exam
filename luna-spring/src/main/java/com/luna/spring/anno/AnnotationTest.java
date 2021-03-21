package com.luna.spring.anno;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 测试java注解类
 *
 * @author luna
 * @date 2018年4月22日
 */
@MyAnTargetType("这是注解传入的值")
public class AnnotationTest {

	private static final Logger log = LoggerFactory.getLogger(AnnotationTest.class);

	@MyAnTargetField
	private String field = "我是字段";

	@MyAnTargetMethod("测试方法")
	public void test(@MyAnTargetParameter String arg1, @MyAnTargetParameter String arg2) {
		log.info("参数值 === " + arg1 + "<==>" + arg2);
	}

	public static void main(String[] args) {
		// 获取类上的注解MyAnTargetType
		MyAnTargetType t = AnnotationTest.class.getAnnotation(MyAnTargetType.class);
		Class<? extends Annotation> aClass = t.annotationType();
		log.info("类上的注解值 === " + t.value() + " === type:" + aClass.toString());
		MyAnTargetMethod tm = null;
		try {
			// 根据反射获取AnnotationTest类上的test方法
			Method method = AnnotationTest.class.getDeclaredMethod("test", String.class, String.class);
			// 获取方法上的注解MyAnTargetMethod getDeclaredMethod() 方法返回一个Method对象，它反映此Class对象所表示的类或接口的指定已声明方法。
			tm = method.getAnnotation(MyAnTargetMethod.class);
			log.info("方法上的注解值 === " + tm.value() + " === type:" + tm.annotationType().toString());
			// 获取方法上的所有参数注解  循环所有注解找到MyAnTargetParameter注解
			Annotation[][] annotations = method.getParameterAnnotations();
			for (Annotation[] tt : annotations) {
				for (Annotation t1 : tt) {
					if (t1 instanceof MyAnTargetParameter) {
						log.info("参数上的注解值 === " + ((MyAnTargetParameter) t1).value());
					}
				}
			}
			method.invoke(new AnnotationTest(), "改变默认参数1", "改变默认参数2");
			// 获取AnnotationTest类上字段field的注解MyAnTargetField
			MyAnTargetField fieldAn = AnnotationTest.class.getDeclaredField("field").getAnnotation(MyAnTargetField.class);
			log.info("字段上的注解值 === " + fieldAn.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}