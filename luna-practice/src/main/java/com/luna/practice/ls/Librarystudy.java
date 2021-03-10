package com.luna.practice.ls;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Librarystudy {


	public static void main(String[] args) {
		int isReturn = 0;
		double money = 1;

		Scanner input = new Scanner(System.in);
		String[]names = new String[100];//�ȳ�ʼ��ʮ���飬���ڿ����
		String[]author = new String[100];
		int []states = new int[100];//ͼ��״̬��0�ɽ裬1�ѽ��
		int []dates = new int[100];//ͼ���������
		int [] counts = new int[502];//ͼ����Ĵ���


			names[0] = "����";
			author[0]="�ʴ�.�ݶ�";
			dates[0] = 3;
			states[0] = 0;
			counts[0] = 10;
			names[1] = "���˴�";
			author[1]="��������";
			dates[1] = 5;
			states[1] = 0;
			counts[1] = 16;
			names[2] = "���صĳ�������";
			author[2]="��������";
			dates[2] = 2;
			states[2] = 0;
			counts[2] = 78;
			names[3] = "��������";
			author[3]="��˹��.��³ķ";
			dates[3] = 8;
			states[3] = 0;
			counts[3] = 18;
			names[4] = "����Թ������";
			author[4]="����.����";
			dates[4] = 11;
			states[4] = 1;
			counts[4] = 38;
			names[5] = "�����˸�";

			author[5]="����.����Ĭ";
			dates[5] = 2;
			states[5] = 0;
			counts[5] = 12;
			names[6] = "�������еľ���";
			author[6]="�������ޱ�";
			dates[6] = 1;
			states[6] = 0;
			counts[6] = 29;
			names[7] = "��Ч������ʮ�����ؼ���";
			author[7]="����.����";
			dates[7] = 1;
			states[7] = 0;
			counts[7] = 58;
			names[8] = "���Ե�����";
			author[8]="����.���ͻ�";
			dates[8] = 5;
			states[8] = 1;
			counts[8] = 9;
			names[9] = "����";
			author[9]="·ң";
			dates[9] = 6;
			states[9] = 1;
			counts[9] = 28;

		try {

			do {
				System.out.println("\"********��ӭ����С��������־ͼ���********\n");
				System.out.println("1.----���鿴ͼ��");
				System.out.println("2.----������ͼ��");
				System.out.println("3.----������ͼ��");
				System.out.println("4.----���黹ͼ��");
				System.out.println("5.----��ɾ��ͼ��");
				System.out.println("6.----���˳�ϵͳ");
				System.out.print("*******��ӭ�´ι���*******\n");
				System.out.println("----------------------------------");

				//��ʾ���
				System.out.println("���������ѡ�������");
				int num = input.nextInt();

				//�����û�������벻ͬ��֧
				switch (num) {

					case 1:
						System.out.println("Welcome to My Library");
						System.out.println("--->�鿴ͼ��\n");
						//�鿴ͼ��
						System.out.println("���\t״̬\t����\t�������\t�������");

						//�����ж�ͼ��״̬
						for (int i = 0; i < names.length; i++) {
							if (names[i] != null) {
								String out = states[i] == 0 ? "�ɽ���":"�ѽ��";
								String time = dates[i] == 0 ? " ":dates[i]+"��";
								System.out.println((i + 1) + "\t" + out + "\t" + names[i] + "\t" + time + "\t" + counts[i]);
							}
						}
						break;
					case 2:
						System.out.println("--->����ͼ��\n");
						//��������װ����ͼ����±�
						int index02 = -1;
						//�����û��λ��װ����ͼ��
						boolean check = false;
						//��ȡ����ͼ����±꣬ͬʱ�ж���û��λ��װ����ͼ��
						for (int i = 0; i < names.length; i++) {
							//�ж���û��λ��װ����ͼ��
							if (names[i] == null) {
								index02 = i;	//��ȡͼ��У��
								check = true;	//��λ��װ����
								break;			//������ǰѭ��
							}
						}

						if (check) {
							//���ͼ��
							System.out.println("������Ҫ��ӵ�ͼ������:");
							names[index02] = input.next();
							System.out.println("��ϲ�㣡��ӡ�" + names[index02] + "���ɹ���");
						}else{
							System.out.println("So Sorry, this shelf is full. Please choose another function");
						}
						break;

//����ͼ��
					case 3:
						boolean isFind = true;
						System.out.println("--->���ͼ��\n");
						System.out.println("������Ҫ�����ͼ�����ƣ�");
						String book = input.next();

						int index03 = -1;
						for (int i = 0; i < names.length; i++) {
							if (names[i].equals(book)) {
								index03 = i;
								isFind = true;
								break;
							}
						}
						if (isFind) {
							if (states[index03] == 0) {
								System.out.println("�������������:");
								int outdate=input.nextInt();

								while(outdate > 31 || outdate < 1){
									System.out.println("��������������������������...");
									outdate=input.nextInt();
								}

								System.out.println("��" + book + "������ɹ���");
								dates[index03]=outdate;
								states[index03]=1;
								counts[index03]++;
							}else{
								System.out.println("��Ҫ���ĵ��顶" + book + "���ѱ������");
							}
						}else{
							System.out.println("û���ҵ�ƥ���ͼ��,��������...");
						}
						break;
					//�黹ͼ��
					case 4:
						System.out.println("--->�黹ͼ��\n");
						System.out.println("������黹ͼ������:");
						String give = input.next();
						int index04 = -1;
						boolean again = false;
						for (int i = 0; i < names.length; i++) {
							if (names[i].equals(give)) {
								index04 = i;
								again = true;
								break;
							}
						}
						if (again) {
							if (states[index04] == 1) {
								System.out.println("������黹����:");
								int giveDate=input.nextInt();

								while(giveDate < dates[index04] || giveDate > 31){
									System.out.println("������������,����������:");
									giveDate=input.nextInt();
								}

								System.out.println("�黹��" + give + "���ɹ���");
								System.out.println("�������Ϊ:" + dates[index04] + "��");
								System.out.println("�黹����Ϊ:" + giveDate + "��");
								System.out.println("Ӧ�����Ϊ:" + (giveDate-dates[index04]) * money + "Ԫ");
								states[index04] = 0;
								dates[index04] = 0;
							}else{
								System.out.println("��Ҫ�黹����δ���,�޷���ɹ黹����");
							}
						}else{
							System.out.println("��Ҫ�黹���鲻����!");
						}
						break;
					case 5:
						System.out.println("--->ɾ��ͼ��\n");
						System.out.println("������Ҫɾ����ͼ�����ƣ�");
						String delete = input.next();

						int index05 = -1;
						boolean isTrue = false;

						for (int i = 0; i < names.length; i++) {
							if (names[i].equals(delete)) {
								index05 = i;
								isTrue = true;
								break;
							}
						}
						if (isTrue) {
							if (states[index05] == 0) {
								for (int i = index05; i < names.length-1; i++) {
									names[i] = names[i+1];
									dates[i] = dates[i+1];
									states[i] = states[i+1];
									counts[i] = counts[i+1];
								}
								names[5] = null;
								System.out.println("��" + delete + "��ͼ��ɾ���ɹ�!");
							}else{
								System.out.println("��Ҫɾ����ͼ�鱻���,�޷�ִ��ɾ������!");
							}
						}else{
							System.out.println("������˼��û�������鼮...");
						}

						break;


					case 6:
						System.out.println("��лʹ��...");
						return;
					default:
						System.out.println("�ܱ�Ǹ��������������������...");
						while(num > 6 || num < 0){
							num = input.nextInt();
						}
						break;
				}

				System.out.println("********************************************\n����0���أ�");
				isReturn = input.nextInt();
				while (isReturn != 0) {
					System.out.println("\n����������������������...(����0����)");
					isReturn = input.nextInt();
				}
			} while (isReturn == 0);

		}catch (InputMismatchException e) {
			System.out.println("���������������˳���������...");
		}
	}
}
