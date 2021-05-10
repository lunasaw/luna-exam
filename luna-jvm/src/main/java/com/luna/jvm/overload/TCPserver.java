package com.luna.jvm.overload;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPserver {
    public static void main(String[] args) throws IOException {
        // 创建一个服务器端口
        ServerSocket ss = new ServerSocket(8080);

        // 使用accept方法与其交互
        Socket socket = ss.accept();

        // 读取服务器端硬盘中的文件
        InputStream is = socket.getInputStream();

        /*byte[] bytes = new byte[1024];
        int len = 0;
        while((len = is.read(bytes)) != -1)
        {
            System.out.println(new String(bytes,0,len));
        }*/
        // 网址：
        // GET /internetbiancheng/rewuyi/wenzhangzhengtibuju.html HTTP/1.1

        // 读取客户端发来的消息，把传过来的地址用字符缓冲流中的readline（）方法
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // 读取数据
        String result = br.readLine();
        System.out.println(result);

        // 使用数据分隔在每个数组中(以一个空格为分隔符)
        String[] shuzu = result.split(" ");
        System.out.println(shuzu[1]);
        // 把地址读取出来，从第二个开始截取，一直截取到该数组末尾
        String path = shuzu[1].substring(1);
        System.out.println(path);
        // 服务器硬盘中的内容传给浏览器,从该路径读取文件，传输给客户端

        // 把硬盘中的文件用文件字节流来表示
        FileInputStream fis = new FileInputStream(path);

        // 写文件给浏览器(客户端)
        OutputStream os = socket.getOutputStream();

        // 写入HTTP协议响应头，固定写法
        os.write("HTTP/1.1 200 OK\r\n".getBytes());
        os.write("Content-Type:text/html\r\n".getBytes());
        // 必须要写入空行，否则浏览器不解析
        os.write("\r\n".getBytes());

        int lens = 0;
        byte[] bytes = new byte[1024];
        while ((fis.read(bytes)) != -1) {
            os.write(bytes, 0, lens);
        }

        // 释放资源
        fis.close();
        socket.close();
        ss.close();
    }
}
