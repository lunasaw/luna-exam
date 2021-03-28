# Spring事务管理及几种简单的实现



事务是逻辑上的一组操作，这组操作要么全部成功，要么全部失败，最为典型的就是银行转账的案例：

A要向B转账，现在A，B各自账户中有1000元，A要给B转200元，那么这个转账就必须保证是一个事务，防止中途因为各种原因导致A账户资金减少而B账户资金未添加，或者B账户资金添加而A账户资金未减少，这样不是用户有损失就是银行有损失，为了保证转账前后的一致性就必须保证转账操作是一个事务。

事务具有的ACID特性

首先，这篇文章先提及一些Spring中事务有关的API，然后分别实现编程式事务管理和声明式事务管理，其中声明式事务管理分别使用基于`TransactionProxyFactoryBean`的方式、基于AspectJ的XML方式、基于注解方式进行实现。

首先，我们简单看一下Spring事务管理需要提及的接口，Spring事务管理高层抽象主要包括3个接口

`PlatformTransactionManager` :事务管理器(用来管理事务，包含事务的提交，回滚)
`TransactionDefinition` :事务定义信息(隔离，传播，超时，只读)
`TransactionStatus` :事务具体运行状态

Spring根据事务定义信息(TransactionDefinition)由平台事务管理器(PlatformTransactionManager)真正进行事务的管理，在进行事务管理的过程中，事务会产生运行状态，状态保存在TransactionStatus中

**PlatformTransactionManager**:

Spring为不同的持久化框架提供了不同的PlatformTransactionManager如:
在使用Spring JDBC或iBatis进行持久化数据时，采用DataSourceTransactionManager
在使用Hibernate进行持久化数据时使用HibernateTransactionManager

**TransactionDefinition**:

`TransactionDefinition`接口中定义了一组常量，包括事务的隔离级别，事务的传播行为，超时信息，其中还定义了一些方法，可获得事务的隔离级别，超时信息，是否只读。

传播行为**主要解决**业务层方法之间的相互调用产生的事务应该如何传递的问题。

`TransactionDefinition`中定义的属性常量如下：

| Field(属性)                  | Description(描述)                                            |
| ---------------------------- | ------------------------------------------------------------ |
| ISOLATION_DEFAULT            | 使用底层数据存储的默认隔离级别                               |
| ISOLATION_READ_COMMITTED     | 表示防止脏读;可能会发生不可重复的读取和幻像读取              |
| ISOLATION_READ_UNCOMMITTED   | 表示可能会发生脏读，不可重复的读取和幻像读取                 |
| ISOLATION_REPEATABLE_READ    | 表示禁止脏读和不可重复读;可以发生幻影读取                    |
| ISOLATION_SERIALIZABLE       | 表示可以防止脏读，不可重复的读取和幻像读取                   |
| PROPAGATION_MANDATORY        | 支持当前交易;如果不存在当前事务，则抛出异常                  |
| **PROPAGATION_NESTED**       | 如果当前事务存在，则在嵌套事务中执行，其行为类似于PROPAGATION_REQUIRED |
| PROPAGATION_NEVER            | 不支持当前交易;如果当前事务存在，则抛出异常                  |
| PROPAGATION_NOT_SUPPORTED    | 不支持当前交易;而是总是非事务地执行                          |
| **PROPAGATION_REQUIRED**     | 支持当前交易;如果不存在，创建一个新的                        |
| **PROPAGATION_REQUIRES_NEW** | 创建一个新的事务，挂起当前事务（如果存在）                   |
| PROPAGATION_SUPPORTS         | 支持当前交易;如果不存在，则执行非事务性的                    |
| TIMEOUT_DEFAULT              | 使用底层事务系统的默认超时，如果不支持超时，则为none         |

TransationStatus:

在该接口中提供了一些方法:

| Method             | Description                                                  |
| ------------------ | ------------------------------------------------------------ |
| flush()            | 将基础会话刷新到数据存储（如果适用）：例如，所有受影响的Hibernate / JPA会话 |
| hasSavepoint()     | 返回此事务是否内部携带保存点，也就是基于保存点创建为嵌套事务 |
| isCompleted()      | 返回此事务是否完成，即是否已经提交或回滚                     |
| isNewTransaction() | 返回当前交易是否是新的（否则首先参与现有交易，或者潜在地不会在实际交易中运行） |
| isRollbackOnly()   | 返回事务是否已被标记为仅回滚（由应用程序或由事务基础结构）   |
| setRollbackOnly()  | 设置事务回滚                                                 |

了解了上述接口，接下来我们通过转账案例来实现Spring的事务管理：

数据库中account表如下：

