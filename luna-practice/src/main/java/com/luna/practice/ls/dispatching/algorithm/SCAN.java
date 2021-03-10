package com.luna.practice.ls.dispatching.algorithm;


import com.luna.practice.ls.dispatching.bean.Task;
import com.luna.practice.ls.dispatching.common.Direct;
import com.luna.practice.ls.dispatching.comparator.ScanComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luna
 * @since 2020/12/19
 */
public class SCAN extends Executor {

    /**
     * 移动方向
     */
    private Direct direct;
    /**
     * 最低层
     */
    private int min;
    /**
     * 最高层
     */
    private int max;

    public SCAN(List<Task> tasks, int initPos, Direct direct, int min, int max) {
        super(tasks, initPos);
        this.direct = direct;
        this.min = min;
        this.max = max;
    }

    @Override
    protected void dispatching() {
        List<Task> visit = new ArrayList<Task>(taskList);
        visit.sort(new ScanComparator(initPos, direct));
        taskList.clear();
        taskList.addAll(visit);
    }

    @Override
    protected String algorithmName() {
        return "3)扫描算法（SCAN）:";
    }

    @Override
    public void exec() {
        System.out.format(algorithmName() + "当前电梯位于第%s层, 对如下乘客进行服务:\n", initPos);
        for (Task task : taskList) {
            System.out.format("%d->%d ", task.from, task.to);
        }
        System.out.println("\n请求     任务     移动楼层数");

        this.dispatching();

        int totalTime = 0;
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            int d = Math.abs(task.from - task.to);
            totalTime += d;
            System.out.format("   %d     %s:%2d->%2d       %2d\n", i, task.name, task.from, task.to, Math.abs(task.from - task.to));
        }
        totalTime = (max - min) * 2;
        System.out.println("总移动距离: " + totalTime);
        double ave = totalTime / 1.0 / taskList.size();
        System.out.format("平均每次服务的距离: %.1f\n", ave);
    }
}
