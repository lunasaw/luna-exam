package com.luna.practice.luna.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender {
    public static void main(String[] args) throws IOException {

        // 创建接受或发送的数据报套接字，并指定发送方的端口号为7770
        DatagramSocket ds = new DatagramSocket(7770); // 端口号也可以不指定
        System.out.println("---发送方---");

        // 创建数据报对象，用来发送数据
        byte[] b = "Java is my friend ！".getBytes();

        // 8800为接收方的端口号，netAddress.getByName("localhost")是获取主机的IP地址
        DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getByName("localhost"), 8800);

        ds.send(dp); // 发送数据报
        System.out.println("数据已发送");
        // 关闭流
        ds.close();
    }
}