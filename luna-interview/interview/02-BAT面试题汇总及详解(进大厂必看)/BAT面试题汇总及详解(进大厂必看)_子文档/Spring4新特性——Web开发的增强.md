# Spring4新特性——Web开发的增强



从Spring4开始，Spring以Servlet3为进行开发，如果用Spring MVC 测试框架的话需要指定Servlet3兼容的jar包（因为其Mock的对象都是基于Servlet3的）。另外为了方便Rest开发，通过新的@RestController指定在控制器上，这样就不需要在每个@RequestMapping方法上加 `@ResponseBody了。而且添加了一个``AsyncRestTemplate` ，支持REST客户端的异步无阻塞支持。



```
@RestController
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping("/test")
      public User view() {
        User user = new User();
        user.setId(1L);
        user.setName("haha");
        return user;
    }

    @RequestMapping("/test2")
    public String view2() {
        return "{\"id\" : 1}";
    }
}
```

 其实现就是在@@RestController中加入@ResponseBody：

```
@org.springframework.stereotype.Controller
@org.springframework.web.bind.annotation.ResponseBody
public @interface RestController {
}
```

这样当你开发Rest服务器端的时候，spring-mvc配置文件需要的代码极少，可能就仅需如下一行：

```
    <context:component-scan base-package="com.sishuok.spring4"/>
    <mvc:annotation-driven/>
```

 

**2、mvc:annotation-driven配置变化**

统一风格；将 enableMatrixVariables改为enable-matrix-variables属性；将ignoreDefaultModelOnRedirect改为ignore-default-model-on-redirect。

 

**3、提供AsyncRestTemplate用于客户端非阻塞异步支持。**

**3.1、服务器端**

对于服务器端的springmvc开发可以参考https://github.com/zhangkaitao/servlet3-showcase中的[chapter3-springmvc](https://github.com/zhangkaitao/servlet3-showcase/tree/master/chapter3-springmvc)

```
@RestController
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping("/api")
      public Callable<User> api() {
        System.out.println("=====hello");
        return new Callable<User>() {
            @Override
            public User call() throws Exception {
                Thread.sleep(10L * 1000); //暂停两秒
                User user = new User();
                user.setId(1L);
                user.setName("haha");
                return user;
            }
        };
    }
}
```

非常简单，服务器端暂停10秒再返回结果（但是服务器也是非阻塞的）。具体参考我github上的代码。

 

**3.2、客户端**

```
    public static void main(String[] args) {
        AsyncRestTemplate template = new AsyncRestTemplate();
        //调用完后立即返回（没有阻塞）
        ListenableFuture<ResponseEntity<User>> future = template.getForEntity("http://localhost:9080/spring4/api", User.class);
        //设置异步回调
        future.addCallback(new ListenableFutureCallback<ResponseEntity<User>>() {
            @Override
            public void onSuccess(ResponseEntity<User> result) {
                System.out.println("======client get result : " + result.getBody());
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("======client failure : " + t);
            }
        });
        System.out.println("==no wait");
    }
```

 此处使用Future来完成非阻塞，这样的话我们也需要给它一个回调接口来拿结果； Future和Callable是一对，一个消费结果，一个产生结果。调用完模板后会立即返回，不会阻塞；有结果时会调用其回调。



AsyncRestTemplate默认使用SimpleClientHttpRequestFactory，即通过java.net.HttpURLConnection实现；另外我们也可以使用apache的http components；使用template.setAsyncRequestFactory(new HttpComponentsAsyncClientHttpRequestFactory());设置即可。

 

另外在开发时尽量不要自己注册如：

```
<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"/>
<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
```

尽量使用

```
<mvc:annotation-driven/> 
```

它设计的已经足够好，使用子元素可以配置我们需要的配置。

 

且不要使用老版本的：

```
<bean class="org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping"/>
<bean class="org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter">
```

否则可能得到如下异常：

写道

Circular view path [login]: would dispatch back to the current handler URL [/spring4/login] again. Check your ViewResolver setup! (Hint: This may be the result of an unspecified view, due to default view name generation.)

 