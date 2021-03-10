package com.luna.practice.ls;

/**
 * @author czy@win10
 * @date 2019/9/19 7:59
 */
public class Sort {
        public static void main(String[] args) {
            Sort sort=new Sort();
            sort.maopao();
            System.out.println("-----------");
            sort.selectSort();
            System.out.println("-----------");

            sort.insert();
        }
        public void maopao(){
            int [] a={6,1,2,3,4}   ;
            for (int i=0;i<a.length;i++){
                for (int j=i;j<a.length-1;j++){
                    if (a[j]>a[j+1]){
                        int t=0;
    //                    t=a[j];
    //                    a[j]=a[j+1];
    //                    a[j+1]=t;

                        swap(a,j,j+1);
                    }
                }
            }
            for (int c:a){
                System.out.print(c+" ");
            }
        }

        public void selectSort(){
            int [] arr={6,1,2,3,4};
            for(int i = 0; i < arr.length; i++){
                int min = i;
                for(int j = i+1; j <arr.length;j++){
                    if(arr[j]<arr[min]){
                        min = j;
                    }
                }
                if(min!=i){
                    swap(arr, i, min);
                }
            }
            for (int c:arr){
                System.out.print(c+" ");
            }
        }

        public void insert() {
            int[] a = {6, 1, 2, 3, 4};
            for (int i = 1; i < a.length; i++) {
                int key = a[i];
                int j = i - 1;
                while (j >= 0 && a[j] > key)//前一个数组元素比当前的大时
                {
                    a[j + 1] = a[j];//将前者赋值给后者
                    j--;
                }
                a[j + 1] = key;
            }

            for (int c : a) {
                System.out.print(c + " ");
            }
        }
            //完成数组两元素间交换
            public static void swap ( int[] arr, int a, int b){
                int temp = arr[a];
                arr[a] = arr[b];
                arr[b] = temp;
            }
}
