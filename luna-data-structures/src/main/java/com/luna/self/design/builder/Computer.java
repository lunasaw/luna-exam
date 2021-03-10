package com.luna.self.design.builder;

/**
 * @author luna@mac
 * @className Computer.java
 * @description TODO
 * 题目：编写一个构建者模式，构建出一个电脑对象，要求电脑品牌（小米、联想）、显卡（英伟达、AMD）、CPU（i5、i7、志强）、内存（4G、8G）
 * @createTime 2021年03月08日 17:00:00
 */
public class Computer {

    private String brand;

    private String gpu;

    private String cpu;

    private String arm;

    public String getBrand() {
        return brand;
    }

    public Computer setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getGpu() {
        return gpu;
    }

    public Computer setGpu(String gpu) {
        this.gpu = gpu;
        return this;
    }

    public String getCpu() {
        return cpu;
    }

    public Computer setCpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public String getArm() {
        return arm;
    }

    public Computer setArm(String arm) {
        this.arm = arm;
        return this;
    }

    public Computer(String brand, String gpu, String cpu, String arm) {
        this.brand = brand;
        this.gpu = gpu;
        this.cpu = cpu;
        this.arm = arm;
    }

    public Computer() {}

    @Override
    public String toString() {
        return "Computer{" +
            "brand='" + brand + '\'' +
            ", gpu='" + gpu + '\'' +
            ", cpu='" + cpu + '\'' +
            ", arm='" + arm + '\'' +
            '}';
    }
}
