# 并发工具类（二）同步屏障CyclicBarrier



### 简介

CyclicBarrier 的字面意思是可循环使用（Cyclic）的屏障（Barrier）。它要做的事情是，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活。CyclicBarrier默认的构造方法是CyclicBarrier(int parties)，其参数表示屏障拦截的线程数量，每个线程调用await方法告诉CyclicBarrier我已经到达了屏障，然后当前线程被阻塞。



实例代码如下：

```
public class CyclicBarrierTest {
	static CyclicBarrier c = new CyclicBarrier(2);
  
	public static void main(String[] args) {
	new Thread(new Runnable() {
        
	@Override
	public void run() {
		try {
			c.await();
        } catch (Exception e) {

		}
		System.out.println(1);
		}
		}).start();

		try {
			c.await();
		} catch (Exception e) { 
		}
		System.out.println(2);
	}
}
输出
    2
    1
或输出
    1
    2
```

如果把new CyclicBarrier(2)修改成new CyclicBarrier(3)则主线程和子线程会永远等待，因为没有第三个线程执行await方法，即没有第三个线程到达屏障，所以之前到达屏障的两个线程都不会继续执行。

CyclicBarrier还提供一个更高级的构造函数CyclicBarrier(int parties,  Runnable barrierAction)，用于在线程到达屏障时，优先执行barrierAction，方便处理更复杂的业务场景。代码如下：

```
public class CyclicBarrierTest2 {

	static CyclicBarrier c = new CyclicBarrier(2, new A());
	public static void main(String[] args) {
		new Thread(new Runnable() {
		@Override
        public void run() {
            try {
                c.await();
            } catch (Exception e) {
            }
            System.out.println(1);
        }
        }).start();
        try {
            c.await();
        } catch (Exception e) {
        }
        System.out.println(2);
    }
	static class A implements Runnable {

		@Override
		public void run() {
			System.out.println(3);

		}
	}
}

输出

3
1
2
```

### CyclicBarrier的应用场景

CyclicBarrier可以用于多线程计算数据，最后合并计算结果的应用场景。比如我们用一个Excel保存了用户所有银行流水，每个Sheet保存一个帐户近一年的每笔银行流水，现在需要统计用户的日均银行流水，先用多线程处理每个sheet里的银行流水，都执行完之后，得到每个sheet的日均银行流水，最后，再用barrierAction用这些线程的计算结果，计算出整个Excel的日均银行流水。

### CyclicBarrier和CountDownLatch的区别

- CountDownLatch的计数器只能使用一次。而CyclicBarrier的计数器可以使用reset() 方法重置。所以CyclicBarrier能处理更为复杂的业务场景，比如如果计算发生错误，可以重置计数器，并让线程们重新执行一次。
- CyclicBarrier还提供其他有用的方法，比如getNumberWaiting方法可以获得CyclicBarrier阻塞的线程数量。isBroken方法用来知道阻塞的线程是否被中断。比如以下代码执行完之后会返回true。

isBroken的使用代码如下：

```
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest3 {

    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
            Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
	                try {
                   		 c.await();
	                } catch (Exception e) {
	                }
	         }       
		});     
		thread.start();
		thread.interrupt();
		try {
			c.await();
		} catch (Exception e) {
			System.out.println(c.isBroken());
		}
	}	
}	

输出
true
```

