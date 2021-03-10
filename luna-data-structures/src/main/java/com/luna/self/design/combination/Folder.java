package com.luna.self.design.combination;

import java.util.List;

/**
 * @author luna@mac
 * @className Folder.java
 * @description TODO
 * @createTime 2021年03月10日 13:50:00
 */
public class Folder {

    private String       type;

    private String       name;

    private int          number;

    private List<Folder> list;

    public String getType() {
        return type;
    }

    public Folder setType(String type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public Folder setName(String name) {
        this.name = name;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public Folder setNumber(int number) {
        this.number = number;
        return this;
    }

    public List<Folder> getList() {
        return list;
    }

    public Folder setList(List<Folder> list) {
        this.list = list;
        return this;
    }
}
