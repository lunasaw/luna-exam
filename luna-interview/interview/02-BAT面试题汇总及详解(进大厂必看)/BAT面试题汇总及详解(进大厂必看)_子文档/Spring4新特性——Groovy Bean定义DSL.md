# Spring4新特性——Groovy Bean定义DSL



Spring4支持使用Groovy DSL来进行Bean定义配置，其类似于XML，不过因为是Groovy DSL，可以实现任何复杂的语法配置，但是对于配置，我们需要那么复杂吗？本着学习的态度试用了下其Groovy DSL定义Bean，其主要缺点：

1、DSL语法规则不足，需要其后续维护；

2、编辑器的代码补全需要跟进，否则没有代码补全，写这个很痛苦；

3、出错提示不友好，排错难；

4、当前对于一些配置还是需要XML的支持，所以还不是100%的纯Groovy DSL；

5、目前对整个Spring生态支持还是不够的，比如Web，需要观望。

其优点就是其本质是Groovy脚本，所以可以做非常复杂的配置，如果以上问题能够解决，其也是一个不错的选择。在Groovy中的话使用这种配置感觉不会有什么问题，但是在纯Java开发环境下也是有它，给我的感觉是这个功能其目的是去推广它的groovy。比较怀疑它的动机。

## 一、对比

对于我来说，没有哪个好/坏，只有适用不适用；开发方便不方便。接下来我们来看一下各种类型的配置吧：

**XML风格配置**





```
    <context:component-scan base-package="com.sishuok.spring4"/>
    <bean class="org.springframework.validation.beanvalidation.MethodValidationPostProcessor">
        <property name="validator" ref="validator"/>
    </bean>
    <mvc:annotation-driven validator="validator"/>

    <bean id="validator" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean">
        <property name="providerClass" value="org.hibernate.validator.HibernateValidator"/>
        <property name="validationMessageSource" ref="messageSource"/>
    </bean>
```

 

**注解风格配置** 

```
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.sishuok.spring4")
public class MvcConfiguration extends WebMvcConfigurationSupport {
    @Override
    protected Validator getValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean =
                new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        return localValidatorFactoryBean;
    }
}
```

 

**Groovy DSL风格配置**

```
import org.hibernate.validator.HibernateValidator
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

beans {
    xmlns context: "http://www.springframework.org/schema/context"
    xmlns mvc: "http://www.springframework.org/schema/mvc"

    context.'component-scan'('base-package': "com,sishuok.spring4")
    mvc.'annotation-driven'('validator': "validator")

    validator(LocalValidatorFactoryBean) {
        providerClass = HibernateValidator.class
        validationMessageSource = ref("messageSource")
    }
}
```

因为Spring4 webmvc没有提供用于Web环境的Groovy DSL实现的WebApplicationContext，所以为了在web环境使用，单独写了一个WebGenricGroovyApplicationContext，可以到源码中查找。

 

 

