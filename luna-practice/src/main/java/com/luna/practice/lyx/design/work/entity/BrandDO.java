package com.luna.practice.lyx.design.work.entity;

import java.util.UUID;

/**
 * @author luna@mac
 * @className BrandDO.java
 * @description TODO
 * @createTime 2021年03月12日 18:06:00
 */
public class BrandDO {

    private long   id;

    private String name;

    public BrandDO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
