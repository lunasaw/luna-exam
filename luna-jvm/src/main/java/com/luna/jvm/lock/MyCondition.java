package com.luna.jvm.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luna@mac
 * 2021年04月06日 09:53
 */
public class MyCondition {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        for (int i = 1; i <= 3; i++) {
            new Thread(() -> {
                shareResource.print(1);
            }, "AA").start();
        }
        for (int i = 1; i <= 3; i++) {
            new Thread(() -> {
                shareResource.print(2);
            }, "BB").start();
        }
        for (int i = 1; i <= 3; i++) {
            new Thread(() -> {
                shareResource.print(3);
            }, "CC").start();
        }
    }
}

class ShareResource {
    private Lock     lock   = new ReentrantLock();

    public Condition c1     = lock.newCondition();

    public Condition c2     = lock.newCondition();

    public Condition c3     = lock.newCondition();

    private int      number = 1;

    public void print(int i) {
        lock.lock();
        try {
            switch (i) {
                case 1:
                    while (i != number) {
                        c1.await();
                    }
                    for (int j = 0; j < 3; j++) {
                        System.out.println(Thread.currentThread().getName() + "\t" + number);
                    }
                    number = 2;
                    c2.signal();
                case 2:
                    while (i != number) {
                        c2.await();
                    }
                    for (int j = 0; j < 3; j++) {
                        System.out.println(Thread.currentThread().getName() + "\t" + number);
                    }
                    number = 3;
                    c3.signal();
                case 3:
                    while (i != number) {
                        c3.await();
                    }
                    for (int j = 0; j < 3; j++) {
                        System.out.println(Thread.currentThread().getName() + "\t" + number);
                    }
                    number = 1;
                    c1.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();

        }
    }
}
