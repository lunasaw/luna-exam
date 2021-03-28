# 深入理解JVM（6） : Java对象内存分配策略



Java技术体系中所提倡的自动内存管理最终可以归结为自动化地解决了两个问题：给对象分配内存以及回收分配给对象的内存。
 对象的内存分配，往大方向讲，就是在堆上分配，对象主要分配在新生代的Eden区上，如果启动了本地线程分配缓冲，将按线程优先在TLAB上分配。少数情况下也可能会直接分配在老年代中，分配的规则并不是百分之百固定的，其细节取决于当前使用的是哪一种垃圾收集器组合，还有虚拟机中与内存相关的参数的设置。

本文中的内存分配策略指的是Serial / Serial Old收集器下（ParNew / Serial Old收集器组合的规则也基本一致）的内存分配和回收的策略。

#### 1. **对象优先在Eden分配**

------

大多数情况下，对象在**新生代Eden区**中分配。当Eden区没有足够空间进行分配时，虚拟机将发起一次Minor GC。

- **代码示例：**



```java
private static final int _1MB = 1024 * 1024;  
/**  
 * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 
  */  
public static void testAllocation() {  
     byte[] allocation1, allocation2, allocation3, allocation4;  
     allocation1 = new byte[2 * _1MB];  
     allocation2 = new byte[2 * _1MB];  
     allocation3 = new byte[2 * _1MB];  
     allocation4 = new byte[4 * _1MB];  // 出现一次Minor GC  
 } 
```

运行结果：



```java
[GC [DefNew: 6651K->148K(9216K), 0.0070106 secs] 6651K->6292K(19456K), 0.0070426 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]   
Heap  
 def new generation   total 9216K, used 4326K [0x029d0000, 0x033d0000, 0x033d0000)  
  eden space 8192K,  51% used [0x029d0000, 0x02de4828, 0x031d0000)  
  from space 1024K,  14% used [0x032d0000, 0x032f5370, 0x033d0000)  
  to   space 1024K,   0% used [0x031d0000, 0x031d0000, 0x032d0000)  
 tenured generation   total 10240K, used 6144K [0x033d0000, 0x03dd0000, 0x03dd0000)  
   the space 10240K,  60% used [0x033d0000, 0x039d0030, 0x039d0200, 0x03dd0000)  
 compacting perm gen  total 12288K, used 2114K [0x03dd0000, 0x049d0000, 0x07dd0000)  
   the space 12288K,  17% used [0x03dd0000, 0x03fe0998, 0x03fe0a00, 0x049d0000)  
No shared spaces configured.  
```

代码的`testAllocation()`方法中，尝试分配3个2MB大小和1个4MB大小的对象，在运行时通过-Xms20M、 -Xmx20M、 -Xmn10M这3个参数限制了Java堆大小为20MB，不可扩展，其中10MB分配给新生代，剩下的10MB分配给老年代。`-XX:SurvivorRatio=8`决定了新生代中Eden区与一个Survivor区的空间比例是8∶1，从输出的结果也可以清晰地看到`eden space 8192K、from space 1024K、to space 1024K`的信息，新生代总可用空间为9216KB（Eden区+1个Survivor区的总容量）。

执行`testAllocation()`中分配allocation4对象的语句时会发生一次Minor GC，这次GC的结果是新生代6651KB变为148KB，而总内存占用量则几乎没有减少（因为allocation1、allocation2、allocation3三个对象都是存活的，虚拟机几乎没有找到可回收的对象）。这次GC发生的原因是给allocation4分配内存的时候，发现Eden已经被占用了6MB，剩余空间已不足以分配allocation4所需的4MB内存，因此发生Minor GC。GC期间虚拟机又发现已有的3个2MB大小的对象全部无法放入Survivor空间（Survivor空间只有1MB大小），所以只好通过分配担保机制提前转移到老年代去。

这次GC结束后，4MB的allocation4对象顺利分配在Eden中，因此程序执行完的结果是Eden占用4MB（被allocation4占用），Survivor空闲，老年代被占用6MB（被allocation1、allocation2、allocation3占用）。通过GC日志可以证实这一点。

#### 2. **大对象直接进入老年代**

------

所谓的大对象是指，需要**大量连续内存空间**的Java对象，最典型的大对象就是那种很长的字符串以及数组。

