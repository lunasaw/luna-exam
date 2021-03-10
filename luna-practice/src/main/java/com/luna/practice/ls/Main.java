package com.luna.practice.ls;

import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/10/23 16:53
 */
public class Main {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner a=new Scanner(System.in);
        String [] number=new String[10];
        String [] name=new String[10];
        String [] borntime=new String[10];
        String [] bornplace=new String[10];
        int [] isborrowed=new int [5];
        int [] borrowtime=new int[5];
        int [] borrowlong=new int[5];
        int [] borrowcount=new int [5];

        number[0]="001";
        name[0]="�������";
        borntime[0]="2019��7��";
        bornplace[0]="�廪��ѧ������";
        isborrowed[0]=0;


        number[1]="002";
        name[1]="�ߵ���ѧ";
        borntime[1]="2019��6��";
        bornplace[1]="ͬ�ô�ѧ������";
        isborrowed[1]=0;

        number[2]="003";
        name[2]="���Դ���";
        borntime[2]="2019��7��";
        bornplace[2]="�廪��ѧ������";
        isborrowed[2]=0;

        number[3]="004";
        name[3]="webǰ��";
        borntime[3]="2019��9��";
        bornplace[3]="�γ�ʦ��ѧԺ������";
        isborrowed[3]=0;

        number[4]="005";
        name[4]="����ԭ��";
        borntime[4]="2019��1��";
        bornplace[4]="�Ͼ���ѧ������";
        isborrowed[4]=0;
        Tushuguan tushuguan=new Tushuguan(number,name,bornplace,isborrowed,borrowtime);

        boolean flag=true;
        int num;
        do{
            System.out.println("******��ӭʹ�ñ�ͼ�����ϵͳ*****");
            System.out.println("******1.�鿴ͼ��*****");
            System.out.println("******2.����ͼ��*****");
            System.out.println("******3.����ͼ��*****");
            System.out.println("******4.�黹ͼ��*****");
            System.out.println("******5.ɾ��ͼ��*****");
            System.out.println("******6.�˳�ϵͳ*****");
            System.out.println("�������Ĳ���");
            int input=a.nextInt();
            switch(input){
                case 1://��ѯ
                    tushuguan.chaxun(tushuguan.getName(),tushuguan.getIsborrowed(),tushuguan.getBorrowcount(),tushuguan.getBorrowlong());
                    break;
                case 2:
                    //����ͼ��;
                    tushuguan.xinzeng(tushuguan.getName());
                    break;
                case 3:
                    //����ͼ��;
                    tushuguan.jieyue(tushuguan.getName(),tushuguan.getIsborrowed(),tushuguan.getBorrowtime(),tushuguan.getBorrowlong(),tushuguan.getBorrowcount());
                    break;
                case 4:
                    //�黹ͼ��;
                    tushuguan.guihuan(tushuguan.getName(),tushuguan.getIsborrowed(),tushuguan.getBorrowlong());
                    break;
                case 5:
                    //ɾ��ͼ��;
                    tushuguan.shanchu(tushuguan.getName(),tushuguan.getBorrowlong(),tushuguan.getBorrowcount());
                    break;
                case 6:
                    //�˳�ϵͳ;
                    flag=false;
                    break;
                default:
                    flag=false;
                    //������ˣ��˳�
                    break;
            }
            if(!flag){
                //����ѭ��
                break;
            }else{
                System.out.print("������0�������˵�");
                num=a.nextInt();
            }
        }while(num==0);
        System.out.println("ллʹ��");
    }
}
