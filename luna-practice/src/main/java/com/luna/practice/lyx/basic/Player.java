package com.luna.practice.lyx.basic;

/**
 * @author luna@mac
 * @className Player.java
 * @description TODO
 * @createTime 2021年03月02日 16:57:00
 */
public class Player {

    private Cpu         cpu          = new SpecialCpu();

    private Memory      innerStorage = new InnerStorage();

    private Memory      tfStorage    = new TfStorage();

    private SmartSystem smartSystem  = new SmartSystem();

    public void play(String fileName) {
        smartSystem.play(fileName);
    }

    public static void main(String[] args) {
        Player player = new Player();
        player.play("java.mp3");

    }

}

class PlayerSystem {

    public static void bootloader() {

    }

    public void play(String fileName) {
        System.out.println("我是操作系统 调用CPU拿到字节码 调用解码器播放");
        byte[] processor = Cpu.processor(fileName);
        Decode.decode(processor);
    }

}

class SmartSystem extends PlayerSystem {

}

class GeneralSystem extends PlayerSystem {

}

class Memory {

    public static byte[] read(String fileName) {
        return new byte[0];
    }
}

class InnerStorage extends Memory {

}

class TfStorage extends Memory {

}

class Cpu implements SoftWareDecode {

    public static byte[] processor(String fileName) {
        System.out.println("我是CPU, 从存储器拿到数据");
        return Memory.read(fileName);
    }

}

class SpecialCpu extends Cpu {

}

class GeneralCpu extends Cpu {

}

interface Decode {

    public static void decode(byte[] processor) {
        System.out.println("我是解码器 拿到字节码 解码播放");
    }
}

interface HardWareDecode extends Decode {

}

interface SoftWareDecode extends Decode {

}