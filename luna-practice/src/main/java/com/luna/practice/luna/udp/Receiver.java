package com.luna.practice.luna.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receiver {
    public static void main(String[] args) throws IOException {
        System.out.println("---接收方---");

        // 创建数据报套接字对象，指定的端口号要和发送方发送数据的端口号相同
        // （不是发送方的端口号7770，是发送方发送数据的端口号8800）
        DatagramSocket ds = new DatagramSocket(8800);

        // 创建接收数据报的对象
        byte[] b = new byte[1024];
        DatagramPacket dp = new DatagramPacket(b, b.length);

        // 接收数据
        ds.receive(dp);
        System.out.println(new String(b, 0, dp.getLength()));
        // 关闭流
        ds.close();
    }
}