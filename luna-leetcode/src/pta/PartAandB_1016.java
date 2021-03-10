package pta;

import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/10/4 13:38
 */
public class PartAandB_1016 {

        public static void main(String[] args){
            Scanner in=new Scanner(System.in);
            String a=in.next();
            int pa=in.nextInt();
            String b=in.next();
            int pb=in.nextInt();
            int alen=a.length();
            int blen=b.length();
            int countPa=0,countPb=0;
//		char[] pastr=a.toCharArray();
//		char[] pbstr=b.toCharArray();
            int sum1=0,sum2=0;
//		String s1="";
//		String s2="";
            for(int i=0;i<alen;i++) {
                if(a.charAt(i)-'0'==pa) {
                    countPa++;//查找有多少个一样的
                }
            }
            for(int i=0;i<blen;i++) {
                if(b.charAt(i)-'0'==pb) {
                    countPb++;
                }
            }
            for(int i=0;i<countPa;i++) {
                sum1+=pa;//查找的次数换成整数存储
                pa*=10;
            }
            for(int i=0;i<countPb;i++) {
                sum2+=pb;
                pb*=10;
            }
            System.out.println(sum1+sum2);
        }

}
