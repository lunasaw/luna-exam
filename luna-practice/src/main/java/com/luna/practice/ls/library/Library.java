package com.luna.practice.ls.library;

import java.util.List;
import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/10/17 7:57
 */
public class Library {
    private int id;

    private String name;//图书馆名

    private String address;//图书馆地址

    private List<Book> books;//图书;


    private int namenumber;//书名数

    //还书
    public void returnbook() {
        System.out.println("请输入你要归还的书名");
        Scanner in = new Scanner(System.in);
        String string = in.nextLine();
        try {
            for (int i = 1; i <= 3; i++) {
                String name = books.get(i).getName();
                if (name.equals(string)) {
                    id = books.get(i).getId();
                    break;

                }
            }
        } catch (Exception e) {
            System.out.println("抱歉!该书不存在");
            return;
        }
        Book book = books.get(id);
        book.setNumber(book.getNumber() + 1);
        System.out.println("感谢您的支持!按任意键继续使用");
        in.nextLine();
        return;
    }

    //借书
    public void borrow() {
        System.out.println("请输入你要借阅的书名");
        Scanner in = new Scanner(System.in);
        String string = in.nextLine();
        int id=0;
        try {
            for (int i = 1; i <= 3; i++) {
                String name = books.get(i).getName();
                if (name.equals(string)) {
                    id = books.get(i).getId();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("抱歉!该书不存在");
            return;
        }
        Book book = books.get(id);
        book.setNumber(book.getNumber() - 1);
        System.out.println("你好!该书还剩" + book.getNumber() + "本.");
        System.out.println("Here you are");
        System.out.println("感谢您的支持!按任意键继续使用");
        in.nextLine();
        return;
    }

    //查书
    public void lookbook(int number){
        for (int i=0;i<number;i++){
            System.out.println(books.get(i).toString());
        }return;
    }

    //增加图书
    public List<Book> addbooks(){
        System.out.println("请输入你要增加的书id,书名,作者,出版社,增加数目,书的类别,并以空格间隔");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        try {
            String[] strings = s.split("\\ ");
            Book book=new Book(Integer.parseInt(strings[0]),strings[1],strings[2],strings[3],Integer.parseInt(strings[4]),strings[5]);
            books.add(book);
            System.out.println("增加成功!");
        } catch (NumberFormatException e) {
            System.out.println("输入有误!");
            return null;
        }
        return books;
    }

    //删除图书
    public void delete(){
        System.out.println("请输入你要删除的书名");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        int id=0;
        try {
            for (int i = 0; i < books.size(); i++) {
                String name = books.get(i).getName();
                if (name.equals(s)) {
                    id = books.get(i).getId();
                    books.remove(id);
                    System.out.println("删除成功!");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("抱歉!该书不存在");
            return;
        }


    }

    public Library(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", books=" + books +
                '}';
    }

    public Library() {
    }

    public Library(int id, String name, String address, List<Book> books) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.books = books;
    }

    public int getNamenumber() {
        return namenumber;
    }

    public void setNamenumber(int namenumber) {
        this.namenumber = namenumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