大对象对虚拟机的内存分配来说就是一个坏消息（比遇到一个大对象更加坏的消息就是遇到一群“朝生夕灭”的“短命大对象”，写程序的时候应当避免），经常出现大对象容易导致内存还有不少空间时就提前触发垃圾收集以获取足够的连续空间来“安置”它们。

虚拟机提供了一个`-XX:PretenureSizeThreshold`参数，令大于这个设置值的对象直接在老年代分配。这样做的目的是避免在Eden区及两个Survivor区之间发生大量的内存复制（复习一下：新生代采用复制算法收集内存）。

- **代码示例：**



```java
private static final int _1MB = 1024 * 1024;   
/**  
 * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 
 * -XX:PretenureSizeThreshold=3145728 
 */  
public static void testPretenureSizeThreshold() {  
  byte[] allocation;  
  allocation = new byte[4 * _1MB];  //直接分配在老年代中  
} 
```

运行结果：



```java
1.Heap  
2.def new generation   total 9216K, used 671K [0x029d0000, 0x033d0000, 0x033d0000)  
3.eden space 8192K,   8% used [0x029d0000, 0x02a77e98, 0x031d0000)  
4.from space 1024K,   0% used [0x031d0000, 0x031d0000, 0x032d0000)  
5.to   space 1024K,   0% used [0x032d0000, 0x032d0000, 0x033d0000)  
6.tenured generation   total 10240K, used 4096K [0x033d0000, 0x03dd0000, 0x03dd0000)  
7.the space 10240K,  40% used [0x033d0000, 0x037d0010, 0x037d0200, 0x03dd0000)  
8.compacting perm gen  total 12288K, used 2107K [0x03dd0000, 0x049d0000, 0x07dd0000)  
9.the space 12288K,  17% used [0x03dd0000, 0x03fdefd0, 0x03fdf000, 0x049d0000)  
10.No shared spaces configured. 
```

执行代码中的`testPretenureSizeThreshold()`方法后，我们看到Eden空间几乎没有被使用，而老年代的10MB空间被使用了40%，也就是4MB的allocation对象直接就分配在老年代中，这是因为`PretenureSizeThreshold`被设置为3MB（就是3145728，这个参数不能像-Xmx之类的参数一样直接写3MB），因此超过3MB的对象都会直接在老年代进行分配。

注意:`PretenureSizeThreshold`参数只对Serial和ParNew两款收集器有效，Parallel Scavenge收集器不认识这个参数，Parallel Scavenge收集器一般并不需要设置。
 如果遇到必须使用此参数的场合，可以考虑ParNew加CMS的收集器组合。

#### 3. **长期存活的对象将进入老年代**

------

为了在内存回收时能识别哪些对象应放在新生代，哪些对象应放在老年代中。虚拟机给每个对象定义了一个对象年龄（Age）计数器。

- **对象年龄的判定：**
   如果对象在Eden出生并经过第一次Minor GC后仍然存活，并且能被Survivor容纳的话，将被移动到Survivor空间中，并且对象年龄设为1。
   对象在Survivor区中每“熬过”一次Minor GC，年龄就增加1岁，当它的年龄增加到一定程度（默认为15岁），就将会被晋升到老年代中。
   对象晋升老年代的年龄阈值，可以通过参数`-XX:MaxTenuringThreshold`设置。
- **代码示例**：



```java
private static final int _1MB = 1024 * 1024;  
/**  
 * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 
 * -XX:+PrintTenuringDistribution  
 */  
@SuppressWarnings("unused")  
public static void testTenuringThreshold() {  
  byte[] allocation1, allocation2, allocation3;  
  allocation1 = new byte[_1MB / 4];    
   // 什么时候进入老年代取决于XX:MaxTenuringThreshold设置  
  allocation2 = new byte[4 * _1MB];  
  allocation3 = new byte[4 * _1MB];  
  allocation3 = null;  
  allocation3 = new byte[4 * _1MB];  
} 
```

以MaxTenuringThreshold=1参数来运行的结果：``



