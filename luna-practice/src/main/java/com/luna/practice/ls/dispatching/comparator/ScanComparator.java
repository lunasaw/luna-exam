package com.luna.practice.ls.dispatching.comparator;


import com.luna.practice.ls.dispatching.bean.Task;
import com.luna.practice.ls.dispatching.common.Direct;

import java.util.Comparator;

/**
 * @author luna
 * 比较器
 */
public class ScanComparator implements Comparator<Task> {
    /**
     * 初始层数
     */
    private final int initPos;
    /**
     * 移动方向
     */
    private final Direct direct;

    /**
     * 构造初始化
     * @param initPos
     * @param direct
     */
    public ScanComparator(int initPos, Direct direct) {
        this.initPos = initPos;
        this.direct = direct;
    }

    /**
     * 自定义比较器
     * @param o1 任务1
     * @param o2 任务2
     * @return
     */
    public int compare(Task o1, Task o2) {
        int i = ifInitDirectIsUp(o1, o2);
        if (direct == Direct.UP) {
            return i;
        } else {
            return -i;
        }
    }

    public int ifInitDirectIsUp(Task o1, Task o2) {
        //
        int i = biggerOrSmaller(o1, o2);
        // 获取运行方向
        Direct d1 = o1.getDirect();
        Direct d2 = o2.getDirect();
        // 如果两次的任务方向相同
        if (d1 == d2) {
            // 如果方向向上
            if (d1 == Direct.UP) {
                // 如果请求楼层在当前楼层之上
                if (o1.from > initPos) {
                    // 返回向上走的层数
                    return i;
                } else {
                    return 1;
                }
            // 方向向下
            } else {
                // 如果请求楼层在当前楼层之上
                if (o1.from > initPos) {
                    // 向下走的层数
                    return -i;
                } else {
                    return 1;
                }
            }
        // 如果两次任务方向不同
        } else {
            // 如果任务1方向向上
            if (d1 == Direct.UP) {
                // 如果请求楼层在当前楼层之上
                if (o1.from > initPos) {
                    // 返回-1表示 1，2交换
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (o1.from > initPos) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }


    /**
     * 判断当前任务的处理方向
     * @param o1
     * @param o2
     * @return
     */
    public int biggerOrSmaller(Task o1, Task o2) {
        // 如果任务1的请求楼层大于任务2
        if (o1.from > o2.from) {
            // 返回1表示向上
            return 1;
        } else if (o1.from < o2.from) {
            // 返回-1表示向下
            return -1;
        } else {
            // 停止
            return 0;
        }
    }
}
