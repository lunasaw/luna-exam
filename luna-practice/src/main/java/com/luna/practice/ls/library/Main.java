package com.luna.practice.ls.library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/10/17 8:09
 */
public class Main {


    public static void main(String[] args) throws IOException {

        Library library = new Library(1, "�γ�ͼ���", "�γ���-�ζ���-�и�·");
        User user = new User();
        ArrayList<Book> list = new ArrayList<>();
        read(list);
        library.setBooks(list);
        library.setNamenumber(list.size());
        System.out.println(library.getName());
        while (true) {
            System.out.println("��ӭ����ͼ���!-------->^");
            System.out.println("����ϵͳ-------------->0");
            System.out.println("�鿴ͼ��-------------->1");
            System.out.println("����ͼ��-------------->2");
            System.out.println("�黹ͼ��-------------->3");
            System.out.println("�˳�ϵͳ-------------->4");
            Scanner in = new Scanner(System.in);
            int i = in.nextInt();
            switch (i) {
                case 0:
                    System.out.println("��ӭ����ͼ���!-------->^");
                    System.out.println("����������------------>0");
                    int max = in.nextInt();
                    if (max == 123) {
                        user.sayhello();
                        while (true) {
                            System.out.println("����ϵͳ-------------->0");//Ĭ������123
                            System.out.println("�鿴ͼ��-------------->1");
                            System.out.println("����ͼ��-------------->2");
                            System.out.println("ɾ��ͼ��-------------->3");
                            System.out.println("�˳�ϵͳ-------------->4");
                            int imax = in.nextInt();
                            switch (imax) {
                                case 1:
                                    library.lookbook(library.getNamenumber());
                                    break;
                                case 2:
                                    library.addbooks();
                                    library.setNamenumber(library.getBooks().size());
                                    break;
                                case 3:
                                    library.delete();
                                    library.setNamenumber(library.getBooks().size());
                                    break;
                                case 4:
                                    clearInfoForFile();
                                    write(library.getBooks());
                                    System.exit(0);
                            }
                        }
                    }else {
                        System.out.println("�Բ���!�������!");
                    }
                    break;
                case 1:
                    library.lookbook(library.getNamenumber());
                    break;
                case 2:
                    library.borrow();
                    break;
                case 3:
                    library.returnbook();
                    break;
                case 4:
                    clearInfoForFile();
                    write(library.getBooks());
                    System.exit(0);
            }
        }
    }

    public static void read(List list){

        File file =new File("D:\\2.txt");
        InputStream is = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;
        try {
            is = new FileInputStream(file);
            reader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                String[] strings = line.split("\\ ");
                Book book=new Book(Integer.parseInt(strings[0]),strings[1],strings[2],strings[3],Integer.parseInt(strings[4]),strings[5]);
                list.add(book);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferedReader)
                    bufferedReader.close();
                if (null != reader)
                    reader.close();
                if (null != is)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void clearInfoForFile() {
        File file = new File("D:\\2.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(List list) throws FileNotFoundException {
        File file =new File("D:\\2.txt");
        FileOutputStream is = null;
        Writer writer = null;
        BufferedWriter BufferedWriter = null;
        try {
            is = new FileOutputStream(file);
            writer = new OutputStreamWriter(is);
            BufferedWriter = new BufferedWriter(writer);
            for (int i=0;i<list.size();i++){
                BufferedWriter.write(list.get(i).toString());
                BufferedWriter.write("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != BufferedWriter)
                    BufferedWriter.close();
                if (null != writer)
                    writer.close();
                if (null != is)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
