package com.luna.practice.ls.dispatching.bean;


import com.luna.practice.ls.dispatching.common.Direct;

/**
 * 一次任务请求
 *
 * @author luna
 */
public class Task {

    /**
     * 任务名
     */
    public String name;
    /**
     * 请求所在楼层
     */
    public int from;
    /**
     * 请求去往楼层
     */
    public int to;

    public Task(String name, int from, int to) {
        this.name = name;
        this.from = from;
        this.to = to;
    }

    /**
     * 获取请求方向
     * @return
     */
    public Direct getDirect() {
        return from - to > 0 ? Direct.DOWN : Direct.UP;
    }


    @Override
    public String toString() {
        return String.format("%s %d->%d", name, from, to);
    }
}