|      | id   | name | money |
| ---- | ---- | ---- | ----- |
| 1    | 1    | aaa  | 1000  |
| 2    | 2    | bbb  | 1000  |
| 3    | 3    | ccc  | 1000  |
| .... | .... | .... | ....  |



------

**1.编程式事务管理实现**：

AccountDao.java:

```
package com.spring.demo1;

/**
 * Created by zhuxinquan on 17-4-27.
 */
public interface AccountDao {
    public void outMoney(String out, Double money);
    public void inMoney(String in, Double money);
}
```

AccountDaoImp.java

```
package com.spring.demo1;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by zhuxinquan on 17-4-27.
 */
public class AccountDaoImp extends JdbcDaoSupport implements AccountDao {
    public void outMoney(String out, Double money) {
        String sql = "update account set money = money - ? where name = ?";
        this.getJdbcTemplate().update(sql, money, out);
    }

    public void inMoney(String in, Double money) {
        String sql = "update account set money = money + ? where name = ?";
        this.getJdbcTemplate().update(sql, money, in);
    }
}
```

AccountService.java

```
package com.spring.demo1;

/**
 * Created by zhuxinquan on 17-4-27.
 */
public interface AccountService {
    public void transfer(String out, String in, Double money);
}
```

AccountServiceImp.java

```
package com.spring.demo1;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by zhuxinquan on 17-4-27.
 */
public class AccountServiceImp implements AccountService{

    private AccountDao accountDao;

    //    注入事务管理的模板
    private TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void transfer(final String out, final String in, final Double money) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                accountDao.outMoney(out, money);
                //此处除0模拟转账发生异常
                int i = 1 / 0;
                accountDao.inMoney(in, money);
            }
        });
    }
}package com.spring.demo1;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by zhuxinquan on 17-4-27.
 */
public class AccountServiceImp implements AccountService{

    private AccountDao accountDao;

    //    注入事务管理的模板
    private TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void transfer(final String out, final String in, final Double money) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                accountDao.outMoney(out, money);
                int i = 1 / 0;
                accountDao.inMoney(in, money);
            }
        });
    }
}
```

创建Spring配置文件applicationContext.xml:

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <context:property-placeholder location="classpath:jdbc.properties"/>

    <!--配置c3p0连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.Driver}"/>
        <property name="jdbcUrl" value="${jdbc.URL}"/>
        <property name="user" value="${jdbc.USERNAME}"/>
        <property name="password" value="${jdbc.PASSWD}"/>
    </bean>

    <!--配置业务层类-->
    <bean id="accountService" class="com.spring.demo1.AccountServiceImp">
        <property name="accountDao" ref="accountDao"/>
        <property name="transactionTemplate" ref="transactionTemplate"/>
    </bean>

    <!--配置Dao的类-->
    <bean id="accountDao" class="com.spring.demo1.AccountDaoImp">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置事务管理模板,Spring为了简化事务管理的代码而提供的类-->
    <bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
        <property name="transactionManager" ref="transactionManager"/>
    </bean>
</beans>
```

编写测试类如下：
SpringDemoTest1.java

```
import com.spring.demo1.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by zhuxinquan on 17-4-27.
 * Spring编程式事务管理
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringDemoTest1 {

    @Resource(name = "accountService")
    private AccountService accountService;

    @Test
    public void demo1(){
        accountService.transfer("aaa", "bbb", 200d);
    }
}
```

------

**2.基于TransactionProxyFactoryBean的声明式事务管理**

Dao与Service代码与1中相同，applicationContext2.xml如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <context:property-placeholder location="classpath:jdbc.properties"/>

    <!--配置c3p0连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.Driver}"/>
        <property name="jdbcUrl" value="${jdbc.URL}"/>
        <property name="user" value="${jdbc.USERNAME}"/>
        <property name="password" value="${jdbc.PASSWD}"/>
    </bean>

    <!--配置业务层类-->
    <bean id="accountService" class="com.spring.demo2.AccountServiceImp">
        <property name="accountDao" ref="accountDao"/>
    </bean>

    <!--配置Dao的类-->
    <bean id="accountDao" class="com.spring.demo2.AccountDaoImp">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置业务层代理-->
    <bean id="accountServiceProxy" class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean">
        <!--配置目标对象-->
        <property name="target" ref="accountService"/>
        <!--注入事务管理器-->
        <property name="transactionManager" ref="transactionManager"/>
        <!--注入事务的属性-->
        <property name="transactionAttributes">
            <props>
                <!--
                prop格式
                    * PROPAGATION   :事务的传播行为
                    * ISOLATION     :事务的隔离级别
                    * readOnly      :只读(不可以进行修改，插入，删除的操作)
                    * -Exception    :发生哪些异常回滚事务
                    * +Exception    :发生哪些异常不回滚事务
                -->
                <prop key="transfer">PROPAGATION_REQUIRED</prop>
                <!--<prop key="transfer">PROPAGATION_REQUIRED,readOnly</prop>-->
                <!--<prop key="transfer">PROPAGATION_REQUIRED, +java.lang.ArithmeticException</prop>-->
            </props>
        </property>
    </bean>
</beans>
```

