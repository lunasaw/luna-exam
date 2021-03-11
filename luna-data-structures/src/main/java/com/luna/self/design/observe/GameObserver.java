package com.luna.self.design.observe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luna@mac
 * @className Game.java
 * @description TODO
 * @createTime 2021年03月11日 14:58:00
 */
public abstract class GameObserver {

    protected GameSubject subject;

    public abstract void update();

}

class GameSubject {
    private List<GameObserver> gameObservers = new ArrayList<GameObserver>();

    private String             name;

    public GameSubject setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public GameSubject(String name) {
        this.name = name;
    }

    public void attach(GameObserver gameObserver) {
        gameObservers.add(gameObserver);
    }

    public void notifyAllObservers() {
        for (GameObserver observer : gameObservers) {
            observer.update();
        }
    }

    public static void main(String[] args) {
        GameSubject gameSubject = new GameSubject("三国战记");
        new ZhangSan(gameSubject);
        new LiSi(gameSubject);
        new WangWu(gameSubject);
        new ZhaoLiu(gameSubject);

        gameSubject.notifyAllObservers();
    }
}

class ZhangSan extends GameObserver {

    public ZhangSan(GameSubject gameSubject) {
        this.subject = gameSubject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("我是张三 我在打游戏==>"+ this.subject.getName());
    }
}

class LiSi extends GameObserver {

    public LiSi(GameSubject gameSubject) {
        this.subject = gameSubject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("我是李四 我在张三看打游戏==>"+ this.subject.getName());

    }
}

class WangWu extends GameObserver {

    public WangWu(GameSubject gameSubject) {
        this.subject = gameSubject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("我是王五 我在看张三打游戏==>"+ this.subject.getName());
    }
}

class ZhaoLiu extends GameObserver {

    public ZhaoLiu(GameSubject gameSubject) {
        this.subject = gameSubject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("我是赵六 我在看张三打游戏==>"+ this.subject.getName());
    }
}