```java
1.[GC [DefNew  
2.Desired Survivor size 524288 bytes, new threshold 1 (max 1)  
3.- age   1:     414664 bytes,     414664 total  
4.: 4859K->404K(9216K), 0.0065012 secs] 4859K->4500K(19456K), 0.0065283 secs] [Times: user=0.02 sys=0.00, real=0.02 secs]   
5.[GC [DefNew  
6.Desired Survivor size 524288 bytes, new threshold 1 (max 1)  
7.: 4500K->0K(9216K), 0.0009253 secs] 8596K->4500K(19456K), 0.0009458 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]   
8.Heap  
9.def new generation   total 9216K, used 4178K [0x029d0000, 0x033d0000, 0x033d0000)  
10.eden space 8192K,  51% used [0x029d0000, 0x02de4828, 0x031d0000)  
11.from space 1024K,   0% used [0x031d0000, 0x031d0000, 0x032d0000)  
12.to   space 1024K,   0% used [0x032d0000, 0x032d0000, 0x033d0000)  
13.to   space 1024K,   0% used [0x032d0000, 0x032d0000, 0x033d0000)  
14.to   space 1024K,   0% used [0x032d0000, 0x032d0000, 0x033d0000)  
15.to   space 1024K,   0% used [0x032d0000, 0x032d0000, 0x033d0000)  
16.tenured generation   total 10240K, used 4500K [0x033d0000, 0x03dd0000, 0x03dd0000)  
17.the space 10240K,  43% used [0x033d0000, 0x03835348, 0x03835400, 0x03dd0000)  
18.compacting perm gen  total 12288K, used 2114K [0x03dd0000, 0x049d0000, 0x07dd0000)  
19.the space 12288K,  17% used [0x03dd0000, 0x03fe0998, 0x03fe0a00, 0x049d0000)  
20.No shared spaces configured. 
```

以MaxTenuringThreshold=15参数来运行的结果：



```java
1.[GC [DefNew  
2.Desired Survivor size 524288 bytes, new threshold 15 (max 15)  
3.- age   1:     414664 bytes,     414664 total  
4.: 4859K->404K(9216K), 0.0049637 secs] 4859K->4500K(19456K), 0.0049932 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]   
5.[GC [DefNew  
6.Desired Survivor size 524288 bytes, new threshold 15 (max 15)  
7.- age   2:     414520 bytes,     414520 total  
8.: 4500K->404K(9216K), 0.0008091 secs] 8596K->4500K(19456K), 0.0008305 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]   
9.Heap  
10.def new generation   total 9216K, used 4582K [0x029d0000, 0x033d0000, 0x033d0000)  
11.eden space 8192K,  51% used [0x029d0000, 0x02de4828, 0x031d0000)  
12.from space 1024K,  39% used [0x031d0000, 0x03235338, 0x032d0000)  
13.to   space 1024K,   0% used [0x032d0000, 0x032d0000, 0x033d0000)  
14.tenured generation   total 10240K, used 4096K [0x033d0000, 0x03dd0000, 0x03dd0000)  
15.the space 10240K,  40% used [0x033d0000, 0x037d0010, 0x037d0200, 0x03dd0000)  
16.compacting perm gen  total 12288K, used 2114K [0x03dd0000, 0x049d0000, 0x07dd0000)  
17.the space 12288K,  17% used [0x03dd0000, 0x03fe0998, 0x03fe0a00, 0x049d0000)  
18.No shared spaces configured.  
```

分别以`-XX:MaxTenuringThreshold=1`和`-XX:MaxTenuringThreshold=15`两种设置来执行代码清单3-7中的`testTenuringThreshold()`方法，此方法中的allocation1对象需要256KB内存，Survivor空间可以容纳。当MaxTenuringThreshold=1时，allocation1对象在第二次GC发生时进入老年代，新生代已使用的内存GC后非常干净地变成0KB。而MaxTenuringThreshold=15时，第二次GC发生后，allocation1对象则还留在新生代Survivor空间，这时新生代仍然有404KB被占用。

#### 4. **动态对象年龄判定**

------

为了能更好地适应不同程序的内存状况，虚拟机并不是永远地要求对象的年龄必须达到了MaxTenuringThreshold才能晋升老年代.

- **动态对象年龄判定**
   如果在Survivor空间中相同年龄所有对象大小的总和大于Survivor空间的一半，年龄大于或等于该年龄的对象就可以直接进入老年代，无须等到`MaxTenuringThreshold`中要求的年龄。
- **代码示例**



