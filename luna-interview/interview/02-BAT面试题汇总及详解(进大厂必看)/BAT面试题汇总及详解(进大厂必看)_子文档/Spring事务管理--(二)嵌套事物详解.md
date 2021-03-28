# Spring事务管理--(二)嵌套事物详解

# 一、前言

​    最近开发程序的时候，出现数据库自增id跳数字情况，无奈之下dba遍查操作日志，没有delete记录。才开始慢慢来查询事物问题。多久以来欠下的账，今天该还给spring事物。 

# 二、spring嵌套事物

##    1、展示项目代码--简单测springboot项目

整体项目就这么简单，为了方便。这里就只有biz层与service层，主要作为两层嵌套，大家只要看看大概就ok。后面会给出git项目地址，下载下来看一看就明白，力求最简单。

下面我们分情况介绍异常。

Controller 调用层（没有使用它作为外层，因为controller作为外层要在servlet-mvc.xml 配置就ok。但是我觉得比较麻烦，一般也不推荐）

```java
<pre name="code" class="html">package com.ycy.app;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ycy on 16/7/19.
 */
@RestController
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ImportResource({"classpath:/applicationContext.xml"})
public class Application {
  @Autowired
  private TestBiz testBiz;

  @RequestMapping("/")
  String home() throws Exception {
    System.out.println("controller 正常执行");
    testBiz.insetTes();
    
    return " 正常返回Hello World!";
  }
  
  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
  }
}
```



Biz层（外层）

```java
<pre name="code" class="html">package com.ycy.app;

import com.ycy.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ycy on 16/7/20.
 */
@Component
public class TestBiz {
  @Autowired
  private TestService testService;

  @Transactional
  public void insetTes() {
    for (int j = 0; j < 8; j++) {
      testService.testInsert(j, j + "姓名");
    }
    System.out.println("biz层 正常执行");
  }
}
```



Service层  （内层）

```java
<pre name="code" class="html"><pre name="code" class="html"><pre name="code" class="html">package com.ycy.service.impl;

import com.ycy.center.dao.entity.YcyTable;
import com.ycy.center.dao.mapper.YcyTableMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ycy on 16/7/19.
 */
@Service
public class TestServiceImpl implements com.ycy.service.TestService {
    @Autowired
   private YcyTableMapper ycyTableMapper;
    @Transactional
    public void  testInsert(int num,String name) {
    
            YcyTable ycyTable=new YcyTable();
            ycyTable.setName(name);
            ycyTable.setNum(num);
            ycyTableMapper.insert(ycyTable);
        System.out.println(num+"service正常执行");

    }
}
```







## 2、外部起事物，内部起事物，内外都无Try Catch

**外部异常：**

代码展示，修改外层Biz层代码如下

```java
<pre name="code" class="html"><pre name="code" class="html">@Component
public class TestBiz {
  @Autowired
  private TestService testService;

  @Transactional
  public void insetTes() {
    for (int j = 0; j < 8; j++) {
      testService.testInsert(j, j + "姓名");
      if (j == 3) {
        int i = 1 / 0;// 此处会产生异常
      }
    }
    System.out.println("biz层 正常执行");
  }
}
```



打印执行结果：0-3service正常执行                   数据库结果：全部数据回滚



外部异常总结: 内外都无try Catch的时候，外部异常，全部回滚。

**内部异常：**

代码展示，修改service层代码

```java
package com.ycy.service.impl;

import com.ycy.center.dao.entity.YcyTable;
import com.ycy.center.dao.mapper.YcyTableMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ycy on 16/7/19.
 */
@Service
public class TestServiceImpl implements com.ycy.service.TestService {
  @Autowired
  private YcyTableMapper ycyTableMapper;

  @Transactional
  public void testInsert(int num, String name) {

    YcyTable ycyTable = new YcyTable();
    ycyTable.setName(name);
    ycyTable.setNum(num);
    ycyTableMapper.insert(ycyTable);
    if (num == 3) {
      int i = 1 / 0;// 此处会产生异常
    }
    System.out.println(num + "service正常执行");

  }
}
```

打印执行结果：0-3service正常执行                   数据库结果：全部数据回滚



内部异常总结： 内外都无try Catch的时候，内部异常，全部回滚。



## 3、外部起事物，内部起事物，外部有Try Catch

**外部异常：**

代码展示，修改biz层代码



```java
@Component
public class TestBiz {
  @Autowired
  private TestService testService;
  @Transactional
  public void insetTes() {
    try {
      for (int j = 0; j < 8; j++) {
        testService.testInsert(j, j + "姓名");
                            
        if (j == 3) {
          int i = 1 / 0;// 此处会产生异常
        }
      }
    } catch (Exception ex) {
      System.out.println("异常日志处理");
    }
    System.out.println("biz层 正常执行");
  }
}
```

打印结果：0-3执行正常数据库结果：4条数据



外部异常总结：外部有try Catch时候，外部异常，不能回滚（事物错误）

**内部异常：**

代码展示，修改service层代码：



```java
@Service
public class TestServiceImpl implements com.ycy.service.TestService {
  @Autowired
  private YcyTableMapper ycyTableMapper;

  @Transactional
  public void testInsert(int num, String name) {

    YcyTable ycyTable = new YcyTable();
    ycyTable.setName(name);
    ycyTable.setNum(num);
    ycyTableMapper.insert(ycyTable);
    if (num == 3) {
      int i = 1 / 0;// 此处会产生异常
    }
    System.out.println(num + "service正常执行");
  }
}
```

打印结果：0-2打印正常   数据库结果：无数据，全部数据回滚


内部异常总结：外部有try Catch时候，内部异常，全部回滚

