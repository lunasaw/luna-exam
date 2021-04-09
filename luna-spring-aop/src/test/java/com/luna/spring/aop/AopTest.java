package com.luna.spring.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author luna@mac
 * 2021年04月09日 09:25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AopTest {

    @Autowired
    private HelloAop    helloAop;

    @Autowired
    private HelloAopTwo helloAopTwo;

    @Test
    public void aopTest() {

        helloAop.printAop();

        helloAop.doPrint("qweqweqwe");

        helloAopTwo.printAop();

        helloAopTwo.doPrint("Qewqeqwe");

        helloAopTwo.doPrint2(new Aop());

        helloAopTwo.getName("name123123");
    }
}
