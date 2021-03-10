package com.luna.self.design.adapter;

/**
 * @author luna@mac
 * @className AdvancedMediaPlayer.java
 * @description TODO
 * @createTime 2021年03月09日 10:50:00
 */
public interface AdvancedMediaPlayer {

    public void playVlc(String fileName);

    public void playMp4(String fileName);
}

class VlcPlayer implements AdvancedMediaPlayer {

    @Override
    public void playVlc(String fileName) {
        System.out.println("VlcPlayer");
    }

    @Override
    public void playMp4(String fileName) {

    }

}

class Mp4Player implements AdvancedMediaPlayer {

    @Override
    public void playVlc(String fileName) {

    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("Mp4Player" + fileName);

    }
}