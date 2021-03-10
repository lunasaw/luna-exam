package com.luna.practice.ls.daily;

import java.util.Scanner;

/**
 * @Package: lsJava
 * @ClassName: Test_06_17
 * @Author: luna
 * @CreateTime: 2020/6/17 19:38
 * @Description:
 */
public class Test_06_17 {


    static class Edge {
        int v; //边的权值
        int[] ConnectPoint = new int[2];  //边所连接的点
        int isSelect; //是否被选择，1表示被选，0表示没有被选
        char No; //图的编号,a,b,c,d...在创建图的时候初始化的
    }

}


