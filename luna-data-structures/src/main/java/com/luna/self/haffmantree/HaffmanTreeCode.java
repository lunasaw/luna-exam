package com.luna.self.haffmantree;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import sun.jvm.hotspot.runtime.Bytes;

import java.util.*;

/**
 * @author luna@mac
 * @className HaffmanTree.java
 * @description TODO
 * 源码发送 字母转ascll 转二进制
 * 字母出现次数作为权值排序  构建haffman树 左为0 右为1
 * @createTime 2021年03月04日 16:47:00
 */
public class HaffmanTreeCode {

    public static List<NodeCode> createTree(int[] arr) {
        ArrayList<NodeCode> list = Lists.newArrayList();
        for (int i = 0; i < arr.length; i++) {
            list.add(new NodeCode(arr[i]));
        }
        return list;
    }

    public static List<NodeCode> createTree(int[] arr, char[] chars) {
        ArrayList<NodeCode> list = Lists.newArrayList();
        for (int i = 0; i < arr.length; i++) {
            list.add(new NodeCode(arr[i], chars[i]));
        }
        return list;
    }

    public static List<NodeCode> createTree(String s) {
        ArrayList<NodeCode> list = Lists.newArrayList();
        char[] chars = s.toCharArray();
        HashMap<Character, Integer> hashMap = Maps.newHashMap();
        for (char c : chars) {
            if (hashMap.containsKey(c)) {
                hashMap.put(c, hashMap.get(c) + 1);
            } else {
                hashMap.put(c, 1);
            }
        }

        for (Map.Entry<Character, Integer> entry : hashMap.entrySet()) {
            list.add(new NodeCode(entry.getValue(), entry.getKey()));
        }
        return list;
    }


