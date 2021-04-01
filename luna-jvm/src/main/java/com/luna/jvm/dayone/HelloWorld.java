package com.luna.jvm.dayone;

/**
 * @author luna@mac
 * @className HelloWorld.java
 * @description TODO
 * @createTime 2021年03月30日 14:55:00
 */
public class HelloWorld {

    public static void main(String[] args) {
        System.out.println(7 ^ 2);
        System.out.println(3 | 5);
        System.out.println(8 & 2);
        /**
         * 1 2 4 8 16 32 64 128
         * eg：
         * sum ^ mark
         * <p>
         * 减标
         * <p/>
         * 有则返回sum 没有返回0 异或
         * 3 ^ 2 == 1
         * 4 ^ 2 == 6 4只能等于4 故不存在2 则加上
         * 5 ^ 2 == 7 5=4+1 故不存在2 则加上
         * 6 ^ 2 == 4 6=4+2 存在则减掉
         * sum ｜ mark
         * <p>
         * 加标
         * <p/>
         * 有则返回mark 没有返回0 除去两者重复的加上 eg 或
         * 3 | 7 == 7 3=2+1 7=1+2+4 ==》1+2+4 =7
         * 3 | 4 == 7
         * 3 | 5 == 7 3=2+1 5=1+4 不能=1+2+2 重复了不可能有相同的标
         * sum & mark
         * <p>
         * 判断标
         * <p/>
         * 有则返回mark 没有返回0 与
         * 1 & 2 == 0
         * 3 & 2 == 2 3=1+2
         * 8 & 2 == 1 只能等于8 不存在2 则返回0
         * 4 & 2 == 0 4不再 2>>1 求和的值
         * 6 & 2 == 2 6=4+2
         */
    }

    public static int computer() {

        new Thread(() -> {

        }).start();
        int i = 0;
        try {
            i = 5 / 0;
        } catch (Exception e) {
            System.out.println("1111");
            i = 1;
            System.out.println(i);
            return i;
        } finally {
            System.out.println("222");
            i = 2;

            System.out.println(i);
            return 2;
        }

    }
}