```java
private static final int _1MB = 1024 * 1024;  
/**  
 * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 
 * -XX:+PrintTenuringDistribution  
 */  
@SuppressWarnings("unused")  
public static void testTenuringThreshold2() { 
  byte[] allocation1, allocation2, allocation3, allocation4;  
  allocation1 = new byte[_1MB / 4];   
    // allocation1+allocation2大于survivo空间一半  
  allocation2 = new byte[_1MB / 4];    
  allocation3 = new byte[4 * _1MB];  
  allocation4 = new byte[4 * _1MB];  
  allocation4 = null;  
  allocation4 = new byte[4 * _1MB];  
} 
```



```java
1.[GC [DefNew  
2.Desired Survivor size 524288 bytes, new threshold 1 (max 15)  
3.- age   1:     676824 bytes,     676824 total  
4.: 5115K->660K(9216K), 0.0050136 secs] 5115K->4756K(19456K), 0.0050443 secs] [Times: user=0.00 sys=0.01, real=0.01 secs]   
5.[GC [DefNew  
6.Desired Survivor size 524288 bytes, new threshold 15 (max 15)  
7.: 4756K->0K(9216K), 0.0010571 secs] 8852K->4756K(19456K), 0.0011009 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]   
8.Heap  
9.def new generation   total 9216K, used 4178K [0x029d0000, 0x033d0000, 0x033d0000)  
10.eden space 8192K,  51% used [0x029d0000, 0x02de4828, 0x031d0000)  
11.from space 1024K,   0% used [0x031d0000, 0x031d0000, 0x032d0000)  
12.to   space 1024K,   0% used [0x032d0000, 0x032d0000, 0x033d0000)  
13.tenured generation   total 10240K, used 4756K [0x033d0000, 0x03dd0000, 0x03dd0000)  
14.the space 10240K,  46% used [0x033d0000, 0x038753e8, 0x03875400, 0x03dd0000)  
15.compacting perm gen  total 12288K, used 2114K [0x03dd0000, 0x049d0000, 0x07dd0000)  
16.the space 12288K,  17% used [0x03dd0000, 0x03fe09a0, 0x03fe0a00, 0x049d0000)  
17.No shared spaces configured. 
```

执行代码中的`testTenuringThreshold2()`方法，并设置`-XX:MaxTenuringThreshold=15`，会发现运行结果中Survivor的空间占用仍然为0%，而老年代比预期增加了6%，也就是说，allocation1、allocation2对象都直接进入了老年代，而没有等到15岁的临界年龄。因为这两个对象加起来已经到达了512KB，并且它们是同年的，满足同年对象达到Survivor空间的一半规则。我们只要注释掉其中一个对象new操作，就会发现另外一个就不会晋升到老年代中去了。

#### 5. **空间分配担保**

------

在发生Minor GC之前，虚拟机会先检查老年代最大可用的连续空间是否大于新生代所有对象总空间，如果这个条件成立，那么Minor GC可以确保是安全的。如果不成立，则虚拟机会查看HandlePromotionFailure设置值是否允许担保失败。如果允许，那么会继续检查老年代最大可用的连续空间是否大于历次晋升到老年代对象的平均大小，如果大于，将尝试着进行一次Minor GC，尽管这次Minor GC是有风险的；如果小于，或者HandlePromotionFailure设置不允许冒险，那这时也要改为进行一次Full GC。

下面解释一下“冒险”是冒了什么风险，前面提到过，新生代使用复制收集算法，但为了内存利用率，只使用其中一个Survivor空间来作为轮换备份，因此当出现大量对象在Minor GC后仍然存活的情况（最极端的情况就是内存回收后新生代中所有对象都存活），就需要老年代进行分配担保，把Survivor无法容纳的对象直接进入老年代。与生活中的贷款担保类似，老年代要进行这样的担保，前提是老年代本身还有容纳这些对象的剩余空间，一共有多少对象会活下来在实际完成内存回收之前是无法明确知道的，所以只好取之前每一次回收晋升到老年代对象容量的平均大小值作为经验值，与老年代的剩余空间进行比较，决定是否进行Full GC来让老年代腾出更多空间。

取平均值进行比较其实仍然是一种动态概率的手段，也就是说，如果某次Minor GC存活后的对象突增，远远高于平均值的话，依然会导致担保失败（Handle Promotion Failure）。如果出现了HandlePromotionFailure失败，那就只好在失败后重新发起一次Full GC。虽然担保失败时绕的圈子是最大的，但大部分情况下都还是会将HandlePromotionFailure开关打开，避免Full GC过于频繁.

