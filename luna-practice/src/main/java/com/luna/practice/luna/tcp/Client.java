package com.luna.practice.luna.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {

        // 创建Socket对象，指定要发送到服务器端的IP地址，以及服务器端应用程序接收的端口号
        // localhost代表本机IP地址
        Socket client = new Socket("localhost", 9000);

        // 获取输出流，用于向服务器端发送数据
        OutputStream os = client.getOutputStream();

        os.write("Java is my friend !".getBytes());
        System.out.println("信息已发送");

        // 关闭流
        os.close();
        client.close();
    }
}