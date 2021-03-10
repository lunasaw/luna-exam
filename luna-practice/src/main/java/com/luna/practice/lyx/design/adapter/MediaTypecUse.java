package com.luna.practice.lyx.design.adapter;

/**
 * @author luna@mac
 * @className MediaUse.java
 * @description TODO
 * @createTime 2021年03月09日 11:12:00
 */
public interface MediaTypecUse {

    public void useMedia(String type);

}

class MediaTypecUseAdapter implements MediaTypecUse {

    public static final String USB   = "USB";
    public static final String HDMI  = "HDMI";
    public static final String POWER = "POWER";
    TypecAdapter               typecAdapter;

    @Override
    public void useMedia(String type) {
        if (type == USB) {
            typecAdapter = new Disk();
            typecAdapter.useUdisk();
        } else if (type == HDMI) {
            typecAdapter = new Hdmi();
            typecAdapter.useHdmi();
        } else if (type == POWER) {
            typecAdapter = new Power();
            typecAdapter.usePower();
        }
    }
}

class AdapterTest {

    public static void main(String[] args) {
        MediaTypecUseAdapter mediaTypecUseAdapter = new MediaTypecUseAdapter();
        mediaTypecUseAdapter.useMedia(MediaTypecUseAdapter.HDMI);
        mediaTypecUseAdapter.useMedia(MediaTypecUseAdapter.USB);
        mediaTypecUseAdapter.useMedia(MediaTypecUseAdapter.POWER);
    }
}
