package com.luna.practice.ls.dispatching.algorithm;



import com.luna.practice.ls.dispatching.bean.Task;

import java.util.List;

/**
 * @author relish
 * @since 2018/08/21
 */

public class FCFS extends Executor {

    public FCFS(List<Task> tasks, int initPos) {
        super(tasks, initPos);
    }

    @Override
    protected void dispatching() {

    }

    @Override
    protected String algorithmName() {
        return "1)先来先服务算法（FCFS）:";
    }
}
