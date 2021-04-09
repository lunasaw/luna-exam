package com.luna.jvm.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author luna@mac
 * 2021年04月08日 21:10
 */
public class MyThread {

    public static void main(String[] args) throws InterruptedException {
        // 当然这里BlockingQueue有7个实现类
        BlockingQueue queue = new ArrayBlockingQueue<Runnable>(5);

        // 这里，如果queue中没有数据，当前线程会一直阻塞在这里，但是当前线程可以被别的线程interrupt()，之后会抛出
        // InterruptedException
        queue.take();

        // 这里，如果queue中没有数据，当前线程会阻塞在这里10s后返回null,如果这在10s中有别的线程往queue放东西
        // 了，那么queue就会解除阻塞，返回queue中的数据.
        queue.poll(10, TimeUnit.SECONDS);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 5, TimeUnit.SECONDS, queue);
    }
}
