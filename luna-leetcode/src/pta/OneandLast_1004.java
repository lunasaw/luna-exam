package pta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OneandLast_1004 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int num = Integer.parseInt(input.nextLine());
        List<String> list=new ArrayList<String>();
        while (num > 0) {
            String str=input.nextLine();
            list.add(str);
            num--;
        }

        int max=0,min=Integer.MAX_VALUE;
        int minIndex=0,maxIndex=0;
        StringBuilder maxString=new StringBuilder();
        StringBuilder minString=new StringBuilder();

        for (int i=0;i<list.size();i++) {
            String x=list.get(i);
            String[] temp=x.split(" ");
            int tempScore=Integer.parseInt(temp[2]);
            if (min>tempScore) {
                min=tempScore;
                minIndex=i;
            }
            if (max<tempScore) {
                max=tempScore;
                maxIndex=i;
            }
        }

        String[] maxTemp=list.get(maxIndex).split(" ");
        String[] minTemp=list.get(minIndex).split(" ");

        minString.append(minTemp[0]);
        minString.append(" ");
        minString.append(minTemp[1]);

        maxString.append(maxTemp[0]);
        maxString.append(" ");
        maxString.append(maxTemp[1]);

        System.out.println(maxString);
        System.out.println(minString);

    }
}