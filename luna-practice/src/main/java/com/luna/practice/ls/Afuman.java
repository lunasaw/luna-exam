package com.luna.practice.ls;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author QBS @win10
 * @version 1.0
 * @date2020/10/15 19:52
 * 类说明
 */

public class Afuman {
    public static Map<String, Integer> map = new HashMap<String, Integer>();
    public static List<String> list = new ArrayList<String>();

    public static void readfile() {
        String filepath = "luna-practice/src/main/resources/lsdoc/ascll.txt";
        try (FileReader filereader = new FileReader(filepath);
             BufferedReader br = new BufferedReader(filereader)) {
            String line = br.readLine();
            String[] strings = line.split("");
            for (String s : strings) {
                if (map.containsKey(s)) {
                    int x;
                    x = map.get(s);
                    x++;
                    map.put(s, x);
                } else {
                    map.put(s, 1);
                    list.add(s);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Huffman.TreeNode huffman(TreeMap<Integer, Character> data) {
        TreeSet<Huffman.TreeNode> tNodes = new TreeSet<>();
        Set<Integer> weights = data.keySet();
        Iterator<Integer> iterator = weights.iterator();
        while (iterator.hasNext()) {
            int weight = iterator.next();
            Huffman.TreeNode tmp = new Huffman.TreeNode(weight, null, null);
            tmp.ch = data.get(weight);
            tNodes.add(tmp);
        }
        while (tNodes.size() > 1) {
            Huffman.TreeNode leftNode = tNodes.pollFirst();
            leftNode.code = "0";
            Huffman.TreeNode rightNode = tNodes.pollFirst();
            rightNode.code = "1";
            Huffman.TreeNode newNode = new Huffman.TreeNode(leftNode.weight + rightNode.weight,
                    leftNode, rightNode);
            tNodes.add(newNode);
        }
        return tNodes.first();
    }

    private static void code(Huffman.TreeNode t) {
        if (t.left != null) {
            t.left.code = t.code + t.left.code;
            code(t.left);
        }
        if (t.right != null) {
            t.right.code = t.code + t.right.code;
            code(t.right);
        }
    }

    public static void print(Huffman.TreeNode root) {
        if (root != null) {
            if (root.left == null && root.right == null) {
                System.out.println(root.ch + " 编码：" + root.code);
            } else {
                print(root.left);
                print(root.right);
            }
        }
    }


    public static void main(String[] args) {
        readfile();
        System.out.println(map);
        TreeMap<Integer, Character> test = new TreeMap<>();
        MapSortUtil mapSortUtil = new MapSortUtil();
        map = mapSortUtil.sortByValueAsc(map);
        int i = 1;
        Map<String, Integer> turnmap = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            map.put(entry.getKey(), (entry.getValue() * i++));
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            test.put(entry.getValue(), Character.valueOf(entry.getKey().charAt(0)));
        }
        Huffman.TreeNode root = huffman(test);
        code(root);
        print(root);
    }
}
