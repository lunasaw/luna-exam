package com.luna.self.design.observe;

import javax.security.auth.Subject;
import java.util.Observable;
import java.util.Observer;

/**
 * @author luna@mac
 * @className MyObserver.java
 * @description TODO
 * @createTime 2021年03月11日 15:58:00
 */
public class RepoterObservable extends Observable {

    private String news;

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
        setChanged(); // 必须调用这个方法来通知Observer状态发生了改变
        notifyObservers("you");
    }

}

/**
 * 新华社
 */
class XinhuaNewspaperObserver implements Observer {

    private String newspaperName = "新华社";

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("'" + newspaperName + "'读取推送信息:" + arg);
        System.out.println("'" + newspaperName + "'读取拉取信息:" + ((RepoterObservable)o).getNews());
    }

}

/**
 * 人民日报
 */
class PeopleDailyNewspaperObserver implements Observer {

    private String newspaperName = "人民日报";

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("'" + newspaperName + "'读取推送信息:" + arg);
        System.out.println("'" + newspaperName + "'读取拉取信息:" + ((RepoterObservable)o).getNews());
    }
}

class Client {
    public static void main(String[] args) {
        RepoterObservable subject = new RepoterObservable();
        PeopleDailyNewspaperObserver peopleDailyNewspaperObserver = new PeopleDailyNewspaperObserver();
        XinhuaNewspaperObserver xinhuaNewspaperObserver = new XinhuaNewspaperObserver();

        subject.addObserver(peopleDailyNewspaperObserver);
        subject.addObserver(xinhuaNewspaperObserver);

        subject.setNews("日韩贸易战最新消息,韩方一再要求撤回贸易管制 日方无松动迹象...");
    }
}