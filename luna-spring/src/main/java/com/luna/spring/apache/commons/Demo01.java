package com.luna.spring.apache.commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.functors.EqualPredicate;
import org.apache.commons.collections4.functors.NotNullPredicate;
import org.apache.commons.collections4.functors.UniquePredicate;
import org.apache.commons.collections4.list.PredicatedList;
import org.junit.Test;

/**
     函数式编程 之 Predicate 断言
  封装条件或判别式  if..else替代
  1、 new EqualPredicate<类型>(值) 
     EqualPredicate.equalPredicate(值);
  2、NotNullPredicate.INSTANCE 
  3、UniquePredicate.uniquePredicate()
  4、自定义
     new Predicate() +evaluate  
    PredicateUtils.allPredicate  andPredicate anyPredicate
    PredicatedXxx.predicatedXxx(容器,判断)
 * @author Administrator
 *
 */
public class Demo01 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("======自定义判断======");
        //自定义的判别式
        Predicate<String> selfPre =new Predicate<String>(){
            @Override
            public boolean evaluate(String object) {
                return object.length()>=5 && object.length()<=20;
                
            }};
        Predicate notNull=NotNullPredicate.notNullPredicate();
        
        Predicate all =PredicateUtils.allPredicate(notNull,selfPre);
        
        List<String> list =PredicatedList.predicatedList(new ArrayList<String>(),all);
        list.add("bjsxt");
        list.add(null);
        list.add("bj");    
        
    }
    /**
     * 判断唯一
     */
    public static void unique(){
        System.out.println("====唯一性判断====");
        Predicate<Long> uniquePre =UniquePredicate.uniquePredicate();
        List<Long> list =PredicatedList.predicatedList(new ArrayList<Long>(), uniquePre);
        list.add(100L);
        list.add(200L);
        list.add(100L); //出现重复值，抛出异常
    }
    
    /**
     * 判断非空
     */
    public static void notNull(){
        System.out.println("====非空判断====");
        //Predicate notNull=NotNullPredicate.INSTANCE;
        Predicate notNull=NotNullPredicate.notNullPredicate();
        //String str ="bjs";
        String str =null;
        System.out.println(notNull.evaluate(str)); //如果非空为true ,否则为false
        
        //添加容器值的判断
        List<Long> list =PredicatedList.predicatedList(new ArrayList<Long>(), notNull);
        list.add(1000L);
        list.add(null); //验证失败，出现异常
    }
    
    
    /**
     * 比较相等判断
     */
    @Test
    public static void equal(){
        System.out.println("======相等判断======");
        //Predicate<String> pre =new EqualPredicate<String>("bjsxt");
        Predicate<String> pre =EqualPredicate.equalPredicate("bjsxt");
        boolean flag =pre.evaluate("bj");
        System.out.println(flag);
    }

}