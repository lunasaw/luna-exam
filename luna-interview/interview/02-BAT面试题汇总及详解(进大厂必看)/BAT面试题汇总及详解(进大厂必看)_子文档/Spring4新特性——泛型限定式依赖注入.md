# Spring4新特性——泛型限定式依赖注入

Spring 4.0已经发布RELEASE版本，不仅支持Java8，而且向下兼容到JavaSE6/JavaEE6，并移出了相关废弃类，新添加如Java8的支持、Groovy式Bean定义DSL、对核心容器进行增强、对Web框架的增强、Websocket模块的实现、测试的增强等。其中两个我一直想要的增强就是：支持泛型依赖注入、对cglib类代理不再要求必须有空参构造器了。

**1、相关代码：**

**1.1、实体**



```
public class User implements Serializable {
    private Long id;
    private String name;
}

public class Organization implements Serializable {
    private Long id;
    private String name;
}
```

 **1.2、Repository**



```
public abstract class BaseRepository<M extends Serializable> {
    public void save(M m) {
        System.out.println("=====repository save:" + m);
    }
}

@Repository
public class UserRepository extends BaseRepository<User> {
}

@Repository
public class OrganizationRepository extends BaseRepository<Organization> {
}
```

 对于Repository，我们一般是这样实现的：首先写一个模板父类，把通用的crud等代码放在BaseRepository；然后子类继承后，只需要添加额外的实现。

 

**1.3、Service**

**1.3.1、以前Service写法**

```
public abstract class BaseService<M extends Serializable> {
    private BaseRepository<M> repository;
    public void setRepository(BaseRepository<M> repository) {
        this.repository = repository;
    }
    public void save(M m) {
        repository.save(m);
    }
}
@Service
public class UserService extends BaseService<User> {
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        setRepository(userRepository);
    }
}

@Service
public class OrganizationService extends BaseService<Organization> {
    @Autowired
    public void setOrganizationRepository(OrganizationRepository organizationRepository) {
        setRepository(organizationRepository);
    }
}
```

 

可以看到，以前必须再写一个setter方法，然后指定注入的具体类型，然后进行注入；

 

**1.3.2、泛型Service的写法**



```
public abstract class BaseService<M extends Serializable> {
    @Autowired
    protected BaseRepository<M> repository;

    public void save(M m) {
        repository.save(m);
    }
}

@Service
public class UserService extends BaseService<User> {
}

@Service
public class OrganizationService extends BaseService<Organization> {
}
```

 

 大家可以看到，现在的写法非常简洁。支持泛型式依赖注入。

 这个也是我之前非常想要的一个功能，这样对于那些基本的CRUD式代码，可以简化更多的代码。

  

如果大家用过Spring data jpa的话，以后注入的话也可以使用泛型限定式依赖注入 ：

```
@Autowired
private Repository<User> userRepository;
```

 

 对于泛型依赖注入，最好使用setter注入，这样万一子类想变，比较容易切换。如果有多个实现时，子类可以使用@Qualifier指定使用哪一个。

 