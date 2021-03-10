package com.luna.practice.gw.practice;

import java.util.*;

/**
 * @author Luna@win10
 * @date 2020/5/26 10:08
 */
public class Test_5_26 {}

class Test1 {
    public static void main(String[] args) {
        String apple = "apple";
        String orange = "orange";
        String pear = "pear";
        List<String> list = new ArrayList<String>();
        list.add(apple);
        list.add(orange);
        list.add(pear);
        list.add(apple);
        System.out.println("List集合中的元素为：");
        Iterator<String> it = list.iterator();
        // 通过iterator()方法序列化集合中的所有对象
        while (it.hasNext()) {
            System.out.println(it.next());// 将元素输出
        }
        Set<String> set = new HashSet<String>();
        // 通过addAll()方法添加指定集合中的所有对象到该集合中
        System.out.println("去掉重复值后的元素为：");
        set.addAll(list);
        Iterator<String> it1 = set.iterator();
        // 通过iterator()方法序列化集合中的所有对象
        while (it1.hasNext()) {
            System.out.println(it1.next());// 将元素输出
        }
    }
}

class Test2 {
    public static void main(String[] args) {
        String student0 = "姓名：李哥，性别：男，出生日期：1984-10-6";
        String student1 = "姓名：小陈，性别：女，出生日期：1982-5-10";
        String student2 = "姓名：小刘，性别：男，出生日期：1983-10-5";
        String student3 = "姓名：小张，性别：男，出生日期：1984-1-1";
        String student4 = "姓名：小董，性别：男，出生日期：1980-7-19";
        String student5 = "姓名：小吕，性别：男，出生日期：1984-11-3";
        List<String> list = new ArrayList<String>();// 定义集合对象
        list.add(student0);
        list.add(student1);
        list.add(student2);
        list.add(student3);
        list.add(student4);
        list.add(student5);
        Iterator<String> it = list.iterator(); // 创建集合迭代器
        System.out.println("集合中的对象：");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        boolean judge = list.remove(student1);
        String judgement;
        if (judge == true)
            judgement = "成功！";
        else
            judgement = "失败！";
        System.out.println("删除student1 " + judgement);
        list.remove(student1);
        Iterator<String> it2 = list.iterator(); // 创建集合迭代器
        System.out.println("删除student1后集合中的对象：");
        while (it2.hasNext()) {
            System.out.println(it2.next());
        }
        String student6 = "姓名：小吕，性别：男，出生日期：1984-11-3";

        boolean judge2 = list.add(student6);
        String judgement2;
        if (judge2 == true)
            judgement2 = "成功！";
        else
            judgement2 = "失败！";
        System.out.println("增加student6" + judgement2);
        Iterator<String> it3 = list.iterator(); // 创建集合迭代器
        System.out.println("增加student6后集合中的对象：");
        while (it3.hasNext()) {
            System.out.println(it3.next());
        }
    }
}
class MapText {
    public static void main(String[] args) {
        System.out.println("请输入需要查询人的姓名：");
        move();
    }
    public static void move(){
        Map map = new HashMap();//创建Map实例
        map.put("张三", "111111");
        map.put("李四", "222222");
        map.put("王五", "333333");
        Scanner scan = new Scanner(System.in);
        String name = scan.next();
        if(map.containsKey(name)==true){
            System.out.println(name+"的电话号码为：\n"+map.get(name));
        }
        else{
            System.out.println("电话薄中无此人，请重新输入：");
            move();
        }
    }
}