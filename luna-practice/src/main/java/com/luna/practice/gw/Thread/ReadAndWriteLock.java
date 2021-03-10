package com.luna.practice.gw.Thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Luna@win10
 * @date 2020/5/14 14:11
 */
public class ReadAndWriteLock {

    /**
     * 操作对象
     */
    private Map<String, String>              map                    = new HashMap<>();

    private ReentrantReadWriteLock           reentrantReadWriteLock = new ReentrantReadWriteLock();

    /**
     * 读锁
     */
    private ReentrantReadWriteLock.ReadLock  readLock               = reentrantReadWriteLock.readLock();

    /**
     * 写锁
     */
    private ReentrantReadWriteLock.WriteLock writeLock              = reentrantReadWriteLock.writeLock();

    public String get(String key) {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "已加锁:读操作执行:");
            Thread.sleep(3000);
            return map.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            System.out.println(Thread.currentThread().getName() + "读操作已解锁");
            readLock.unlock();
        }
    }

    public void setMap(String key, String value) {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "已加锁:写操作开始:");
            map.put(key, value);
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "写操作已解锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        ReadAndWriteLock readAndWriteLock = new ReadAndWriteLock();
        readAndWriteLock.setMap("key1", "value1");
        new Thread("读线程1") {
            @Override
            public void run() {
                System.out.println(readAndWriteLock.get("key1"));
            }
        }.start();
        new Thread("读线程2") {
            @Override
            public void run() {
                System.out.println(readAndWriteLock.get("key1"));
            }
        }.start();
    }

}
