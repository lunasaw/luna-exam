package com.luna.practice.lyx.design.work.service;

import com.luna.practice.lyx.design.work.dto.GoodsDTO;
import com.luna.practice.lyx.design.work.dto.ShopCartDTO;
import com.luna.practice.lyx.design.work.entity.GoodsDO;
/**
 * @author luna@mac
 * @className CartService.java
 * @description TODO
 * @createTime 2021年03月12日 18:16:00
 */
public class CartService {

    public static ShopCartDTO shopCartDTO = new ShopCartDTO();

    public static Integer totalAmount() {
        int totalAmount = 0;
        for (GoodsDO goodsDO : shopCartDTO.getGoodsLists()) {
            totalAmount += goodsDO.getPrice();
        }
        return totalAmount;
    }

    public static boolean joinCart(int id) {
        GoodsDO byId = GoodsDTO.findById(id);
        if (byId != null) {
            shopCartDTO.getGoodsLists().add(byId);
            return true;
        }
        return false;
    }
}
