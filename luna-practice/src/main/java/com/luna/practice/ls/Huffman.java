package com.luna.practice.ls;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @Package: com.luna.practice.ls
 * @ClassName: Test_10_15
 * @Author: luna
 * @CreateTime: 2020/10/15 21:48
 * @Description:
 */
public class Huffman {

    private static Integer length = 0;

    /**
     * 读取文件
     *
     * @param path
     * @return
     */
    public static Map<String, Integer> readfile(String path) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try (FileReader filereader = new FileReader(path);
             BufferedReader bufferedReader = new BufferedReader(filereader)) {
            String line = bufferedReader.readLine();
            length = line.length();
            String[] strings = line.split("");
            for (String s : strings) {
                if (map.containsKey(s)) {
                    int x = map.get(s);
                    x++;
                    map.put(s, x);
                } else {
                    map.put(s, 1);
                }
            }
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class TreeNode implements Comparable<Huffman.TreeNode> {
        Huffman.TreeNode left;
        Huffman.TreeNode right;
        int weight;
        char ch;
        String code;

        public TreeNode(int weight, Huffman.TreeNode left, Huffman.TreeNode right) {
            this.weight = weight;
            this.left = left;
            this.right = right;
            this.code = "";
        }

        @Override
        public int compareTo(Huffman.TreeNode o) {
            if (this.weight > o.weight) {
                return 1;
            } else if (this.weight < o.weight) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * 编码节点
     *
     * @param data
     * @return
     */
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

    /**
     * 编码
     *
     * @param t
     */
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

    /**
     * 计算字符bit
     *
     * @param root
     * @param integerMap
     * @return
     */
    public static Map<Integer, Integer> calculate(Huffman.TreeNode root, Map<Integer, Integer> integerMap) {
        if (root != null) {
            if (root.left == null && root.right == null) {
                Integer length = root.code.length();
                if (integerMap.containsKey(length)) {
                    Integer integer = integerMap.get(length);
                    integer++;
                    integerMap.put(length, integer);
                } else {
                    integerMap.put(length, 1);
                }
            } else {
                calculate(root.left, integerMap);
                calculate(root.right, integerMap);
            }
        }
        return integerMap;
    }

    /**
     * 计算压缩比
     *
     * @param integerMap
     * @param length
     * @return
     */
    public static Double getPercent(Map<Integer, Integer> integerMap, Integer length) {
        Double percent = 0.0;
        Iterator<Map.Entry<Integer, Integer>> entries = integerMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, Integer> entry = entries.next();
            Integer value = entry.getValue();
            Integer key = entry.getKey();
            percent += value * key;
        }
        return percent / length;
    }

    /**
     * 打印编码
     *
     * @param root
     */
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

    /**
     * 初始节点
     *
     * @param map
     * @return
     */
    public static TreeMap<Integer, Character> init(Map<String, Integer> map) {
        TreeMap<Integer, Character> characterTreeMap = new TreeMap<>();
        //  对Map进行排序
        map = MapSortUtil.sortByValueAsc(map);
        Iterator<Map.Entry<String, Integer>> entries = map.entrySet().iterator();
        int i = 1;
        while (entries.hasNext()) {
            Map.Entry<String, Integer> entry = entries.next();
            map.put(entry.getKey(), (entry.getValue() * i++));
            characterTreeMap.put(entry.getValue(), Character.valueOf(entry.getKey().charAt(0)));
        }
        System.out.println(characterTreeMap);
        return characterTreeMap;
    }


    public static void main(String[] args) {
        Map<String, Integer> readfile = readfile("luna-practice/src/main/resources/lsdoc/ascll.txt");
        TreeMap<Integer, Character> characterTreeMap = init(readfile);
        Huffman.TreeNode root = huffman(characterTreeMap);
        code(root);
        print(root);
        Map<Integer, Integer> integerMap = new HashMap<>();
        Map<Integer, Integer> calculate = calculate(root, integerMap);
        Double percent = getPercent(calculate, length * 8);
        System.out.println("压缩比:" + percent);
    }
}
