package com.luna.practice.lyx.design.work.service;

import com.luna.practice.lyx.design.work.dto.ShopCartDTO;
import com.luna.practice.lyx.design.work.entity.GoodsDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luna@mac
 * @className CartService.java
 * @description TODO
 * @createTime 2021年03月12日 18:16:00
 */
public class CartService {

    private static Map<Long, ShopCartDTO> cartMap = new HashMap<>();

    public static ShopCartDTO addCart(List<GoodsDO> list) {
        ShopCartDTO shopCartDTO = new ShopCartDTO();
        shopCartDTO.setGoodsLists(list);
        if (cartMap.containsValue(shopCartDTO)) {
            return cartMap.get(shopCartDTO.getId());
        }
        cartMap.put(shopCartDTO.getId(), shopCartDTO);
        return shopCartDTO;
    }

    public static void display(ShopCartDTO shopCartDTO) {
        List<GoodsDO> goodsLists = shopCartDTO.getGoodsLists();
        System.out.println(goodsLists);
    }

    public static Integer totalAmount(Long shopCart) {
        ShopCartDTO shopCartDTO = cartMap.get(shopCart);
        int totalAmount = 0;
        for (GoodsDO goodsDO : shopCartDTO.getGoodsLists()) {
            totalAmount += goodsDO.getPrice();
        }
        return totalAmount;
    }
}