    public static void preOrder(NodeCode root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("空树");
        }
    }

    public static Map<Character, String> haffmanCodeCreate(NodeCode nodeCode) {
        return haffmanCodeCreate(nodeCode, "", new StringBuilder());
    }

    /**
     * 字符串利用哈夫曼编码返回二进制串
     *
     * @param content 待编码内容
     * @return 二进制串
     */
    public static String haffmanCodeCreate2Binary(String content) {
        List<NodeCode> tree = createTree(content);
        Collections.sort(tree);
        NodeCode node = haffmanTree(tree);
        Map<Character, String> characterStringMap = haffmanCodeCreate(node);
        System.out.println(characterStringMap);
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = content.toCharArray();
        for (char c : chars) {
            stringBuilder.append(characterStringMap.get(c));
        }
        return stringBuilder.toString();
    }


    /**
     * 创建haffman编码表
     *
     * @param nodeCode      跟结点
     * @param code          当前编码
     * @param stringBuilder
     * @param codeMap
     * @return
     */
    public static Map<Character, String> haffmanCodeCreate(NodeCode nodeCode, String code, StringBuilder stringBuilder, Map<Character, String> codeMap) {
        StringBuilder builder = new StringBuilder(stringBuilder);
        builder.append(code);
        if (nodeCode != null) {
            // 当前结点是叶子结点 还是非叶子结点
            if (nodeCode.c == null) {
                // 非叶子节点
                codeMap = haffmanCodeCreate(nodeCode.left, "0", builder, codeMap);
                codeMap = haffmanCodeCreate(nodeCode.right, "1", builder, codeMap);
            } else {
                // 叶子节点 找到某个叶子节点最后
                codeMap.put(nodeCode.c, builder.toString());
            }
        }

        return codeMap;
    }


    private static Map<Character, String> hashMap = Maps.newHashMap();

    /**
     * 传入Node结点的所有叶子结点的Haffman编码得到并放入Map<Byte, String>集合
     *
     * @param nodeCode
     * @param code     左为0 右为1
     */
    public static Map<Character, String> haffmanCodeCreate(NodeCode nodeCode, String code, StringBuilder stringBuilder) {
        StringBuilder builder = new StringBuilder(stringBuilder);
        builder.append(code);
        if (nodeCode != null) {
            // 当前结点是叶子结点 还是非叶子结点
            if (nodeCode.c == null) {
                // 非叶子节点
                haffmanCodeCreate(nodeCode.left, "0", builder);
                haffmanCodeCreate(nodeCode.right, "1", builder);
            } else {
                // 叶子节点 找到某个叶子节点最后
                hashMap.put(nodeCode.c, builder.toString());
            }
        }

        return hashMap;
    }

    public static NodeCode haffmanTree(List<NodeCode> tree) {
        while (tree.size() > 1) {
            Collections.sort(tree);
            // 取出权值最小的两个树
            NodeCode left = tree.get(0);
            NodeCode right = tree.get(1);
            // 构建新二叉树
            NodeCode parent = new NodeCode(left.value + right.value, null);
            parent.left = left;
            parent.right = right;

            tree.remove(left);
            tree.remove(right);
            tree.add(parent);
        }
        return tree.get(0);
    }

    public static byte[] haffmanCreateAndZip(String content) {
        String s = haffmanCodeCreate2Binary(content);
        System.out.println(s);
        return haffmanZip(s);
    }

    /**
     * byte数组转二进制字符串
     *
     * @param b
     * @param flag 是否需要异或转
     */
    public static String byte2BinaryString(boolean flag, byte b) {
        int temp = b;
        if (flag) {
            // 按位与 异或（相同0 不同1）
            temp |= 256;
        }
        String s = Integer.toBinaryString(temp);
        if (flag) {
            return s.substring(s.length() - Byte.SIZE);
        } else {
            return s;
        }
    }

    /**
     * 解压哈夫曼数据
     *
     * @param bytes              编码后字节数组
     * @param characterStringMap 编码表
     */
    public static String haffmanUnZip(byte[] bytes, Map<Character, String> characterStringMap) {
        HashMap<String, Character> reverseMap = Maps.newHashMap();
        for (Map.Entry<Character, String> entry : characterStringMap.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }
        StringBuilder stringBuilder = new StringBuilder();
        // byte[] 转二进制字符串
        for (int i = 0; i < bytes.length; i++) {
            String s = byte2BinaryString(i != bytes.length - 1, bytes[i]);
            stringBuilder.append(s);
        }

        StringBuilder stringBuilder2 = new StringBuilder();
        for (int i = 0; i <= stringBuilder.length(); i++) {
            String substring = stringBuilder.substring(0, i);
            if (reverseMap.get(substring) != null) {
                Character character = reverseMap.get(substring);
                stringBuilder2.append(character);
                stringBuilder.delete(0, i);
                i = 0;
            }
        }
        return stringBuilder2.toString();
    }

    /**
     * haffman压缩
     *
     * @param contentByte 待压缩数据
     * @return 8位一组 放入byte数组中
     */
    public static byte[] haffmanZip(String contentByte) {
        StringBuilder stringBuilder = new StringBuilder(contentByte);
        // 统计长度
        int len;
        if (stringBuilder.length() % 8 == 0) {
            len = stringBuilder.length() / Byte.SIZE;
        } else {
            len = stringBuilder.length() / Byte.SIZE + 1;
        }
        // 创建存储压缩后的byte[]
        byte[] bytes = new byte[len];
        int k = 0;
        String strByte = StringUtils.EMPTY;
        // 每8位对应一个byte
        for (int i = 0; i < stringBuilder.length(); i += Byte.SIZE) {
            if (i + Byte.SIZE > stringBuilder.length()) {
                strByte = stringBuilder.substring(i, stringBuilder.length());
            } else {
                strByte = stringBuilder.substring(i, i + Byte.SIZE);
            }
            bytes[k++] = (byte) Integer.parseInt(strByte, 2);
        }
        return bytes;
    }

    public static void main(String[] args) {
        String content = "i like java, i'm doing now";
        System.out.println(content + " 长度: " + content.getBytes().length);
        byte[] bytes = haffmanCreateAndZip(content);
        int haffManLength = bytes.length;
        int length = content.getBytes().length;
        double percent = (double) (length - haffManLength) / length;
        System.out.println(percent);
        System.out.println(haffmanUnZip(bytes, hashMap));
    }

}

class NodeCode implements Comparable<NodeCode> {
    Integer value;

    Character c;

    NodeCode left;

    NodeCode right;

    public NodeCode(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NodeCode{" +
                "value=" + value +
                ", c=" + c +
                '}';
    }

    @Override
    public int compareTo(NodeCode o) {
        return this.value - o.value;
    }

    public NodeCode(Integer value, Character c) {
        this.value = value;
        this.c = c;
    }

    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
}