# springMVC一个Controller处理所有用户请求的并发问题



有状态和无状态的对象基本概念： 
有状态对象(Stateful Bean)，就是有实例变量的对象 ，可以保存数据，是非线程安全的。一般是prototype scope。
无状态对象(Stateless Bean)，就是没有实例变量的对象，不能保存数据，是不变类，是线程安全的。一般是singleton scope。

如Struts2中的Action，假如内部有实例变量User，当调用新增用户方法时，user是用来保存数据，那么此action是有状态对象。多个线程同时访问此action时 会造成user变量的不一致。所以action的scope要设计成prototype，或者，User类放到threadLocal里来保持多个线程不会造成User变量的乱串（此种场景没必要放到threadLocal内）。

而Service内部一般只有dao实例变量 如userDao, 因为userDao是无状态的对象（内部无实例变量且不能保存数据），所以service也是无状态的对象。

***\*public  class\**** XxxAction{

　　 // 由于多线程环境下，user是引用对象，是非线程安全的 

　　***\*public\**** User user;

　　......

}

***\*public  class\**** XxxService {  

　　// 虽然有billDao属性，但billDao是没有状态信息的，是Stateless Bean.  

　　BillDao billDao;  

　　......

}

对于那些会以多线程运行的单例类

局部变量不会受多线程影响，
成员变量会受到多线程影响。

多个线程调用同一个对象的同一个方法： 
如果方法里无局部变量，那么不受任何影响；
如果方法里有局部变量，只有读操作，不受影响；存在写操作，考虑多线程影响值；

例如Web应用中的Servlet，每个方法中对局部变量的操作都是在线程自己独立的内存区域内完成的，所以是线程安全的。 
对于成员变量的操作，可以使用ThreadLocal来保证线程安全。 



springMVC中，一般Controller、service、DAO层的scope均是singleton；

每个请求都是单独的线程,即使同时访问同一个Controller对象，因为并没有修改Controller对象，相当于针对Controller对象而言，只是读操作，没有写操作，不需要做同步处理。

 

Service层、Dao层用默认singleton就行，虽然Service类也有dao这样的属性，但dao这些类都是没有状态信息的，也就是 相当于不变(immutable)类，所以不影响。

Struts2中的Action因为会有User、BizEntity这样的实例对象，是有状态信息 的，在多线程环境下是不安全的，所以Struts2默认的实现是Prototype模式。在Spring中，Struts2的Action中scope 要配成prototype作用域。

 

 

### Spring并发访问的线程安全性问题 

由于Spring MVC默认是Singleton的，所以会产生一个潜在的安全隐患。根本核心是instance变量保持状态的问题。这意味着每个request过来，系统都会用原有的instance去处理，这样导致了两个结果：
一是我们不用每次创建Controller，
二是减少了对象创建和垃圾收集的时间；
由于只有一个Controller的instance，当多个线程同时调用它的时候，它里面的instance变量就不是线程安全的了，会发生窜数据的问题。
当然大多数情况下，我们根本不需要考虑线程安全的问题，比如dao,service等，除非在bean中声明了实例变量。因此，我们在使用spring mvc 的contrller时，应避免在controller中定义实例变量。 
如：

```
publicclassControllerextendsAbstractCommandController{
......
protectedModelAndView handle(HttpServletRequest request,HttpServletResponse response,
Object command,BindException errors)throwsException{
company =................;
}
protectedCompany company;
}
```

 

在这里有声明一个变量company，这里就存在并发线程安全的问题。
如果控制器是使用单例形式，且controller中有一个私有的变量a,所有请求到同一个controller时，使用的a变量是共用的，即若是某个请求中修改了这个变量a，则，在别的请求中能够读到这个修改的内容。。

有几种解决方法：
1、在控制器中不使用实例变量
2、将控制器的作用域从单例改为原型，即在spring配置文件Controller中声明 scope="prototype"，每次都创建新的controller
3、在Controller中使用ThreadLocal变量

这几种做法有好有坏，第一种，需要开发人员拥有较高的编程水平与思想意识，在编码过程中力求避免出现这种BUG，而第二种则是容器自动的对每个请求产生一个实例，由JVM进行垃圾回收，因此做到了线程安全。
使用第一种方式的好处是实例对象只有一个，所有的请求都调用该实例对象，速度和性能上要优于第二种，不好的地方，就是需要程序员自己去控制实例变量的状态保持问题。第二种由于每次请求都创建一个实例，所以会消耗较多的内存空间。
所以在使用spring开发web 时要注意，默认Controller、Dao、Service都是单例的