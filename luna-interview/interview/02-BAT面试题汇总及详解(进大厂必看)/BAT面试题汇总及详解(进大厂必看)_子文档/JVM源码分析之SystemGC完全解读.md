# JVM源码分析之SystemGC完全解读



## 概述

JVM的GC一般情况下是JVM本身根据一定的条件触发的，不过我们还是可以做一些人为的触发，比如通过jvmti做强制GC，通过System.gc触发，还可以通过jmap来触发等，针对每个场景其实我们都可以写篇文章来做一个介绍，本文重点介绍下System.gc的原理

或许大家已经知道如下相关的知识

- system.gc其实是做一次full gc
- system.gc会暂停整个进程
- system.gc一般情况下我们要禁掉，使用-XX:+DisableExplicitGC
- system.gc在cms gc下我们通过-XX:+ExplicitGCInvokesConcurrent来做一次稍微高效点的GC(效果比Full GC要好些)
- system.gc最常见的场景是RMI/NIO下的堆外内存分配等

如果你已经知道上面这些了其实也说明你对System.gc有过一定的了解，至少踩过一些坑，但是你是否更深层次地了解过它，比如

- 为什么CMS GC下-XX:+ExplicitGCInvokesConcurrent这个参数加了之后会比真正的Full GC好？
- 它如何做到暂停整个进程？
- 堆外内存分配为什么有时候要配合System.gc？

如果你上面这些疑惑也都知道，那说明你很懂System.gc了，那么接下来的文字你可以不用看啦

## JDK里的System.gc的实现

先贴段代码吧（java.lang.System）



```dart
    /**
     * Runs the garbage collector.
     * <p>
     * Calling the <code>gc</code> method suggests that the Java Virtual
     * Machine expend effort toward recycling unused objects in order to
     * make the memory they currently occupy available for quick reuse.
     * When control returns from the method call, the Java Virtual
     * Machine has made a best effort to reclaim space from all discarded
     * objects.
     * <p>
     * The call <code>System.gc()</code> is effectively equivalent to the
     * call:
     * <blockquote><pre>
     * Runtime.getRuntime().gc()
     * </pre></blockquote>
     *
     * @see     java.lang.Runtime#gc()
     */
    public static void gc() {
        Runtime.getRuntime().gc();
    }
```

发现主要调用的是Runtime里的gc方法（java.lang.Runtime）



```dart
    /**
     * Runs the garbage collector.
     * Calling this method suggests that the Java virtual machine expend
     * effort toward recycling unused objects in order to make the memory
     * they currently occupy available for quick reuse. When control
     * returns from the method call, the virtual machine has made
     * its best effort to recycle all discarded objects.
     * <p>
     * The name <code>gc</code> stands for "garbage
     * collector". The virtual machine performs this recycling
     * process automatically as needed, in a separate thread, even if the
     * <code>gc</code> method is not invoked explicitly.
     * <p>
     * The method {@link System#gc()} is the conventional and convenient
     * means of invoking this method.
     */
    public native void gc();
```

这里看到gc方法是native的，在java层面只能到此结束了，代码只有这么多，要了解更多，可以看方法上面的注释，不过我们需要更深层次地来了解其实现，那还是准备好进入到jvm里去看看

## Hotspot里System.gc的实现

### 如何找到native里的实现

上面提到了Runtime.gc是一个本地方法，那需要先在jvm里找到对应的实现，这里稍微提一下jvm里native方法最常见的也是最简单的查找，jdk里一般含有native方法的类，一般都会有一个对应的c文件，比如上面的java.lang.Runtime这个类，会有一个Runtime.c的文件和它对应，native方法的具体实现都在里面了，如果你有source，可能会猜到和下面的方法对应



```cpp
JNIEXPORT void JNICALL
Java_java_lang_Runtime_gc(JNIEnv *env, jobject this)
{
    JVM_GC();
}
```

其实没错的，就是这个方法，jvm要查找到这个native方法其实很简单的，看方法名可能也猜到规则了，Java_pkgName_className_methodName，其中pkgName里的"."替换成"_"，这样就能找到了，当然规则不仅仅只有这么一个，还有其他的，这里不细说了，有机会写篇文章详细介绍下其中细节

### DisableExplicitGC参数

上面的方法里是调用JVM_GC()，实现如下



```css
JVM_ENTRY_NO_ENV(void, JVM_GC(void))
  JVMWrapper("JVM_GC");
  if (!DisableExplicitGC) {
    Universe::heap()->collect(GCCause::_java_lang_system_gc);
  }
JVM_END
```

看到这里我们已经解释其中一个疑惑了，就是`DisableExplicitGC`这个参数是在哪里生效的，起的什么作用，如果这个参数设置为true的话，那么将直接跳过下面的逻辑，我们通过-XX:+ DisableExplicitGC就是将这个属性设置为true，而这个属性默认情况下是true还是false呢



```cpp
product(bool, DisableExplicitGC, false,                                   \
          "Tells whether calling System.gc() does a full GC")    
```

