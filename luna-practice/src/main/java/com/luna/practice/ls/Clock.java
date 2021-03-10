package com.luna.practice.ls;

/**
 * @author czy@win10
 * @date 2019/10/24 8:16
 */
public class Clock {

    int hour;
    int minute;
    int second;

    public void setTime(int newH,int newW,int newS){
        hour=newH;
        minute=newW;
        second=newS;
    }
    public void showTime(){
        System.out.println(hour+":"+minute+":"+second);
    }

    public static void main(String[] args) {
        Clock clock=new Clock();
        clock.setTime(3,23,12);
        clock.showTime();
    }
}
