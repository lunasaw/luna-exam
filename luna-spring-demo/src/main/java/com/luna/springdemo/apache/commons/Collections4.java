package com.luna.springdemo.apache.commons;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luna@win10
 * @date 2020/5/18 19:44
 */
public class Collections4 {

    @Test
    public void aTest() {
        List<Integer> a = new ArrayList<Integer>();
        List<Integer> b = null;
        List<Integer> c = new ArrayList<Integer>();
        c.add(5);
        c.add(6);
        // 判断集合是否为空
        System.out.println(CollectionUtils.isEmpty(a)); // true
        System.out.println(CollectionUtils.isEmpty(b)); // true
        System.out.println(CollectionUtils.isEmpty(c)); // false

        // 判断集合是否不为空

        System.out.println(CollectionUtils.isNotEmpty(a)); // false
        System.out.println(CollectionUtils.isNotEmpty(b)); // false
        System.out.println(CollectionUtils.isNotEmpty(c)); // true

        // 两个集合间的操作
        List<Integer> e = new ArrayList<Integer>();
        e.add(2);
        e.add(1);
        List<Integer> f = new ArrayList<Integer>();
        f.add(1);
        f.add(2);
        List<Integer> g = new ArrayList<Integer>();
        g.add(12);
        // 比较两集合值
        System.out.println(CollectionUtils.isEqualCollection(e, f)); // true
        System.out.println(CollectionUtils.isEqualCollection(f, g)); // false

        List<Integer> h = new ArrayList<Integer>();
        h.add(1);
        h.add(2);
        h.add(3);
        List<Integer> i = new ArrayList<Integer>();
        i.add(3);
        i.add(3);
        i.add(4);
        i.add(5);
        // 并集
        System.out.println(CollectionUtils.union(i, h)); // [1, 2, 3, 3, 4, 5]
        // 交集
        System.out.println(CollectionUtils.intersection(i, h)); // [3]
        // 交集的补集
        System.out.println(CollectionUtils.disjunction(i, h)); // [1, 2, 3, 4, 5]
        // e与h的差
        System.out.println(CollectionUtils.subtract(h, i)); // [1, 2]
        System.out.println(CollectionUtils.subtract(i, h)); // [3, 4, 5]
    }

    public static void main(String[] args) {

    }

}
