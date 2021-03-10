package com.luna.practice.lyx.basic;

import java.io.File;

/**
 * @author luna@mac
 * @className FileTree.java
 * @description TODO
 * @createTime 2021年03月04日 15:18:00
 */
public class FileTree {

    public static void main(String[] args) {
        fileTree(new File("./luna-practice"), 0);
    }

    public static void fileTree(File dir, int i) {
        StringBuffer stringBuffer = new StringBuffer("|--");
        for (int j = 0; j < i; j++) {
            stringBuffer.insert(0, "| ");
        }

        File[] childs = dir.listFiles();
        int length = childs == null ? 0 : childs.length;
        for (int k = 0; k < length; k++) {
            System.out.println(stringBuffer.toString() + childs[k].getName());
            if (childs[k].isDirectory()) {
                fileTree(childs[k], k + 1);
            }
        }
    }
}