此时注入时需要选择代理类，因为在代理类中进行增强操作，测试代码如下：
SpringDemoTest2.java

```
import com.spring.demo2.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by zhuxinquan on 17-4-27.
 * Spring声明式事务管理:基于TransactionProxyFactoryBean的方式
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext2.xml")
public class SpringDemoTest2 {


    /*
    此时需要注入代理类：因为代理类进行增强操作
     */
//    @Resource(name = "accountService")
    @Resource(name = "accountServiceProxy")
    private AccountService accountService;

    @Test
    public void demo1(){
        accountService.transfer("aaa", "bbb", 200d);
    }
}
```

------

**3.基于AspectJ的XML声明式事务管理**

在这种方式下Dao和Service的代码也没有改变，applicationContext3.xml如下：
applicationContext3.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:task="http://www.springframework.org/schema/task"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-3.1.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop-3.1.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx-3.1.xsd
       http://www.springframework.org/schema/task
       http://www.springframework.org/schema/task/spring-task-3.1.xsd">

    <context:property-placeholder location="classpath:jdbc.properties"/>

    <!--配置c3p0连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.Driver}"/>
        <property name="jdbcUrl" value="${jdbc.URL}"/>
        <property name="user" value="${jdbc.USERNAME}"/>
        <property name="password" value="${jdbc.PASSWD}"/>
    </bean>

    <!--配置业务层类-->
    <bean id="accountService" class="com.spring.demo3.AccountServiceImp">
        <property name="accountDao" ref="accountDao"/>
    </bean>

    <!--配置Dao的类-->
    <bean id="accountDao" class="com.spring.demo3.AccountDaoImp">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置事务的通知：(事务的增强)-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <!--
                propagation     :事务传播行为
                isolation       :事务的隔离级别
                read-only       :只读
                rollback-for    :发生哪些异常回滚
                no-rollback-for :发生哪些异常不回滚
                timeout         :过期信息
            -->
            <tx:method name="transfer" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>

    <!--配置切面-->
    <aop:config>
        <!--配置切入点-->
        <aop:pointcut id="pointcut1" expression="execution(* com.spring.demo3.AccountService+.*(..))"/>
        <!--配置切面-->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut1"/>
    </aop:config>
</beans>
```

测试类与1中相同，增强是动态织入的，所以此时注入的还是accountService。

------

**4.基于注解的声明式事务管理**

基于注解的方式需要在业务层上添加一个@Transactional的注解。
如下：
AccountServiceImp.java

```
package com.spring.demo4;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhuxinquan on 17-4-27.
 * propagation  :事务的传播行为
 * isolation    :事务的隔离级别
 * readOnly     :只读
 * rollbackFor  :发生哪些异常回滚
 */
@Transactional(propagation = Propagation.REQUIRED)
public class AccountServiceImp implements AccountService {

    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void transfer(String out, String in, Double money) {
        accountDao.outMoney(out, money);
        int i = 1 / 0;
        accountDao.inMoney(in, money);
    }
}
```

此时需要在Spring配置文件中开启注解事务，打开事务驱动
applicationContext4.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:task="http://www.springframework.org/schema/task"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-3.1.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop-3.1.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx-3.1.xsd
       http://www.springframework.org/schema/task
       http://www.springframework.org/schema/task/spring-task-3.1.xsd">

    <context:property-placeholder location="classpath:jdbc.properties"/>

    <!--配置c3p0连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.Driver}"/>
        <property name="jdbcUrl" value="${jdbc.URL}"/>
        <property name="user" value="${jdbc.USERNAME}"/>
        <property name="password" value="${jdbc.PASSWD}"/>
    </bean>

    <!--配置业务层类-->
    <bean id="accountService" class="com.spring.demo4.AccountServiceImp">
        <property name="accountDao" ref="accountDao"/>
    </bean>

    <!--配置Dao的类-->
    <bean id="accountDao" class="com.spring.demo4.AccountDaoImp">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--开启注解事务  打开事务驱动-->
    <tx:annotation-driven transaction-manager="transactionManager"/>
</beans>
```

测试类与1中相同