package com.luna.practice.luna.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("--服务器端已开启--");

        // 创建ServerSocket对象，这里的端口号必须与客户端的端口号相同
        ServerSocket server = new ServerSocket(9000);

        // 调用方法accept()，用来监听客户端发来的请求
        Socket socket = server.accept();

        // 获取输入流对象
        InputStream is = socket.getInputStream();

        // 读取输入流中的数据
        int b = 0;
        while ((b = is.read()) != -1) {
            System.out.print((char)b);
        }
        // 关闭流
        is.close();
        socket.close();
        server.close();
    }
}