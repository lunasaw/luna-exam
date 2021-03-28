# Java并发编程的类、接口和方法



1：线程池

  与每次需要时都创建线程相比，线程池可以降低创建线程的开销，这也是因为线程池在线程执行结束后进行的是回收操作，而不是真正的

 销毁线程。

2：ReentrantLock

  ReentrantLock提供了tryLock方法，tryLock调用的时候，如果锁被其他线程持有，那么tryLock会立即返回，返回结果为false，如果锁没有被

其他线程持有，那么当前调用线程会持有锁，并且tryLock返回的结果是true,

 lock.lock();

 try {

   //do something 

 } finally {

   lock.unlock();

  }

3：volatile

   保证了同一个变量在多线程中的可见性，所以它更多是用于修饰作为开关状态的变量，因为volatile保证了只有一份主存中的数据。

4：Atomics

​    public class Count {

​      private AtomicInteger counter = new AtomicInteger();

​      public int increase() {

​        return counter.incrementAndGet();

​      }

​     public int decrease() {

​        return counter.decrementAndGet();

​     }

   }

 AtomicInteger内部通过JNI的方式使用了硬件支持的CAS指令。

5：CountDownLatch

   它是java.util.concurrent包中的一个类，它主要提供的机制是当多个(具体数量等于初始化CountDown时的count参数的值)线程都到达了预期状态

或完成预期工作时触发事件，其他线程可以等待这个事件来出发自己后续的工作，等待的线程可以是多个，即CountDownLatch是可以唤醒多个等待

的线程的，到达自己预期状态的线程会调用CountDownLatch的countDown方法，而等待的线程会调用CountDownLatch的await方法

6：CyclicBarrier

  循环屏障，CyclicBarrier可以协同多个线程，让多个线程在这个屏障前等待，直到所有线程都到达了这个屏障时，再一起继续执行后面的动作。

  CyclicBarrier和CountDownLatch都是用于多个线程间的协调的，二者的一个很大的差别是，CountDownLatch是在多个线程都进行了latch.countDown

后才会触发事件，唤醒await在latch上的线程，而执行countDown的线程，执行完countDown后，会继续自己线程的工作；

  CyclicBarrier是一个栅栏，用于同步所有调用await方法的线程，并且等所有线程都到了await方法，这些线程才一起返回继续各自的工作，因为使用CyclicBarrier的线程都会阻塞在await方法上，所以在线程池中使用CyclicBarrier时要特别小心，如果线程池的线程 数过少，那么就会发生死锁了,

CyclicBarrier可以循环使用，CountDownLatch不能循环使用。

7：Semaphore

  是用于管理信号量的，构造的时候传入可供管理的信号量的数值，信号量对量管理的信号就像令牌，构造时传入个数，总数就是控制并发的数量。

  semaphore.acquire();

  try {

​    //调用远程通信的方法

  } finally () {

​    semaphore.release();

  }

8：Exchanger

  Exchanger，从名字上讲就是交换，它用于在两个线程之间进行数据交换，线程会阻塞在Exchanger的exchange方法上，直到另一个线程也到了

同一个Exchanger的exchange方法时，二者进行交换，然后两个线程会继续执行自身相关的代码。

 

9：Future和FutureTask

  Future<HashMap> future = getDataFromRemote2();

  //do something

  HashMap data = (HashMap)future.get();

 

 private Future<HashMap> getDateFromRemote2() {

   return threadPool.submit(new Callable<HashMap>() {

​      public HashMap call() {

​          return getDataFromRemote();

​      }

   });

 }

思路：调用函数后马上返回，然后继续向下执行，急需要数据时再来用，或者说再来等待这个数据，具体实现方式有两种，一个是用Future，另一个

使用回调。