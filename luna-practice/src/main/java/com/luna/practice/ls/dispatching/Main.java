package com.luna.practice.ls.dispatching;



import com.luna.practice.ls.dispatching.algorithm.Executor;
import com.luna.practice.ls.dispatching.algorithm.FCFS;
import com.luna.practice.ls.dispatching.algorithm.SCAN;
import com.luna.practice.ls.dispatching.algorithm.SSTF;
import com.luna.practice.ls.dispatching.bean.Task;
import com.luna.practice.ls.dispatching.common.Direct;
import com.luna.practice.ls.dispatching.common.Type;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 简便起见:
 * 0 电梯默认初始状态在1层
 * 1 所有楼层都是正整数
 * 2 每层调度所需时间为1s
 *
 * @author relish
 * @since 2018/08/21
 */
public class Main {
    /**
     * 电梯初始位置
     */
    private static final int INIT_POS = 1;
    /**
     * 最底层
     */
    private static final int MIN_FLOOR = 1;
    /**
     * 最高层
     */
    private static final int MAX_FLOOR = 20;
    /**
     * 电梯初始前进方向
     */
    private static final Direct INIT_DIRECT = Direct.UP;
    /**
     * 请求队列
     */
    private static final List<Task> TASKS = Collections.unmodifiableList(new LinkedList<Task>() {
        {
            add(new Task("1", 3, 6));
            add(new Task("2", 4, 11));
            add(new Task("3", 2, 8));
            add(new Task("4", 6, 12));
            add(new Task("5", 17, 2));
            add(new Task("6", 6, 20));
            add(new Task("7", 20, 3));
            add(new Task("8", 11, 8));
            add(new Task("9", 15, 20));
            add(new Task("10", 13, 20));
        }
    });

    public static class ExecutorFactory {
        public static Executor create(Type type) {
            return new SCAN(TASKS, INIT_POS, INIT_DIRECT, MIN_FLOOR, MAX_FLOOR);
        }
    }

    public static void main(String[] args) {
        Executor scan = ExecutorFactory.create(Type.SCAN);
        scan.exec();
    }
}
