package com.luna.practice.lyx.design.work.entity;

import java.util.UUID;

/**
 * @author luna@mac
 * @className GoodsDO.java
 * @description TODO
 * @createTime 2021年03月12日 18:07:00
 */
public class GoodsDO {

    private Long       id;

    private String     name;

    private int        price;

    private BrandDO    brandDO;

    private CategoryDO categoryDO;

    public GoodsDO() {}

    public GoodsDO(long id, String name, int price, BrandDO brandDO, CategoryDO categoryDO) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.brandDO = brandDO;
        this.categoryDO = categoryDO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public BrandDO getBrand() {
        return brandDO;
    }

    public void setBrand(BrandDO brandDO) {
        this.brandDO = brandDO;
    }

    public CategoryDO getCategory() {
        return categoryDO;
    }

    public void setCategory(CategoryDO categoryDO) {
        this.categoryDO = categoryDO;
    }
}