可以看到，它们之前差别不是特别大；以上只提取了部分配置，完整的配置可以参考我的github：[spring4-showcase](https://github.com/zhangkaitao/spring4-showcase)

 

对于注解风格的配置，如果在Servlet3容器中使用的话，可以借助WebApplicationInitializer实现无配置：

```
public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(javax.servlet.ServletContext sc) throws ServletException {

//        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//        rootContext.register(AppConfig.class);
//        sc.addListener(new ContextLoaderListener(rootContext));

        //2、springmvc上下文
        AnnotationConfigWebApplicationContext springMvcContext = new AnnotationConfigWebApplicationContext();
        springMvcContext.register(MvcConfiguration.class);

        //3、DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(springMvcContext);
        ServletRegistration.Dynamic dynamic = sc.addServlet("dispatcherServlet", dispatcherServlet);
        dynamic.setLoadOnStartup(1);
        dynamic.addMapping("/");

        //4、CharacterEncodingFilter
        FilterRegistration filterRegistration =
                sc.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
        filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");


    }
}
```

 

到底好还是不好，需要根据自己项目大小等一些因素来衡量。对于Servlet3可以参考我github的示例：[servlet3-showcase ](https://github.com/zhangkaitao/servlet3-showcase) 

 

对于Groovy风格配置，如果语法足够丰富、Spring内部支持完善，且编辑器支持也非常好的话，也是不错的选择。

 

 

## 二、Groovy Bean定义

接下来我们来看下groovy DSL的具体使用吧：

1、安装环境

```
        <dependency>
            <groupId>org.codehaus.groovy</groupId>
            <artifactId>groovy-all</artifactId>
            <version>${groovy.version}</version>
        </dependency>
```

我使用的groovy版本是2.2.1

 

2、相关组件类

此处使用Spring Framework官网的hello world，可以前往http://projects.spring.io/spring-framework/ 主页查看 

 

3、Groovy Bean定义配置文件

```
import com.sishuok.spring4.xml.MessageServiceImpl
import com.sishuok.spring4.xml.MessagePrinter

beans {
    messageService(MessageServiceImpl) {//名字(类型) 
        message = "hello"  //注入的属性
    }

    messagePrinter(MessagePrinter, messageService) //名字（类型，构造器参数列表）

}
```

从此处可以看到 如果仅仅是简单的Bean定义，确实比XML简洁。

 

 

4、测试

如果不测试环境可以这样测试：

```
public class XmlGroovyBeanDefinitionTest1 {
    @Test
    public void test() {
        ApplicationContext ctx = new GenericGroovyApplicationContext("classpath:spring-config-xml.groovy");
        MessagePrinter messagePrinter = (MessagePrinter) ctx.getBean("messagePrinter");
        messagePrinter.printMessage();
    }
}
```

使用GenericGroovyApplicationContext加载groovy配置文件。 

 

 

如果想集成到Spring Test中，可以这样：

```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-xml.groovy", loader = GenericGroovyContextLoader.class)
public class XmlGroovyBeanDefinitionTest2 {

    @Autowired
    private MessagePrinter messagePrinter;

    @Test
    public void test() {
        messagePrinter.printMessage();
    }
}
```

此处需要定义我们自己的bean loader，即从groovy配置文件加载：

```
public class GenericGroovyContextLoader extends AbstractGenericContextLoader {

    @Override
    protected String getResourceSuffix() {
        throw new UnsupportedOperationException(
                "GenericGroovyContextLoader does not support the getResourceSuffix() method");
    }
    @Override
    protected BeanDefinitionReader createBeanDefinitionReader(GenericApplicationContext context) {
        return new GroovyBeanDefinitionReader(context);
    }
}
```

使用GroovyBeanDefinitionReader来加载groovy配置文件。 

 

到此基本的使用就结束了，还算是比较简洁，但是我们已经注意到了，在纯Java环境做测试还是比较麻烦的。 比如没有给我们写好相关的测试支撑类。另外大家可以前往Spring的github看看在groovy中的单元测试：[GroovyBeanDefinitionReaderTests.groovy](https://github.com/spring-projects/spring-framework/blob/master/spring-context/src/test/groovy/org/springframework/context/groovy/GroovyBeanDefinitionReaderTests.groovy)

 

再看一下我们使用注解方式呢：

```
@Component
public class MessageServiceImpl implements MessageService {
    @Autowired
    @Qualifier("message")
    private String message;
    ……
}
```





```
@Component
public class MessagePrinter {
    private MessageService messageService;
    @Autowired
    public MessagePrinter(MessageService messageService) {
        this.messageService = messageService;
    }
……
}
```

 

 

Groovy配置文件：

```
beans {
    xmlns context: "http://www.springframework.org/schema/context"    //导入命名空间

    context.'component-scan'('base-package': "com.sishuok.spring4") {
        'exclude-filter'('type': "aspectj", 'expression': "com.sishuok.spring4.xml.*")
    }

    message(String, "hello") {}

}
```

在该配置文件中支持导入xml命名空间， 其中context.'component-scan'部分等价于XML中的：

```
    <context:component-scan base-package="com.sishuok.spring4">
        <context:exclude-filter type="aspectj" expression="com.sishuok.spring4.xml.*"/>
    </context:component-scan>            
```

 从这里可以看出，其还没能完全从XML风格配置中走出来，不是纯Groovy DSL。 

 

测试方式和之前的一样就不重复了，可以查看[XmlGroovyBeanDefinitionTest2.java](https://github.com/zhangkaitao/spring4-showcase/blob/master/spring4-groovy/src/test/java/com/sishuok/spring4/XmlGroovyBeanDefinitionTest2.java)。

 

## 三、Groovy Bean定义 DSL语法

到目前为止，基本的helloworld就搞定了；接下来看看Groovy DSL都支持哪些配置吧：

**创建Bean**

 

构造器

```
    validator(LocalValidatorFactoryBean) { //名字（类型）
        providerClass = HibernateValidator.class  //属性=值
        validationMessageSource = ref("messageSource") //属性 = 引用，当然也支持如 validationMessageSource=messageSource 但是这种方式缺点是messageSource必须在validator之前声明
    } 
```

静态工厂方法

```
def bean = factory(StaticFactory) {
    prop = 1
}
bean.factoryMethod = "getInstance"
```

或者 

```
bean(StaticFactory) { bean ->
    bean.factoryMethod = "getInstance"
    prop = 1
}
```

 

实例工厂方法 

```
beanFactory(Factory)
bean(beanFactory : "newInstance", "args") {
    prop = 1
}
```

或者

```
beanFactory(Factory)
bean("bean"){bean ->
    bean.factoryBean="beanFactory"
    bean.factoryMethod="newInstance"
    prop = 1
}
```

 

**依赖注入**

 

属性注入

```
    beanName(BeanClass) { //名字（类型）
        str = "123" // 常量直接注入
        bean = ref("bean") //属性 = 引用 ref("bean", true) 这样的话是引用父容器的
        beans = [bean1, bean2] //数组/集合
        props = [key1:"value1", key2:"value2"] // Properties / Map
    }
```

 

构造器注入 

```
bean(Bean, "args1", "args2") 
```

 

静态工厂注入/实例工厂注入，请参考创建bean部分

 

 

**匿名内部Bean**



```
outer(OuterBean) {
   prop = 1
   inner =  { InnerBean bean ->  //匿名内部Bean
                          prop =2
            }
}
```





```
outer(OuterBean) {
   prop = 1
   inner =  { bean ->  //匿名内部Bean 通过实例工厂方法创建
                          bean.factoryBean = "innerBean"
                          bean.factoryMethod = "create"
                          prop = 2
            }
}
```

**单例/非单例/作用域**





```
singletonBean(Bean1) { bean ->
    bean.singleton = true
}
nonSingletonBean(Bean1) { bean ->
    bean.singleton = false
}
prototypeBean(Bean1) { bean ->
    bean.scope = "prototype"
}
```

 

其中bean可以理解为xml中的<bean> 标签，即bean定义。 

 

 

**父子Bean**





```
parent(Bean1){ bean -> 
    bean.'abstract' = true //抽象的
    prop = 123
}
child { bean ->
    bean.parent = parent //指定父bean
}
```

 

**命名空间**





```
    xmlns aop:"http://www.springframework.org/schema/aop"
    myAspect(MyAspect)
    aop {
        config("proxy-target-class":true) {
            aspect( id:"test",ref:"myAspect" ) {
                before method:"before", pointcut: "execution(void com.sishuok.spring4..*.*(..))"
            }
        }
    }
```

以上是AOP的，可以自己推到其他相关的配置； 

 

```
    xmlns context: "http://www.springframework.org/schema/context"   
    context.'component-scan'('base-package': "com.sishuok.spring4") {
        'exclude-filter'('type': "aspectj", 'expression': "com.sishuok.spring4.xml.*")
    }
```

以上是component-scan，之前介绍过了。 

```
xmlns aop:"http://www.springframework.org/schema/aop"
scopedList(ArrayList) { bean ->
    bean.scope = "haha"
    aop.'scoped-proxy'()  
}
```

 等价于 

```
    <bean id="scopedList" class="java.util.ArrayList" scope="haha">
        <aop:scoped-proxy/>
    </bean>
```

 

```
    xmlns util:"http://www.springframework.org/schema/util"
    util.list(id : 'list') {
        value 1
        value 2
    }
```

等价于XML： 

```
    <util:list id="list">
        <value>1</value>
        <value>2</value>
    </util:list>
```

 

```
    xmlns util:"http://www.springframework.org/schema/util"
    util.map(id : 'map') {
        entry(key : 1, value :1)
        entry('key-ref' : "messageService", 'value-ref' : "messageService")
    }
```

 等价于 

```
    <util:map id="map">
        <entry key="1" value="1"/>
        <entry key-ref="messageService" value-ref="messageService"/>
    </util:map>
```

**引入其他配置文件**

```
importBeans "classpath:org/springframework/context/groovy/test.xml"
```

当然也能引入XML的。 

 

对于DSL新的更新大家可以关注：[GroovyBeanDefinitionReaderTests.groovy](https://github.com/spring-projects/spring-framework/blob/master/spring-context/src/test/groovy/org/springframework/context/groovy/GroovyBeanDefinitionReaderTests.groovy#L47)，本文也是根据其编写的。

 

再来看看groovy bean定义的另一个好处：

我们可以直接在groovy bean定义文件中声明类，然后使用



```
@Controller
def class GroovyController {
    @RequestMapping("/groovy")
    @ResponseBody
    public String hello() {
        return "hello";
    }
}

beans {

    groovyController(GroovyController)

}
```

 

 另一种Spring很早就支持的方式是引入外部groovy文件，如：

```
xmlns lang: "http://www.springframework.org/schema/lang"
lang.'groovy'(id: 'groovyController2', 'script-source': 'classpath:com/sishuok/spring4/controller/GroovyController2.groovy')
```

使用其lang命名空间引入外部脚本文件。

 

 

到此，Groovy Bean定义DSL就介绍完了，其没有什么特别之处，只是换了种写法而已，我认为目前试试即可，还不能用到真实环境。

 