package com.luna.practice.ls.library;

/**
 * @author czy@win10
 * @date 2019/10/17 7:55
 */
public class Book {
    private int id;

    private String name;//书名

    private String author;//作者

    private String address;//出版社

    private int number;//每本书有多少本

    private String sort;//书的类别



    @Override
    public String toString() {
        return id+" "+name+" "+author+" "+address+" "+number+" "+sort;
    }

    public Book() {

    }

    public Book(int id, String name, String author, String address, int number, String sort) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.address = address;
        this.number = number;
        this.sort = sort;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
