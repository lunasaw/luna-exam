package pta;

import java.util.ArrayList;
import java.util.Scanner;

public class PassTheTest_1003 {
    public static void main(String[] args) {
        int flagSum = 0;
        ArrayList<String> stringList = new ArrayList<String>();
        Scanner sc = new Scanner(System.in);
        flagSum = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < flagSum; ++i) {
            stringList.add(sc.nextLine());
        }
        for (String x : stringList) {
            judge(x);
        }
    }
    public static void judge(String s) {
        String pattern = "A*PA+TA*";
        String pattern1 = "PA+T";
        if (s.matches(pattern)) {
            if (s.matches(pattern1)) {//????????????????????????????? true??
                System.out.println("YES");
            } else {
                String temp[] = s.split("P|T");

                int aLength = temp[0].length();
                int bLength = temp[1].length();
                int cLength = temp[2].length();
                //if ((cLength - aLength) / aLength == (bLength - 1)) {
                if(bLength*aLength==cLength){
                    System.out.println("YES");

                } else {
                    System.out.println("NO");
                }
            }
        } else {
            System.out.println("NO");
        }
    }
}