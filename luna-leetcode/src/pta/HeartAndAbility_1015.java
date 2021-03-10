package pta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.Collections.sort;

/**
 * @author czy@win10
 * @date 2019/9/18 13:35
 */
public class HeartAndAbility_1015 {
    public static void main(String[] args) {
        HeartAndAbility_1015 heartAndAbility=new HeartAndAbility_1015();
        heartAndAbility.test();
    }
    public void test(){
        Scanner in =new Scanner(System.in);
        int N = in.nextInt();//人数
        int L = in.nextInt();//及格线--录取最底线
        int H = in.nextInt();//优先录取线
        int M=0;
        List<String> list =new ArrayList<>();
        List<Integer> personscore=new ArrayList<>();
        List<Integer> personscore1=new ArrayList<>();
        List<Integer> personscore2=new ArrayList<>();
        List<String> personscore3=new ArrayList<>();

        String s2 = in.nextLine();//先读取回车字符 不然循环里读入的是空白
        for (int i=0;i<N;i++){
            String s = in.nextLine();
            //System.out.println(s);
            String[] score = s.split("\\s+");
            //System.out.println(score.length);
            if (Integer.parseInt(score[1])>=L&&Integer.parseInt(score[2])>=L){
                list.add(s);
                M++;
            }//达到及格线
            else {
                //未达及格线 总分排序
                personscore3.add(s);
            }
        }
        for (int i=0;i<list.size();i++){
            String[] person = list.get(i).split("\\s+");
            if (Integer.parseInt(person[1])>=H&&Integer.parseInt(person[2])>=H){
                //德才兼备 总分排序
                personscore.add(Integer.parseInt(person[1])+Integer.parseInt(person[2]));
            }
            else if (Integer.parseInt(person[1])>=H&&Integer.parseInt(person[2])<H){
                //德胜才 总分排序
                personscore1.add(Integer.parseInt(person[1])+Integer.parseInt(person[2]));
            }
            else if (Integer.parseInt(person[1])<H&&Integer.parseInt(person[2])<H){
                //“才德兼亡”但尚有“德胜才” 总分排序
                if (Integer.parseInt(person[1])<Integer.parseInt(person[2])) {
                    personscore2.add(Integer.parseInt(person[1]) + Integer.parseInt(person[2]));
                }
            }else{

            }
        }
//        sort(personscore);
//        sort(personscore1);
//        sort(personscore2);
        sort(personscore3);
        personscore=orderByAsc(personscore);
        personscore1=orderByAsc(personscore1);
        personscore2=orderByAsc(personscore2);
            System.out.println(M);
            compare(personscore,list);
        compare(personscore1,list);
        compare(personscore2,list);
        for (String s1:personscore3){
            System.out.println(s1);
        }

    }
    public void compare(List<Integer> lis,List<String> m){
        for (int i=0;i<lis.size();i++){
            for (int j=0;j<m.size();j++){
                //System.out.println(j);
                String[] split = m.get(j).split("\\s+");
                int scores=Integer.parseInt(split[1])+Integer.parseInt(split[2]);
                if (lis.get(i)==scores){
                    System.out.println(m.get(j));
                    break;
                }
            }
        }
    }
    private List<Integer> orderByAsc(List<Integer> scores){


        for (int i = 0; i < scores.size() - 1; i++) {
            for (int j = 1; j < scores.size() - i; j++) {
                Integer a;
                if ((scores.get(j - 1)).compareTo(scores.get(j)) > 0) {

                    a = scores.get(j - 1);
                    scores.set((j - 1), scores.get(j));
                    scores.set(j, a);
                }
            }
        }
        return scores;
    }
}
