package com.luna.practice.gw.practice;

import java.io.*;
import java.util.Scanner;

/**
 * @author Iszychen@win10
 * @date 2020/3/3 10:02
 */
public class Test_3_3 {

    /**
     * 控制台输入,输出到文件
     *
     * @throws Exception
     */
    public static void file() throws Exception {
        Scanner in = new Scanner(System.in);
        File file = new File("D:\\practice.txt");
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        while (true) {
            String next = in.next();
            if (next.equals("#") == false) {
                writer.write(next);
            } else {
                break;
            }

        }
        writer.flush();
        writer.close();
    }

    public static void bufferReader() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\practice.txt"), "UTF-8"));
            String Line;
            while ((Line = br.readLine()) != null) {
                System.out.println(Line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 复制
     *
     * @throws Exception
     */
    public static void copy() throws Exception {
        FileReader fr = new FileReader("D:\\practice.txt");
        FileWriter fw = new FileWriter("D:\\TestCopy.txt");
        int c;
        int tag = 0;
        while ((c = fr.read()) != -1) {
            System.out.print((char)c);
            tag++;
            fw.write(c);
        }
        fr.close();
        fw.close();
        System.out.println("\n读取字节数:" + tag);
    }

    public static void main(String[] args) throws Exception {
        Test_3_3.file();
        Test_3_3.copy();
        Test_3_3.bufferReader();
    }
}
