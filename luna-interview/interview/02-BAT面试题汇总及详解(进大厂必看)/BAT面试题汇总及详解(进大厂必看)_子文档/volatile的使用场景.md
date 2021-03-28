## volatile的适用场景

### 模式 #1：状态标志 

也许实现 volatile 变量的规范使用仅仅是使用一个布尔状态标志，用于指示发生了一个重要的一次性事件，例如完成初始化或请求停机。

```
1. volatile boolean shutdownRequested; 
2.  
3. ... 
4.  
5. public void shutdown() {  
6.   shutdownRequested = true;  
7. } 
8.  
9. public void doWork() {  
10.   while (!shutdownRequested) {  
11.     // do stuff 
12.   } 
13. } 
```

线程1执行doWork()的过程中，可能有另外的线程2调用了shutdown，所以boolean变量必须是volatile。

而如果使用 `synchronized` 块编写循环要比使用 volatile 状态标志编写麻烦很多。由于 volatile 简化了编码，并且状态标志并不依赖于程序内任何其他状态，因此此处非常适合使用 volatile。



这种类型的状态标记的一个公共特性是：**通常只有一种状态转换**；`shutdownRequested` 标志从`false` 转换为`true`，然后程序停止。这种模式可以扩展到来回转换的状态标志，但是只有在转换周期不被察觉的情况下才能扩展（从`false` 到`true`，再转换到`false`）。此外，还需要某些原子状态转换机制，例如原子变量。



### 模式 #2：一次性安全发布（one-time safe publication） 

在缺乏同步的情况下，可能会遇到某个对象引用的更新值（由另一个线程写入）和该对象状态的旧值同时存在。

这就是造成著名的双重检查锁定（double-checked-locking）问题的根源，其中对象引用在没有同步的情况下进行读操作，产生的问题是您可能会看到一个更新的引用，但是仍然会通过该引用看到不完全构造的对象。

```
1. //注意volatile！！！！！！！！！！！！！！！！！  
2. private volatile static Singleton instace;   
3.   
4. public static Singleton getInstance(){   
5.   //第一次null检查    
6.   if(instance == null){       
7.     synchronized(Singleton.class) {  //1    
8.       //第二次null检查     
9.       if(instance == null){     //2  
10.         instance = new Singleton();//3  
11.       }  
12.     }       
13.   }  
14.   return instance;     


```

如果不用volatile，则因为内存模型允许所谓的“无序写入”，可能导致失败。**——某个线程可能会获得一个未完全初始化的实例。**

考察上述代码中的 //3 行。此行代码创建了一个 Singleton 对象并初始化变量 instance 来引用此对象。这行代码的问题是：**在Singleton 构造函数体执行之前，变量instance 可能成为非 null 的！**
什么？这一说法可能让您始料未及，但事实确实如此。

在解释这个现象如何发生前，请先暂时接受这一事实，我们先来考察一下双重检查锁定是如何被破坏的。假设上述代码执行以下事件序列：

1.   线程 1 进入 getInstance() 方法。
2.   由于 instance 为 null，线程 1 在 //1 处进入synchronized 块。
3.   线程 1 前进到 //3 处，但在构造函数执行之前，使实例成为非null。
4.   线程 1 被线程 2 预占。
5.   线程 2 检查实例是否为 null。因为实例不为 null，线程 2 将instance 引用返回，返回一个构造完整但**部分初始化**了的Singleton 对象。
6.   线程 2 被线程 1 预占。
7.   线程 1 通过运行 Singleton 对象的构造函数并将引用返回给它，来完成对该对象的初始化。



### 模式 #3：独立观察（independent observation） 

安全使用 volatile 的另一种简单模式是：定期 “发布” 观察结果供程序内部使用。【例如】假设有一种环境传感器能够感觉环境温度。一个后台线程可能会每隔几秒读取一次该传感器，并更新包含当前文档的 volatile 变量。然后，其他线程可以读取这个变量，从而随时能够看到最新的温度值。

使用该模式的另一种应用程序就是收集程序的统计信息。【例】如下代码展示了身份验证机制如何记忆最近一次登录的用户的名字。将反复使用`lastUser` 引用来发布值，以供程序的其他部分使用。

```
1. public class UserManager { 
2.   public volatile String lastUser; //发布的信息 
3.  
4.   public boolean authenticate(String user, String password) { 
5.     boolean valid = passwordIsValid(user, password); 
6.     if (valid) { 
7.       User u = new User(); 
8.       activeUsers.add(u); 
9.       lastUser = user; 
10.     } 
11.     return valid; 
12.   } 
13. }  
```

### 模式 #4：“volatile bean” 模式

volatile bean 模式的基本原理是：很多框架为易变数据的持有者（例如 `HttpSession`）提供了容器，但是放入这些容器中的对象必须是线程安全的。

在 volatile bean 模式中，JavaBean 的所有数据成员都是 volatile 类型的，并且 getter 和 setter 方法必须非常普通——即不包含约束！

```
1. @ThreadSafe 
2. public class Person { 
3.   private volatile String firstName; 
4.   private volatile String lastName; 
5.   private volatile int age; 
6.  
7.   public String getFirstName() { return firstName; } 
8.   public String getLastName() { return lastName; } 
9.   public int getAge() { return age; } 
10.  
11.   public void setFirstName(String firstName) {  
12.     this.firstName = firstName; 
13.   } 
14.  
15.   public void setLastName(String lastName) {  
16.     this.lastName = lastName; 
17.   } 
18.  
19.   public void setAge(int age) {  
20.     this.age = age; 
21.   } 
22. } 
```

### 模式 #5：开销较低的“读－写锁”策略

如果读操作远远超过写操作，您可以结合使用**内部锁**和 **volatile 变量**来减少公共代码路径的开销。

如下显示的线程安全的计数器，使用 `synchronized` 确保增量操作是原子的，并使用 `volatile` 保证当前结果的可见性。如果更新不频繁的话，该方法可实现更好的性能，因为读路径的开销仅仅涉及 volatile 读操作，这通常要优于一个无竞争的锁获取的开销。

```
1. @ThreadSafe 
2. public class CheesyCounter { 
3.   // Employs the cheap read-write lock trick 
4.   // All mutative operations MUST be done with the 'this' lock held 
5.   @GuardedBy("this") private volatile int value; 
6.  
7.   //读操作，没有synchronized，提高性能 
8.   public int getValue() {  
9.     return value;  
10.   }  
11.  
12.   //写操作，必须synchronized。因为x++不是原子操作 
13.   public synchronized int increment() { 
14.     return value++; 
15.   } 
```

使用锁进行所有变化的操作，使用 volatile 进行只读操作。 
其中，锁一次只允许一个线程访问值，volatile 允许多个线程执行读操作 