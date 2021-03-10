package com.luna.self.design.dynamic.porxy;

/**
 * @author luna_mac
 */
public class RealImage implements Image {

    private String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromDosk(fileName);
    }

    @Override
    public void display() {
        System.out.println("RealImage..." + fileName);
    }

    public void loadFromDosk(String fileName) {
        System.out.println("loadFromDosk..." + fileName);
    }
}
