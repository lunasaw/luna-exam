package com.luna.jvm.dayone;

import java.util.regex.Pattern;

/**
 * @author luna@mac
 * 2021年04月01日 20:29:00
 */
public class Panater {

    public static void main(String[] args) {
        String content = "abc 123";

        String pattern = ": \\A \\Z \\d";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
    }
}
