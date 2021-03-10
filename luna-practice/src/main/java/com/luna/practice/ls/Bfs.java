package com.luna.practice.ls;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
 
/**
 * bfs走迷宫
 * @author 子墨
 * @datetime 2019年3月29日 下午7:12:59
 */
public class Bfs {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new FileInputStream(new File("luna-practice/src/main/resources/lsdoc/map.txt")));
		List<String> list = new ArrayList<>();
		while (in.hasNext()) {
			list.add(in.nextLine());
		}
 
		char map[][] = new char[list.size()][list.get(0).length()];
		char originalMap[][] = new char[list.size()][list.get(0).length()];
		
		for (int i = 0; i < list.size(); i++) {
			String temp = list.get(i);
			for (int j = 0; j < temp.length(); j++) {
				map[i] = temp.toCharArray();
				originalMap[i] = temp.toCharArray();
			}
		}
		
		Point start = new Point(0,0);
		Point end = new Point(map.length - 1, map[0].length - 1);
		
		bfs(start,end,map,originalMap);
		
	}
 
	// 标记下左右上，字典顺序
	static int dirX[] = {1,0,0,-1};
	static int dirY[] = {0,-1,1,0};
	static char directions[] = {'D','L','R','U'};
	
	
	public static void bfs(Point current, Point end, char[][] map,char[][] originalMap) {
		Queue<Point> queue = new LinkedList<>();
		queue.add(current);
		
		while (!queue.isEmpty()) {
			current = queue.poll();
 
			// 到达终点
			if (current.x == end.x && current.y == end.y) {
				String path = current.path;
				Point temp = new Point(0, 0);
				
				// 回溯原始迷宫，将最短路径用'#'标记出来,方便输出
				originalMap[temp.x][temp.y] = '#';
				for (int i = 0; i < path.length(); i++) {
					char ch = path.charAt(i);
					switch (ch) {
					case 'U':
						temp.x = temp.x - 1;
						break;
					case 'D':
						temp.x = temp.x + 1;
						break;
					case 'L':
						temp.y = temp.y - 1;
						break;
					case 'R':
						temp.y = temp.y + 1;
						break;
					}
					originalMap[temp.x][temp.y] = '#';
				}
				
				// 输出结果
				System.out.println(path.length()+","+path);
				printMap(originalMap);
			}
			
			// 遍历方向
			for (int i = 0; i < 4; i++) {
				int x = current.x + dirX[i];
				int y = current.y + dirY[i];
				String path = current.path + directions[i];
				
				Point temp = new Point(x, y, path);
				
				if (temp.x >= 0 && temp.x < map.length && temp.y >= 0 && temp.y < map[0].length && map[temp.x][temp.y] == '0') {
					queue.add(temp);
					map[temp.x][temp.y] = '#';
				}
			}
		}
	}
	
	public static void printMap(char map[][]) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				/*if (map[i][j] == '#') {
					System.out.print('#');
				}else {
					System.out.print(' ');
				}*/
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	static class Point{
		int x;
		int y;
		String path;// 从起始点到当前点走过的路径
		
		public Point(int x,int y) {
			this(x, y ,"");
		}
		
		public Point(int x,int y,String path) {
			this.x = x;
			this.y = y;
			this.path = path;
		}
	}
}