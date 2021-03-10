package pta;

import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/8/21 11:53
 */
public class CallatzGuess_1001 {
    public static void main(String[] args) {
        Scanner in =new Scanner(System.in);
        int n=in.nextInt();
        int count=0;
        while(n!=1){
            count++;
            if (n%2==0){
                n=n/2;

            }else if (n%2==1) {
                n = (3 * n + 1) / 2;
            }
        }
        System.out.println(count);
    }
}
