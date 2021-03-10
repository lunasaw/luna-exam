package com.luna.self.design.strategy;

/**
 * @author luna@mac
 * @className Brocade.java
 * @description TODO
 * @createTime 2021年03月10日 19:34:00
 */
public interface Brocade {

    void strategy();
}

class Context {
    private Brocade brocade;

    public Context(Brocade brocade) {
        this.brocade = brocade;
    }

    public void executeBrocade() {
        brocade.strategy();
    }

    public static void main(String[] args) {
        Context context = new Context(new TacticsFirst());
        context.executeBrocade();

        Context context2 = new Context(new TacticsSecond());
        context2.executeBrocade();

        Context context3 = new Context(new TacticsThird());
        context3.executeBrocade();

    }
}
class TacticsFirst implements Brocade {

    @Override
    public void strategy() {
        System.out.println("策略一:阻止不去拜见吴国太（不告诉吴老太提前的事情）");

    }

}

class TacticsSecond implements Brocade {

    @Override
    public void strategy() {
        System.out.println("策略二:利用温柔乡不让刘备回去");

    }

}

class TacticsThird implements Brocade {

    @Override
    public void strategy() {
        System.out.println("策略三:直接派人阻止截杀");

    }

}
