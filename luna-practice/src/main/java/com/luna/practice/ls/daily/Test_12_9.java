package com.luna.practice.ls.daily;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author luna@mac
 * @className Test_12_9.java
 * @description TODO
 * @createTime 2020年12月09日 20:30:00
 */
public class Test_12_9 {


    public static void main(String args[]) {
        Scanner reader = new Scanner(System.in);
        LinkedList<studentlei> listone = new LinkedList<studentlei>();

        System.out.println("请输入3位学生信息：");
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入第" + (i + 1) + "位学生的学号，姓名，数学，计算机成绩");
            studentlei t = new studentlei(reader.nextInt(), reader.next(), reader.nextDouble(), reader.nextDouble());
            listone.add(t);
        }

        Iterator<studentlei> feng = listone.iterator();

        listone.forEach(studentlei -> {
            System.out.println(studentlei.getid());
        });

        while (feng.hasNext())//看是否还有下一个结点
        {
            studentlei temp = feng.next();
            System.out.println(temp.getname());
        // System.out.printf("%d\t%s\t%.1f\t%.lf\n", temp.getid(), temp.getname(), temp.getmath(), temp.getcomputer());
        }
        listone.remove(1);
        while (feng.hasNext()) {
            studentlei temp = feng.next();
            System.out.println(temp.getname());
            //System.out.printf("%d\t%s\t%.1f\t%.lf\n", temp.getid(), temp.getname(), temp.getmath(), temp.getcomputer());

        }
    }

}

class studentlei {
    int id;
    String name;
    double math;
    double computer;

    public studentlei(int id, String name, double math, double computer) {
        this.id = id;
        this.name = name;
        this.math = math;
        this.computer = computer;
    }

    public int getid() {
        return id;
    }

    public String getname() {
        return name;
    }

    public double getmath() {
        return math;
    }

    public double getcomputer() {
        return computer;
    }

    public void setid(int x) {
        this.id = x;
    }

    public void setname(String x) {
        this.name = x;
    }

    public void setmath(double x) {
        this.math = x;
    }

    public void setcomputer(double x) {
        this.computer = x;
    }
}
