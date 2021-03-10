package com.luna.practice.ls.library;

/**
 * @author czy@win10
 * @date 2019/10/17 10:46
 */
public class User {
    private int id;
    private final String name = "books.admin";
    private int sort;

    public void sayhello(){
        System.out.println("hello"+name);
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
