package pta;

import java.util.*;

/**
 * @author czy@win10
 * @date 2019/9/17 12:42
 */
public class CountPrime_1013 {

    public static void main(String[] args) {
//        Date datestart=new Date();
//        System.out.println(datestart1);
        long time=new Date().getTime();
        testisprime01();
//        Date dateend =new Date();
//        System.out.println(dateend);888
        System.out.println();
        long end=new Date().getTime();
        System.out.println(end-time+"ms");
    }

    public static void testisprime01(){

        Scanner in =new Scanner(System.in);
        int min=in.nextInt();
        int max=in.nextInt();
        int count=0;
        List<Integer> list=new ArrayList<>();

        for (int i=2;i>0;i++){
            boolean prime =isPrime(i);
            if (prime){
                list.add(i);
                if (list.size()>max){
                    break;
                }
            }
        }
        for (int i=min-1;i<list.size()-2;i++){
            System.out.print(list.get(i));
            count++;
            if (count%10==0){
                System.out.println();
            }else {
                System.out.print(" ");
            }
        }
        System.out.print(list.get(list.size()-1));

        //System.out.print(list);
    }

    public static void testisprime(){
        Scanner scanner = new Scanner(System.in);
        int M = scanner.nextInt();
        int N = scanner.nextInt();
        List<Integer> list;
        list = GetnPrimeList(N);
        for(int i=M-1; i<N; i++){
            System.out.print(list.get(i));
            if((i-M+2)%10 !=0){
                if(i==N-1) break;
                System.out.print(" ");
            }else {
                System.out.println();
            }
        }
    }

    public static List<Integer> GetnPrimeList(int num){
        List<Integer> list = new ArrayList<Integer>();
        int startNumber = 1;
        while(list.size() < num){
            if((IsPrime(startNumber,list))){
                list.add(startNumber);
            }
            startNumber++;
        }
        return list;
    }

    public static boolean isPrime(int n) {

        if(n < 2) return false;

        if(n == 2) return true;

        if(n%2==0) return false;

        for(int i = 3; i*i <= n; i += 2)

            if(n%i == 0) return false;

        return true;

    }

    public static boolean IsPrime(int num,List<Integer> list){
        if(num==1){
            return false;
        }
        int max = (int) Math.sqrt(num);
        for(int n:list){
            if(num%n==0){
                return false;
            }
            if(n>max){
                break;
            }
        }
        return true;
    }
}
