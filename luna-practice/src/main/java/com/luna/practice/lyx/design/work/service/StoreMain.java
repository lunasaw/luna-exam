package com.luna.practice.lyx.design.work.service;

import com.luna.practice.lyx.design.work.dto.GoodsDTO;

import java.util.Scanner;

/**
 * @author luna@mac
 * @className StoreMain.java
 * @description TODO
 * @createTime 2021年03月12日 21:28:00
 */
public class StoreMain {

    public static void main(String[] args) {
        String key = "";
        // 控制是否退出菜单
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);

        while (loop) {
            System.out.println("1: 展示商品。");
            System.out.println("2: 输入编号加入购物车。");
            System.out.println("3: 统计购物车金额。");
            System.out.println("4: 展示购物车商品。");
            System.out.println("请输入你的选择：");
            key = scanner.next();
            switch (key) {
                case "1":
                    GoodsService.showGoods(GoodsDTO.goodsDOList);
                    break;
                case "2":
                    System.out.println("请输入商品编号：");
                    int value = scanner.nextInt();
                    CartService.joinCart(value);
                    System.out.println("成功加入购物车：");
                    break;
                case "3":
                    System.out.println("当前购物车总计：" + CartService.totalAmount());
                    break;
                case "4":
                    GoodsService.showGoods(CartService.shopCartDTO.getGoodsLists());
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }

        System.out.println("程序退出~~~");
    }
}
