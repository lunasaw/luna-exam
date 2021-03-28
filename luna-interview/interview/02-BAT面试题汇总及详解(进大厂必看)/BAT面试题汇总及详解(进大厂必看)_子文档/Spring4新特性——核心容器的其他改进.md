# Spring4新特性——**核心容器的其他改进**



**1、Map依赖注入：**





```
@Autowired
private Map<String, BaseService> map;
```

这样会注入：key是bean名字；value就是所有实现了BaseService的Bean，假设使用上一篇的例子，则会得到：

{organizationService=com.sishuok.spring4.service.OrganizationService@617029, userService=com.sishuok.spring4.service.UserService@10ac73b}

 

**2、List/数组注入：**





```
@Autowired
private List<BaseService> list;
```

 这样会注入所有实现了BaseService的Bean；但是顺序是不确定的，如果我们想要按照某个顺序获取；在Spring4中可以使用@Order或实现Ordered接口来实现，如：





```
@Order(value = 1)
@Service
public class UserService extends BaseService<User> {
}
```

这种方式在一些需要多态的场景下是非常有用的。

 

**3、@Lazy可以延迟依赖注入：**





```
@Lazy
@Service
public class UserService extends BaseService<User> {
}
```





```
    @Lazy
    @Autowired
    private UserService userService;
```

 我们可以把@Lazy放在@Autowired之上，即依赖注入也是延迟的；当我们调用userService时才会注入。即延迟依赖注入到使用时。同样适用于@Bean。

 

**4、@Conditional**

@Conditional类似于`@Profile（一般用于如我们有开发环境、测试环境、正式机环境，为了方便切换不同的环境可以使用`@Profile指定各个环境的配置，然后通过某个配置来开启某一个环境，方便切换`）`，但是@Conditional的优点是允许自己定义规则。可以指定在如@Component、@Bean、@Configuration等注解的类上，以绝对Bean是否创建等。首先来看看使用@Profile的用例，假设我们有个用户模块：



```
public abstract class UserService extends BaseService<User> {
}

@Profile("local")
@Service
public class LocalUserService extends UserService {
}

@Profile("remote")
@Service
public class RemoteUserService extends UserService {
}
```

我们在写测试用例时，可以指定我们使用哪个Profile：





```
@ActiveProfiles("remote")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =  "classpath:spring-config.xml")
public class ServiceTest {

    @Autowired
    private UserService userService;
}
```

 这种方式非常简单。如果想自定义如@Profile之类的注解等，那么@Conditional就派上用场了；假设我们系统中有好多本地/远程接口，那么我们定义两个注解@Local和@Remote注解要比使用@Profile方便的多；如：

 





```
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(CustomCondition.class)
public @interface Local {
}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(CustomCondition.class)
public @interface Remote {
}
```

 





```
public class CustomCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean isLocalBean = metadata.isAnnotated("com.sishuok.spring4.annotation.Local");
        boolean isRemoteBean = metadata.isAnnotated("com.sishuok.spring4.annotation.Remote");
        //如果bean没有注解@Local或@Remote，返回true，表示创建Bean
        if(!isLocalBean && !isRemoteBean) {
            return true;
        }

        boolean isLocalProfile = context.getEnvironment().acceptsProfiles("local");

        //如果profile=local 且 bean注解了@Local，则返回true 表示创建bean；
        if(isLocalProfile) {
            return isLocalBean;
        }

        //否则默认返回注解了@Remote或没有注解@Remote的Bean
        return isRemoteBean;
    }
}
```

 

 然后我们使用这两个注解分别注解我们的Service：





```
@Local
@Service
public class LocalUserService extends UserService {
}
```

 





```
@Remote
@Service
public class RemoteUserService extends UserService {
}
```

 

首先在@Local和@Remote注解上使用@Conditional(CustomCondition.class)指定条件，然后使用@Local和@Remote注解我们的Service，这样当加载Service时，会先执行条件然后判断是否加载为Bean。@Profile就是这样实现的，其Condition是：org.springframework.context.annotation.ProfileCondition。可以去看下源码，很简单。

 

**5、基于CGLIB的类代理不再要求类必须有空参构造器了：**

这是一个很好的特性，使用构造器注入有很多好处，比如可以只在创建Bean时注入依赖，然后就不变了，如果使用setter注入，是允许别人改的。当然我们可以使用spring的字段级别注入。如果大家使用过如Shiro，我们可能要对Controller加代理。如果是类级别代理，此时要求Controller必须有空参构造器，有时候挺烦人的。spring如何实现的呢？其内联了[objenesis](http://objenesis.org/)类库，通过它来实现，可以去其官网看看介绍。这样就支持如下方式的构造器注入了：

 





```
@Controller
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
```

 

org.springframework.cglib.proxy.Enhancer在其github和maven仓库中的source中竟然木有，其github：https://github.com/spring-projects/spring-framework/tree/master/spring-core/src/main/java/org/springframework/cglib；难道忘了吗？