package com.luna.spring.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @Author: Lingye
 * @Date: 2018/11/11
 * @Describe:
 * 定义日志切面
 * @Lazy 注解:容器一般都会在启动的时候实例化所有单实例 bean，如果我们想要 Spring 在启动的时候延迟加载 bean，需要用到这个注解
 * value为true、false 默认为true,即延迟加载，@Lazy(false)表示对象会在初始化的时候创建
 *
 * @Modified By:
 */
@Aspect
@Component
@Lazy(false)
public class LoggerAspect {

    /**
     * 定义切入点：对要拦截的方法进行定义与限制，如包、类
     *
     * 1、execution(public * *(..)) 任意的公共方法
     * 2、execution（* set*（..）） 以set开头的所有的方法
     * 3、execution（* com.luna.annotation.LoggerApply.*（..））com.luna.annotation.LoggerApply这个类里的所有的方法
     * 4、execution（* com.luna.annotation.*.*（..））com.luna.annotation包下的所有的类的所有的方法
     * 5、execution（* com.luna.annotation..*.*（..））com.luna.annotation包及子包下所有的类的所有的方法
     * 6、execution(* com.luna.annotation..*.*(String,?,Long))
     * com.luna.annotation包及子包下所有的类的有三个参数，第一个参数为String类型，第二个参数为任意类型，第三个参数为Long类型的方法
     * 7、execution(@annotation(com.luna.annotation.Lingyejun))
     */
    @Pointcut("@annotation(com.luna.spring.aop.Luna)")
    private void cutMethod() {

    }

    @Pointcut("execution(public * *(..)) || execution(* set*(..)) || execution(* com.luna..*.* (..)) || execution(* com.luna.spring.aop.*.*())")
    public void cutMethod2() {

    }

    @Before("@within(com.luna.spring.aop.Luna)")
    public void begin2() {
        System.out.println("==@Before2== luna blog logger : begin");
    }

    @Before("execution(* com.luna.spring.aop.HelloAopTwo.doPrint2(Aop))  && @args(com.luna.spring.aop.Luna) ")
    public void begin4() {
        System.out.println("==@Before4== luna blog logger : com.luna.spring.aop.HelloAopTwo.doPrint2(Aop)");
    }

    @Before("execution(* com.luna.spring.aop.HelloAopTwo.getName(String)) && args(name) ")
    public void begin5(String name) {
        System.out.println("==@Before5== luna blog logger : begin name=" + name);
    }

    @Before("execution(* com.luna.spring.aop.HelloAopTwo.doPrint2(String)) && @args(com.luna.spring.aop.Luna) ")
    public void begin6() {
        System.out.println("==@Before6== luna blog logger : begin name=");
    }

    @Before("this(com.luna.spring.aop.MyAop)")
    public void begin3() {
        System.out.println("==@Before3== luna blog logger : begin");
    }

    /**
     * 前置通知：在目标方法执行前调用
     */
    @Before("execution(* com.luna.spring.aop.*Two.*())")
    public void begin() {
        System.out.println("==@Before== luna blog logger : begin");
    }

    /**
     * 后置通知：在目标方法执行后调用，若目标方法出现异常，则不执行
     */
    @AfterReturning("cutMethod()")
    public void afterReturning() {
        System.out.println("==@AfterReturning== luna blog logger : after returning");
    }

    /**
     * 后置/最终通知：无论目标方法在执行过程中出现异常都会在它之后调用
     */
    @After("cutMethod()")
    public void after() {
        System.out.println("==@After== luna blog logger : finally returning");
    }

    /**
     * 异常通知：目标方法抛出异常时执行
     */
    @AfterThrowing("cutMethod()")
    public void afterThrowing() {
        System.out.println("==@AfterThrowing== luna blog logger : after throwing");
    }

    /**
     * 环绕通知：灵活自由的在目标方法中切入代码
     */
    @Around("cutMethod()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取目标方法的名称
        String methodName = joinPoint.getSignature().getName();
        // 获取方法传入参数
        Object[] params = joinPoint.getArgs();
        Luna luna = getDeclaredAnnotation(joinPoint);
        System.out.println("==@Around== luna blog logger --》 method name " + methodName + " args ");
        // 执行源方法
        joinPoint.proceed();
        // 模拟进行验证
        if (params != null && params.length > 0 && params[0].equals("Blog Home")) {
            System.out.println("==@Around== luna blog logger --》 " + luna.value() + " auth success");
        } else {
            System.out.println("==@Around== luna blog logger --》 " + luna.value() + " auth failed");
        }
    }

    /**
     * 获取方法中声明的注解
     *
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    public Luna getDeclaredAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature)joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        Luna annotation = objMethod.getDeclaredAnnotation(Luna.class);
        // 返回
        return annotation;
    }
}