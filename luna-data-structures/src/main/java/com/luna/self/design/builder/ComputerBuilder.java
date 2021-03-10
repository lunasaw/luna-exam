package com.luna.self.design.builder;

/**
 * @author luna@mac
 * @className ComputerBuilder.java
 * @description TODO
 * @createTime 2021年03月08日 17:01:00
 */
public interface ComputerBuilder {

    public Computer setBrand(String brand);

    public Computer setCpu(String cpu);

    public Computer setGpu(String gpu);

    public Computer setArm(String arm);

}
