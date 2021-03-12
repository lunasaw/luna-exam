package com.luna.practice.lyx.design.work.service;

import com.alibaba.fastjson.JSON;
import com.luna.practice.lyx.design.work.dto.GoodsDTO;
import com.luna.practice.lyx.design.work.entity.GoodsDO;

import java.util.*;

/**
 * @author luna@mac
 * @className GoodsService.java
 * @description TODO
 * @createTime 2021年03月12日 19:03:00
 */
public class GoodsService {

    static Formatter formatter = new Formatter(System.out);

    public static void showGoods(List<GoodsDO> goodsList) {
        System.out.printf("%1s %3s %2s %2s %3s \n", "编号", "物品名", "价格", "分类", "品牌");
        String[] strings = new String[goodsList.size()];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < goodsList.size(); i++) {
            stringBuilder.append(
                goodsList.get(i).getId() + "," + goodsList.get(i).getName() + "," + goodsList.get(i).getPrice()
                    + "," + goodsList.get(i).getCategory().getName() + "," + goodsList.get(i).getBrand().getName());
            strings[i] = stringBuilder.toString();
            stringBuilder = new StringBuilder("");
        }
        printResult(strings);
    }

    public static void printResult(String[] A) {
        String[] tempA = A[0].split(",");
        int maxLen = tempA.length;
        for (int i = 1; i < A.length; i++) {
            tempA = A[i].split(",");
            if (maxLen < tempA.length) {
                maxLen = tempA.length;
            }
        }
        String[][] valueA = new String[A.length][maxLen];
        for (int i = 0; i < valueA.length; i++) {
            for (int j = 0; j < valueA[0].length; j++) {
                valueA[i][j] = "";
            }
        }

        for (int i = 0; i < A.length; i++) {
            tempA = A[i].split(",");
            for (int j = 0; j < tempA.length; j++) {
                valueA[i][j] = tempA[j];
            }
        }
        int[] maxJ = new int[maxLen];
        for (int j = 0; j < maxLen; j++) {
            for (int i = 0; i < A.length; i++) {
                if (valueA[i][j].length() > maxJ[j]) {
                    maxJ[j] = valueA[i][j].length();
                }
            }
        }

        StringBuilder opera = new StringBuilder("+");
        for (int j = 0; j < maxJ.length; j++) {
            for (int k = 0; k < maxJ[j]; k++) {
                opera.append('-');
            }
            opera.append('+');
        }
        for (int i = 0; i < valueA.length; i++) {
            System.out.println(opera);
            System.out.print("|");
            for (int j = 0; j < valueA[0].length; j++) {
                int len = maxJ[j] - valueA[i][j].length();
                String format = "";
                if (len == 0) {
                    format = "" + "%s";
                } else {
                    format = "%" + len + "s";
                }
                System.out.print(valueA[i][j]);
                System.out.printf(format, "");
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println(opera);
        return;
    }

    public static void main(String[] args) {
        GoodsService.showGoods(GoodsDTO.goodsDOList);
    }
}
