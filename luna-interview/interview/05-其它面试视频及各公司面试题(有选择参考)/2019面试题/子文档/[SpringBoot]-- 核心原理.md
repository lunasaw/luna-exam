# 【SpringBoot】-- 核心原理



springboot是服务于spring框架的框架，可以帮助使用Spring的开发者快速构建Spring框架，它基于了约定由于配置的理念，服务的范围是简化配置文件；

# 1.约定由于配置： 

约定优于配置，是一种软件设计范式，目的在于减少开发人员需要做决定的数量，使开发变得简单，但不失灵活

## 体现：

- maven的目录结构
  - 默认以jar的方式打包
  - 默认会有resource文件夹
  - 默认提供application.properties/yml文件
- main方法运行就会启动web工程，启动时创建一个内置的tomcat容器，将当前项目部署在此容器中
- 可以通过Spring,profiles.active属性来决定运行不同环境读取的配置文件
- maven里面引用了spring-boot-starter-web
  - 会自动添加spring mvc工程所需要的所有东西



# 2. 重点需要了解的内容

对spring 已有的东西进行封装然后创建出来的新东西：

1. AutoConfiguration 自动装配
2. Starter
3. Actuator
4. SpringBoot CLI



# 3.启动类上的复合注解: @SpringBootApplication

本质上复合了@EnableAutoConfiguration,@ComponentScan,@Configuration

1. EnableAutoC onfiguration
   EnableAutoConfiguration 的 主要作用其实就是帮助springboot 应用把所有符合条件的@Configuration 配置都自动加载到当前 SpringBoot 创建并使用的 IOC 容器中
2. ComponentScan
   扫描@Component/@Reponsitory/@Service/@Controller 携带了上面注解的类都会被扫描到IOC容器内托管
3. Configuration
   任何一个标注了@Configuration 的 Java 类定义都是一个JavaConfig 配置类。而在这个配置类中，任何标注了@Bean 的方法，它的返回值都会作为 Bean 定义注册到Spring 的 IOC 容器，方法名默认成为这个 bean 的 id；



# 4. Spring Boot 自动配置原理是什么？

1. 在复合注解SpringBootapplication中，包含EnableAutoConfiguration启动spring应用程序上下文的自动配置，EnableAutoConfiguration内会导入一个AutoConfigurationImportSelector类详细流程是这样：
   （1）SpringApplication.run(AppConfig.class,args);执行流程，中有refreshContext(context);
   （2）refreshContext(context);内部会解析我们的配置类上的标签.实现自动装配功能的注解@EnableAutoConfiguration
   （3）会解析@EnableAutoConfiguration这个注解里面的@Import引入的配置类.AutoConfigurationImportSelector
2. 这个类会去读取spring.factories下key为EnableAutoConfiguration对应的全限定名的值；
3. spring.factories里配置的所有key-value,是要告诉springBoot这个stareter所需要加载的XXXAutoConfiguraion类，也就是我们想要自动注入的bean;

![aHR0cHM6Ly9jZG4ubmxhcmsuY29tL3l1cXVlLzAvMjAxOS9wbmcvMTk4NTM2LzE1NjQxMjk3NDMyNTUtOTM4NDJlZWUtNGE3OC00YWVmLTgxMTUtYTkwNzcyYzNhMTc5LnBuZw]([SpringBoot]-- 核心原理.assets/aHR0cHM6Ly9jZG4ubmxhcmsuY29tL3l1cXVlLzAvMjAxOS9wbmcvMTk4NTM2LzE1NjQxMjk3NDMyNTUtOTM4NDJlZWUtNGE3OC00YWVmLTgxMTUtYTkwNzcyYzNhMTc5LnBuZw.png)

下的getCandidateConfigurations()

```java
    protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        // 核心代码 
        // 调用了方法：spring.core中的loadFactoryNames()
        // 参数1--this.getSpringFactoriesLoaderFactoryClass()
        // 参数2--this.getBeanClassLoader() 
        List<String> configurations = SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.getBeanClassLoader());
        Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you are using a custom packaging, make sure that file is correct.");
        return configurations;
    }

```

- 参数1–this.getSpringFactoriesLoaderFactoryClass()

```java
    protected Class<?> getSpringFactoriesLoaderFactoryClass() {
        return EnableAutoConfiguration.class;
    }

```

- 参数2–this.getBeanClassLoader()

```java
    protected ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

```

- org.springframework.core.io.support.SpringFactoriesLoader类下的loadFactoryNames()

```java
public static List<String> loadFactoryNames(Class<?> factoryClass, @Nullable ClassLoader classLoader) {
        String factoryClassName = factoryClass.getName();
        return (List)loadSpringFactories(classLoader).getOrDefault(factoryClassName, Collections.emptyList());
    }

    private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
        MultiValueMap<String, String> result = (MultiValueMap)cache.get(classLoader);
        if (result != null) {
            return result;
        } else {
            try {
                // 核心代码看这里
                Enumeration<URL> urls = classLoader != null ? classLoader.getResources("META-INF/spring.factories") : ClassLoader.getSystemResources("META-INF/spring.factories");
                LinkedMultiValueMap result = new LinkedMultiValueMap();

                while(urls.hasMoreElements()) {
                    URL url = (URL)urls.nextElement();
                    UrlResource resource = new UrlResource(url);
                    Properties properties = PropertiesLoaderUtils.loadProperties(resource);
                    Iterator var6 = properties.entrySet().iterator();

                    while(var6.hasNext()) {
                        Entry<?, ?> entry = (Entry)var6.next();
                        String factoryClassName = ((String)entry.getKey()).trim();
                        String[] var9 = StringUtils.commaDelimitedListToStringArray((String)entry.getValue());
                        int var10 = var9.length;

                        for(int var11 = 0; var11 < var10; ++var11) {
                            String factoryName = var9[var11];
                            result.add(factoryClassName, factoryName.trim());
                        }
                    }
                }

                cache.put(classLoader, result);
                return result;
            } catch (IOException var13) {
                throw new IllegalArgumentException("Unable to load factories from location [META-INF/spring.factories]", var13);
            }
        }
    }


```

# 5. SPI扩展点机制的实现和运用

**Spring的SPI是什么？**
SPI的全名为Service Provider Interface，为某个接口寻找服务实现的机制,也叫扩展点机制，主要是为了提升扩展性而存在；
当服务的提供者，提供了服务接口的一种实现之后，在jar包的META-INF/services/目录里同时创建一个以服务接口命名的文件。该文件里就是实现该服务接口的具体实现类。而当外部程序装配这个模块的时候，就能通过该jar包META-INF/services/里的配置文件找到具体的实现类名，并装载实例化，完成模块的注入。通过这个约定，就不需要把服务放在代码中了，通过模块被装配的时候就可以发现服务类了
**场景：**
当SpringBoot里面提供了默认实现不满足我们的要求时候，我们可以对其进行扩展添加自己的实现
**过程：**
在resource/META-INFO下添加spring.fatories文件，配置相应的key，value
key：类的全路径（固定的 ）
value对应着所有的实现