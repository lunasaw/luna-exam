package com.luna.practice.ls;

import java.util.TreeMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class Point {
	private int id;// 点的id
	private boolean flag = false;// 标志是否被遍历
	int sum;// 记录总的点个数

	private TreeMap<Integer, Integer> thisPointMap = new TreeMap<Integer, Integer>();// 该点到各点的距离。
	BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));

	Point(int sum) { // 构造函数 带有顶点个数
		this.sum = sum;
	}

	public void setId(int id) {// 设置顶点id
		this.id = id;
	}

	public int getId() {// 获得顶点id
		return this.id;
	}

	public void changeFlag() {// 修改访问状态。
		this.flag = true;
	}

	public boolean isVisit() {// 查看访问状态
		return flag;
	}

	public void setLenToOther()throws IOException{// 初始化改点到各顶点的距离。
		System.out.println("=======请输入顶点" + (this.id + 1) + "至其他各顶点的边距=======");
		for (int i = 0; i < sum; i++) {
			if (i == this.id) {
				thisPointMap.put(this.id, 0);
			} else {
				System.out.print("至 顶点" + (i + 1) + " 的距离 ：");
				boolean flag =true;
				int len = 0;
				while(flag){
					try {
						len = Integer.valueOf(bufr.readLine());
						flag = false;
					} catch (NumberFormatException e) {
						System.out.print("输入有误，请重新输入：");
					}
				};
				thisPointMap.put(i, len);
			}
		}
	}

	// 该点到顶尖id的 距离。
	public int lenToPointId(int id) {
		return thisPointMap.get(id);
	}
}

class Dijkstra_2 {
	public static void main(String[] args)throws IOException {
		ArrayList<Point> point_arr = new ArrayList<Point>();// 存储点集合
		BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("请输入顶点个数： ");
		int sum = 0;
		boolean flag =true;
		while(flag){
			try {
				sum = Integer.valueOf(bufr.readLine());
				flag = false;
			} catch (NumberFormatException e) {
				System.out.print("输入有误，请重新输入：");
			}
		};
		for (int i = 0; i < sum; i++) {// 初始化
			Point p = new Point(sum);
			p.setId(i);
			p.setLenToOther();
			point_arr.add(p);
		}
		System.out.print("请输入起始顶点 id ：");
		boolean flag2 =true;
		int start = 0;
		while(flag2){
			try {
				start = Integer.valueOf(bufr.readLine())-1;
				if(start > sum-1 || start < 0) {
					throw new NumberFormatException();
				}
				flag2 = false;
			}catch (NumberFormatException e) {
				System.out.print("输入有误，请重新输入：");
			}
		};
		showDijkstra(point_arr, start);// 单源最短路径遍历
	}

	public static void showDijkstra(ArrayList<Point> arr, int i) {
		System.out.print("顶点" + (i + 1));
		arr.get(i).changeFlag();
		Point p1 = getTopointMin(arr, arr.get(i));
		if (p1 == null) {
			return;
		}
		int id = p1.getId();
		showDijkstra(arr, id);

	}

	public static Point getTopointMin(ArrayList<Point> arr, Point p) {
		Point temp = null;
		int minLen = Integer.MAX_VALUE;
		for (int i = 0; i < arr.size(); i++) {
			// 当已访问 或 者是自身或者无该路径时跳过。
			if (arr.get(i).isVisit() || arr.get(i).getId() == p.getId() || p.lenToPointId(i) < 0) {
				continue;
			} else {
				if (p.lenToPointId(i) < minLen) {
					minLen = p.lenToPointId(i);
					temp = arr.get(i);
				}
			}
		}
		if (temp == null) {
			return temp;
		} else {
			System.out.print("  @--" + minLen + "--> ");
		}
		return temp;
	}
}