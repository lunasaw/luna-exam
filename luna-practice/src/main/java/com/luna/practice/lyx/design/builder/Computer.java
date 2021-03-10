package com.luna.practice.lyx.design.builder;

/**
 * @author luna@mac
 * @className Computer.java
 * @description TODO
 * @createTime 2021年03月08日 15:50:00
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
}
