package com.luna.practice.ls;

import java.util.ArrayList;
import java.util.List;

/**
 * @author czy@win10
 * @date 2019/10/24 8:20
 */
class Circle {
     double r;
     double pai=3.1415926;
}
class Rectangle{
     double height;
     double width;
}
class ShapeTester{

    public static void main(String[] args) {
        Circle circle = new Circle();
        System.out.println(circle);
        System.out.println("r="+circle.r);
        Rectangle rectangle=new Rectangle();
        System.out.println(circle+"   "+rectangle);
    }
}
class ShapeTester1 {
    public static void main(String[] args) {
        Circle x=new Circle();
        Rectangle y=new Rectangle();
        Rectangle z=new Rectangle();
        System.out.println(x+"  "+y+"  "+z);
    }
}
class ShapeTester2{
    public static void main(String[] args) {
        Circle x=new Circle();
        Rectangle y=new Rectangle();
        Rectangle z=new Rectangle();
        x.r=50;
        z.width=68.94;
        z.height=47.54;
        System.out.println(x.r+" "+z.width+" "+z.height);
    }
}
class Address{
    String name;
    int getNumber;
    String streetName;
    String city;
    String province;
    String posttalCode;
}
class AddressTester{
    public static void main(String[] args) {
        Address address1=new Address();
        Address address2=new Address();
        address1.name="zhang li";
        address1.streetName="15";
        address1.streetName="Tsinghua East Road";
        address1.city="Beijing";
        address1.posttalCode="100084";
        address2.name="Li hong";
        address2.streetName="2";
        address2.streetName="BeiNong";
        address2.city="Beijing";
        address2.posttalCode="102206";
        System.out.println(address1.name+""+address2.name);

        List<Integer> list =new ArrayList<>();
        list.add(2);
        list.add(1);
        list.add(1,9);
        System.out.println(list.get(1));
        System.out.println(list.contains(9));
//        System.out.println(list.remove(1.));
        int i = list.indexOf(1);
        list.remove(i);
        System.out.println(list.size());
        System.out.println(list.toString());
    }

}