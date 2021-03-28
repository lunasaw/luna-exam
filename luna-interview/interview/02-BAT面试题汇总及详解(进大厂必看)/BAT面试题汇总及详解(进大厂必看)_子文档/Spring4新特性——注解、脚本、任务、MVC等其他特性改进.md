## 一、注解方面的改进

spring4对注解API和ApplicationContext获取注解Bean做了一点改进。

获取注解的注解，如@Service是被@Compent注解的注解，可以通过如下方式获取@Componet注解实例：

```
Annotation service = AnnotationUtils.findAnnotation(ABService.class, org.springframework.stereotype.Service.class);
Annotation component = AnnotationUtils.getAnnotation(service, org.springframework.stereotype.Component.class);
```

 

获取重复注解：

比如在使用hibernate validation时，我们想在一个方法上加相同的注解多个，需要使用如下方式：

```
@Length.List(
        value = {
                @Length(min = 1, max = 2, groups = A.class),
                @Length(min = 3, max = 4, groups = B.class)
        }
)
public void test() {
```

可以通过如下方式获取@Length：

```
Method method = ClassUtils.getMethod(AnnotationUtilsTest.class, "test");
Set<Length> set = AnnotationUtils.getRepeatableAnnotation(method, Length.List.class, Length.class);
```

 

当然，如果你使用Java8，那么本身就支持重复注解，比如spring的任务调度注解，

```
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(Schedules.class)
public @interface Scheduled { 
```



```
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Schedules {

	Scheduled[] value();

}
```

 

这样的话，我们可以直接同时注解相同的多个注解：

```
@Scheduled(cron = "123")
@Scheduled(cron = "234")
public void test   
```

但是获取的时候还是需要使用如下方式：

```
AnnotationUtils.getRepeatableAnnotation(ClassUtils.getMethod(TimeTest.class, "test"), Schedules.class, Scheduled.class)
```

 

ApplicationContext和BeanFactory提供了直接通过注解获取Bean的方法：

```
    @Test
    public void test() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(GenericConfig.class);
        ctx.refresh();

        Map<String, Object> beans = ctx.getBeansWithAnnotation(org.springframework.stereotype.Service.class);
        System.out.println(beans);
    }
```

这样可以实现一些特殊功能。

 

另外和提供了一个AnnotatedElementUtils用于简化java.lang.reflect.AnnotatedElement的操作，具体请参考其javadoc。  

 

## 二、脚本的支持 

spring4也提供了类似于javax.script的简单封装，用于支持一些脚本语言，核心接口是：

```
public interface ScriptEvaluator {
	Object evaluate(ScriptSource script) throws ScriptCompilationException;
	Object evaluate(ScriptSource script, Map<String, Object> arguments) throws ScriptCompilationException;
}
```

 

比如我们使用groovy脚本的话，可以这样：

```
    @Test
    public void test() throws ExecutionException, InterruptedException {
        ScriptEvaluator scriptEvaluator = new GroovyScriptEvaluator();

        //ResourceScriptSource 外部的
        ScriptSource source = new StaticScriptSource("i+j");
        Map<String, Object> args = new HashMap<>();
        args.put("i", 1);
        args.put("j", 2);
        System.out.println(scriptEvaluator.evaluate(source, args));
    }
```

没什么很特别的地方。另外还提供了BeanShell（BshScriptEvaluator）和javax.script（StandardScriptEvaluator）的简单封装。

 

## 三、Future增强

提供了一个ListenableFuture，其是jdk的Future的封装，用来支持回调（成功/失败），其借鉴了com.google.common.util.concurrent.ListenableFuture。

```
    @Test
    public void test() throws Exception {
        ListenableFutureTask<String> task = new ListenableFutureTask<String>(new Callable() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(10 * 1000L);
                System.out.println("=======task execute");
                return "hello";
            }
        });

        task.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("===success callback 1");
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        task.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("===success callback 2");
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(task);
        String result = task.get();
        System.out.println(result);

    }
```

可以通过addCallback添加一些回调，当执行成功/失败时会自动调用。

 

## 四、MvcUriComponentsBuilder

MvcUriComponentsBuilder类似于ServletUriComponentsBuilder，但是可以直接从控制器获取URI信息，如下所示：

假设我们的控制器是：

```
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/{id}")
    public String view(@PathVariable("id") Long id) {
        return "view";
    }

    @RequestMapping("/{id}")
    public A getUser(@PathVariable("id") Long id) {
        return new A();
    }

}
```

注：如果在真实mvc环境，存在两个@RequestMapping("/{id}")是错误的。当前只是为了测试。

 

我们可以通过如下方式得到

```
    //需要静态导入 import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;
    @Test
    public void test() {
        MockHttpServletRequest req = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));

        //MvcUriComponentsBuilder类似于ServletUriComponentsBuilder，但是直接从控制器获取
        //类级别的
        System.out.println(
                fromController(UserController.class).build().toString()
        );

        //方法级别的
        System.out.println(
                fromMethodName(UserController.class, "view", 1L).build().toString()
        );

        //通过Mock方法调用得到
        System.out.println(
                fromMethodCall(on(UserController.class).getUser(2L)).build()
        );
    }
}
```

注意：当前MvcUriComponentsBuilder实现有问题，只有JDK环境支持，大家可以复制一份，然后修改：

method.getParameterCount() （Java 8才支持）

到

method.getParameterTypes().length

 

## 五、Socket支持

提供了获取Socket TCP/UDP可用端口的工具，如

SocketUtils.findAvailableTcpPort()

SocketUtils.findAvailableTcpPort(min, max) 

SocketUtils.findAvailableUdpPort()

非常简单，就不用特别说明了。

