package com.luna.self.design.builder;

/**
 * @author luna@mac
 * @className ComputerBuilderImpl.java
 * @description TODO
 * @createTime 2021年03月08日 17:03:00
 */
public class ComputerBuilderImpl implements ComputerBuilder {

    private Computer computer = new Computer();

    @Override
    public Computer setBrand(String brand) {
        computer.setBrand(brand);
        return computer;
    }

    @Override
    public Computer setCpu(String cpu) {
        computer.setCpu(cpu);
        return computer;
    }

    @Override
    public Computer setGpu(String gpu) {
        computer.setGpu(gpu);
        return computer;
    }

    @Override
    public Computer setArm(String arm) {
        computer.setArm(arm);
        return computer;
    }

    public Computer build() {
        return computer;
    }

    public static void main(String[] args) {
        ComputerBuilderImpl computerBuilder = new ComputerBuilderImpl();
        Computer computer = computerBuilder.build().setArm("4G").setGpu("AMD").setCpu("i5").setBrand("小米");
        System.out.println(computer);
        computer = computerBuilder.build().setArm("8G").setGpu("英伟达").setCpu("i7").setBrand("联想");
        System.out.println(computer);
    }
}
