package com.luna.self.design.sta.porxy;

/**
 * @author luna@mac
 * @className Image.java
 * @description TODO
 * @createTime 2021年03月09日 13:43:00
 */
public interface Image {

    public void display();

}

class RealImage implements Image {

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

class ProxyImage implements Image {

    private String    fileName;

    private RealImage realImage;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}

class ProxyPatterDemo {
    public static void main(String[] args) {
        Image image = new ProxyImage("java.jpg");
        image.display();
    }
}