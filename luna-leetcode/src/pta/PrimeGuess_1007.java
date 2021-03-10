package pta;

import java.io.*;
import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/9/10 22:41
 */
public class PrimeGuess_1007 {
//0007
        public static void main(String[] args) throws IOException {
//            StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
//            PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
            Scanner in =new Scanner(System.in);
            int N=in.nextInt();
            int  k = 0 , count = 0;
            int[] arr = new int[N + 1];

            for(int i = 2 ; i <= N ; i++) {
                int j = 2;
                for( ; j <= Math.sqrt(i) ; j++) {
                    if(i % j == 0)
                        break;
                }
                if(j > Math.sqrt(i))
                {
                    arr[k++] = i;
                }//判断素数
            }
            for(int i = 0 ; i < N ; i++) {
                if(arr[i + 1] - arr[i] == 2){
                    count++;
                }
            }
            System.out.println(count);

        }

}