### ExplicitGCInvokesConcurrent参数

这里主要针对CMSGC下来做分析，所以我们上面看到调用了heap的collect方法，我们找到对应的逻辑



```cpp
void GenCollectedHeap::collect(GCCause::Cause cause) {
  if (should_do_concurrent_full_gc(cause)) {
#ifndef SERIALGC
    // mostly concurrent full collection
    collect_mostly_concurrent(cause);
#else  // SERIALGC
    ShouldNotReachHere();
#endif // SERIALGC
  } else {
#ifdef ASSERT
    if (cause == GCCause::_scavenge_alot) {
      // minor collection only
      collect(cause, 0);
    } else {
      // Stop-the-world full collection
      collect(cause, n_gens() - 1);
    }
#else
    // Stop-the-world full collection
    collect(cause, n_gens() - 1);
#endif
  }
}

bool GenCollectedHeap::should_do_concurrent_full_gc(GCCause::Cause cause) {
  return UseConcMarkSweepGC &&
         ((cause == GCCause::_gc_locker && GCLockerInvokesConcurrent) ||
          (cause == GCCause::_java_lang_system_gc && ExplicitGCInvokesConcurrent));
}
```

collect里一开头就有个判断，如果should_do_concurrent_full_gc返回true，那会执行collect_mostly_concurrent做并行的回收

其中should_do_concurrent_full_gc中的逻辑是如果使用CMS GC，并且是system gc且ExplicitGCInvokesConcurrent==true，那就做并行full gc，当我们设置-XX:+ ExplicitGCInvokesConcurrent的时候，就意味着应该做并行Full GC了，不过要注意千万不要设置-XX:+DisableExplicitGC，不然走不到这个逻辑里来了

## 并行Full GC相对正常的Full GC效率高在哪里

### stop the world

说到GC，这里要先提到VMThread，在jvm里有这么一个线程不断轮询它的队列，这个队列里主要是存一些VM_operation的动作，比如最常见的就是内存分配失败要求做GC操作的请求等，在对gc这些操作执行的时候会先将其他业务线程都进入到安全点，也就是这些线程从此不再执行任何字节码指令，只有当出了安全点的时候才让他们继续执行原来的指令，因此这其实就是我们说的stop the world(STW)，整个进程相当于静止了

### CMS GC

这里必须提到CMS GC，因为这是解释并行Full GC和正常Full GC的关键所在，CMS GC我们分为两种模式background和foreground，其中background顾名思义是在后台做的，也就是可以不影响正常的业务线程跑，触发条件比如说old的内存占比超过多少的时候就可能触发一次background式的cms gc，这个过程会经历CMS GC的所有阶段，该暂停的暂停，该并行的并行，效率相对来说还比较高，毕竟有和业务线程并行的gc阶段；而foreground则不然，它发生的场景比如业务线程请求分配内存，但是内存不够了，于是可能触发一次cms gc，这个过程就必须是要等内存分配到了线程才能继续往下面走的，因此整个过程必须是STW的，因此CMS GC整个过程都是暂停应用的，但是为了提高效率，它并不是每个阶段都会走的，只走其中一些阶段，这些省下来的阶段主要是并行阶段，Precleaning、AbortablePreclean，Resizing这几个阶段都不会经历，其中sweep阶段是同步的，但不管怎么说如果走了类似foreground的cms gc，那么整个过程业务线程都是不可用的，效率会影响挺大。CMS GC具体的过程后面再写文章详细说，其过程确实非常复杂的

### 正常的Full GC

正常的Full GC其实是整个gc过程包括ygc和cms gc(这里说的是真正意义上的Full GC，还有些场景虽然调用Full GC的接口，但是并不会都做，有些时候只做ygc，有些时候只做cms gc)都是由VMThread来执行的，因此整个时间是ygc+cms gc的时间之和，其中CMS GC是上面提到的foreground式的，因此整个过程会比较长，也是我们要避免的

### 并行的Full GC

并行Full GC也通样会做YGC和CMS GC，但是效率高就搞在CMS GC是走的background的，整个暂停的过程主要是YGC+CMS_initMark+CMS_remark几个阶段

## 堆外内存常配合使用System GC

这里说的堆外内存主要针对java.nio.DirectByteBuffer，这些对象的创建过程会通过Unsafe接口直接通过os::malloc来分配内存，然后将内存的起始地址和大小存到java.nio.DirectByteBuffer对象里，这样就可以直接操作这些内存。这些内存只有在DirectByteBuffer回收掉之后才有机会被回收，因此如果这些对象大部分都移到了old，但是一直没有触发CMS GC或者Full GC，那么悲剧将会发生，因为你的物理内存被他们耗尽了，因此为了避免这种悲剧的发生，通过-XX:MaxDirectMemorySize来指定最大的堆外内存大小，当使用达到了阈值的时候将调用System.gc来做一次full gc，以此来回收掉没有被使用的堆外内存，具体堆外内存是如何回收的，其原理机制又是怎样的，还是后面写篇详细的文章吧