## 4、外部起事物，内部起事物，内部有Try Catch

**外部异常:**

代码展示，修改biz层：

```java
@Component
public class TestBiz {
  @Autowired
  private TestService testService;

  @Transactional
  public void insetTes() {
    for (int j = 0; j < 8; j++) {
      testService.testInsert(j, j + "姓名");
      if (j == 3) {
        int i = 1 / 0;// 此处会产生异常
      }
    }
    System.out.println("biz层 正常执行");
  }
}
```

打印结果：0-3service打印正常   数据库结果：无数据，全部数据回滚


外部异常总结： 内部有try Catch，外部异常，全部回滚

**内部异常：**

修改service层代码：

```java
@Service
public class TestServiceImpl implements com.ycy.service.TestService {
  @Autowired
  private YcyTableMapper ycyTableMapper;

  @Transactional
  public void testInsert(int num, String name) {
    try {
      YcyTable ycyTable = new YcyTable();
      ycyTable.setName(name);
      ycyTable.setNum(num);
      ycyTableMapper.insert(ycyTable);
      if (num == 3) {
        int i = 1 / 0;// 此处会产生异常
      }
    } catch (Exception ex) {
      System.out.println(num + "service异常日志");
    }
    System.out.println(num + "service正常执行");

  }
}
```



打印结果：0-0service打印正常   数据库结果：没有回滚



内部异常总结： 内部有try Catch，内部异常，全部不回滚(事物失败);

## 5、外部起事物，内部起事物，内外有Try Catch

**外部异常：**

代码展示，修改biz层：

```java
@Component
public class TestBiz {
  @Autowired
  private TestService testService;

  @Transactional
  public void insetTes() {
    try {
      for (int j = 0; j < 8; j++) {
        testService.testInsert(j, j + "姓名");
        if (j == 3) {
          int i = 1 / 0;// 此处会产生异常
        }
      }
    } catch (Exception ex) {
      System.out.println("biz层异常日志处理");
    }
    System.out.println("biz层 正常执行");
  }
}
```



打印结果：0-3service打印正常   数据库结果：插入三条数据，没有回滚



外部异常总结： 内外都有try Catch，外部异常，事物执行一半（事物失败）

**内部异常：**

代码展示,修改service 层代码

```java
@Service
public class TestServiceImpl implements com.ycy.service.TestService {
  @Autowired
  private YcyTableMapper ycyTableMapper;

  @Transactional
  public void testInsert(int num, String name) {
    try {
      YcyTable ycyTable = new YcyTable();
      ycyTable.setName(name);
      ycyTable.setNum(num);
      ycyTableMapper.insert(ycyTable);
      if (num == 3) {
        int i = 1 / 0;// 此处会产生异常
      }
    } catch (Exception ex) {
      System.out.println(num + "service异常日志处理");
    }
    System.out.println(num + "service正常执行");
  }
}
```

打印结果：0-7service打印正常，3异常日子好   数据库结果：插入全部，没有回滚



内部事物总结： 内外都有try Catch，内部异常，事物全部不会滚（事物失败）





# 三、嵌套事物总结

事物成功总结

1、内外都无try Catch的时候，外部异常，全部回滚。

2、内外都无try Catch的时候，内部异常，全部回滚。

3、外部有try Catch时候，内部异常，全部回滚

4、内部有try Catch，外部异常，全部回滚

5、友情提示：外层方法中调取其他接口，或者另外开启线程的操作，一定放到最后！！！(因为调取接口不能回滚，一定要最后来处理)

总结：由于上面的异常被捕获导致，很多事务回滚失败。如果一定要将捕获，请捕获后又抛出 RuntimeException（默认为异常捕获RuntimeException） 。

# 四、正确的嵌套事物实例

controller层

```java
package com.ycy.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ycy on 16/7/19.
 */
@RestController
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ImportResource({"classpath:/applicationContext.xml"})
public class Application {
  @Autowired
  private TestBiz testBiz;
 
  @RequestMapping("/")
  String home()  {
    System.out.println("controller 正常执行");
    try {
      testBiz.insetTes();
    } catch (Exception e) {
      System.out.println("controller 异常日志执行");
    }

    return " 正常返回Hello World!";
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
  }
}
```



外层biz层：



```java
package com.ycy.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ycy.service.TestService;
 
/**
 * Created by ycy on 16/7/20.
 */
@Component
public class TestBiz {
  @Autowired
  private TestService testService;
  
  @Transactional
  public void insetTes() throws Exception {
    try {
      for (int j = 0; j < 8; j++) {
        testService.testInsert(j, j + "姓名");
        if (j == 3) {
          int i = 1 / 0;// 此处会产生异常
        }
      }
    } catch (Exception ex) {
      System.out.println("biz层异常日志处理");
      throw new RuntimeException(ex);
    }
    
    System.out.println("biz层 正常执行");
  }
}
```

内层service层



```java
package com.ycy.service.impl;

import com.ycy.center.dao.entity.YcyTable;
import com.ycy.center.dao.mapper.YcyTableMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ycy on 16/7/19.
 */
@Service
public class TestServiceImpl implements com.ycy.service.TestService {
  @Autowired
  private YcyTableMapper ycyTableMapper;
  @Transactional
  public void testInsert(int num, String name) throws Exception {
    try {
      YcyTable ycyTable = new YcyTable();
      ycyTable.setName(name);
      ycyTable.setNum(num);
      ycyTableMapper.insert(ycyTable);
      if (num== 3) {
        int i = 1 / 0;// 此处会产生异常
      }
    } catch (Exception ex) {
      System.out.println(num + "service异常日志处理");
        throw new RuntimeException(ex);
    }
    System.out.println(num + "service正常执行");
  }
}
```