package com.luna.jvm.hashmap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author luna@mac
 * 2021年04月04日 13:12:00
 */
public class MyHashMap {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>(8);

        ConcurrentHashMap<Object, Object> hashMap = new ConcurrentHashMap<>(8);

        ArrayList<Object> objects = new ArrayList<>(8);

        List<Object> objects1 = Collections.synchronizedList(new ArrayList<>(8));
        objects1.add("hello");

        CopyOnWriteArrayList writeArrayList = new CopyOnWriteArrayList<String>(new ArrayList<>(8));

        HashSet<String> strings = new HashSet<>();
        strings.add("123");
        strings.add("123");

        System.out.println(strings);
        System.out.println(1 << 30);
        System.out.println(10 >> 1);
    }
}
