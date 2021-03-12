package com.luna.practice.lyx.design.work.dto;

import com.luna.practice.lyx.design.work.entity.GoodsDO;

import java.util.List;
import java.util.UUID;

/**
 * @author luna@mac
 * @className ShopCartDTO.java
 * @description TODO
 * @createTime 2021年03月12日 18:13:00
 */
public class ShopCartDTO {

    private long          id = Long.parseLong(UUID.randomUUID().toString());

    private List<GoodsDO> goodsLists;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<GoodsDO> getGoodsLists() {
        return goodsLists;
    }

    public void setGoodsLists(List<GoodsDO> goodsLists) {
        this.goodsLists = goodsLists;
    }
}
