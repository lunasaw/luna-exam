package com.luna.practice.lyx.basic;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/**
 * @author luna@mac
 * @className LuckyDraw.java
 * @description TODO
 * @createTime 2021年03月04日 16:11:00
 */
public class LuckyDraw {

    public static List<String> getNames(String file) {
        try {
            return Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getRandomName(List<String> names) {
        Random random = new Random();
        return names.get(random.nextInt(names.size()));
    }

    public static void main(String[] args) {
        List<String> names = getNames("luna-practice/src/main/resources/lyxdoc/names.txt");
//        System.out.println(names);
        System.out.println(getRandomName(names));
    }
}
