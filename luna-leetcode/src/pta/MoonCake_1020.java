package pta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/10/18 12:59
 * 月饼 利润
 * 注意：销售时允许取出一部分库存。样例给出的情形是这样的：假如我们有 3 种月饼，其库存量分别为 18、15、10 万吨，
 * 总售价分别为 75、72、45 亿元。如果市场的最大需求量只有 20 万吨，那么我们最大收益策略应该是卖出全部 15 万吨第 2 种月饼、以及 5 万吨第 3 种月饼，
 * 获得 72 + 45/2 = 94.5（亿元）。
 * 输入格式：
 */
public class MoonCake_1020 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s1 = in.nextLine();//几种月饼  共需多少顿
        String s2 = in.nextLine();//每种库存
        String s3 = in.nextLine();//每种售价

        String[] strings1 = s1.split("\\ ");
        int[] need = new int[strings1.length];
        for (int i = 0; i < strings1.length; i++) {
            need[i] = Integer.parseInt(strings1[i]);
        }

        String[] strings2 = s2.split("\\ ");
        int[] repertory = new int[need[0]];
        for (int i = 0; i < strings2.length; i++) {
            repertory[i] = Integer.parseInt(strings2[i]);
        }//库存

        String[] strings3 = s3.split("\\ ");
        int[] selmoney = new int[need[0]];
        for (int i = 0; i < strings3.length; i++) {
            selmoney[i] = Integer.parseInt(strings3[i]);
        }

//        for (int c:need
//             ) {
//            System.out.print(c+" ");
//        }
//        for (int c:repertory
//        ) {
//            System.out.print(c+" ");
//        }
//        for (int c:selmoney
//        ) {
//            System.out.print(c+" ");
//        }
        double unit;
        double max = 0;
        ArrayList<Double> list = new ArrayList<>();
        ArrayList<Double> list2 = new ArrayList<>();
        for (int i = 0; i < need[0]; i++) {
            unit = (double) selmoney[i] / (double) repertory[i];//库存除以售价等于单位售价
            list.add(unit);
            list2.add(unit);
        }
        while (need[1] >= 0) {
            double selmax = max(list);
            int maxid = findid(list2, selmax);
            if(maxid!=-1){
                unit = selmoney[maxid];
                if (need[1] - repertory[maxid] > 0) {
                    need[1] -= repertory[maxid];
                    max += unit;
                } else if (need[1] - repertory[maxid] == 0){
                    need[1] -= repertory[maxid];
                    max += unit;
                   break;
                }else {
                    max += (double) selmoney[maxid]/((double) repertory[maxid]/need[1]);
                    break;
                }
                list.remove(maxid);
            }
        }
//        maxid=max(list);
//        unit=list.get(maxid)*(double)repertory[maxid];
//        max+=unit;
        System.out.printf("%.2f", max);

    }

    public static double max(List<Double> list) {
        double max = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > max) {
                max = list.get(i);
            }
        }
        return max;//返回最大利润
    }
    public static int findid(List<Double> list,double x){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == x) {
               return i;
            }
        }return -1;
    }
}
