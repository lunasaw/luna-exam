# 基于synchronized的对象锁，类锁以及死锁模拟



分为对象锁和类锁

```java
public class T {
	public void test1() throws Exception{
     synchronized(this){ //对象锁
    	 System.out.println(Thread.currentThread().getName()+"---test1  Doing");
    	 Thread.currentThread().sleep(2000);
    	 test2();
     };
	}
    
	public synchronized void test2() throws Exception{ //同test1方法
	    System.out.println(Thread.currentThread().getName()+"--- test2 Doing");
	    Thread.currentThread().sleep(2000);
	    test1();
	}

	public void testclass1() throws Exception{
	     synchronized(T.class){//类锁
	    	 System.out.println(Thread.currentThread().getName()+"---testclass1  Doing");
	    	 Thread.currentThread().sleep(2000);
	     };
	}

	public static synchronized void testclass2() throws Exception{ //同testclass1方法
    	 System.out.println(Thread.currentThread().getName()+"---testclass2  Doing");
    	 Thread.currentThread().sleep(2000);
    	 testclass3();
	}

	public static synchronized void testclass3() throws Exception{ //同testclass1方法
   	  System.out.println(Thread.currentThread().getName()+"---testclass3  Doing");
   	  Thread.currentThread().sleep(2000);
   	  testclass2();
	}
}
```



不同对象 访问类锁测试：

```java
public class clent {

	public static void main(String[] args) throws Exception {
		final CountDownLatch c=new CountDownLatch(1);
		new Thread(new Runnable(){
			@Override
			public void run() {
				T t1=new T();
				try {
					System.out.println(Thread.currentThread().getName()+"启动");
					c.await();
					t1.testclass3();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	   new Thread(new Runnable(){
					@Override
					public void run() {
						T t1=new T();
						try {											
                           System.out.println(Thread.currentThread().getName()+"启动");
							c.await();
							t1.testclass2();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
	   c.countDown();
	   /* 可以发现 类的synchronized一个线程获得不释放，其他线程就不能访问 通过Jconsole可以看到Thread0 一直在等待Thread1
	   Thread-1启动
	   Thread-1---testclass2  Doing
	   Thread-0启动
	   Thread-1---testclass3  Doing
	   Thread-1---testclass2  Doing
	   Thread-1---testclass2  Doing
	   Thread-1---testclass3  Doing*/
	}
```



不同对象访问对象锁测试：

```java
public class clent1 {
	public static void main(String[] args) {
		final CountDownLatch c=new CountDownLatch(1);
		new Thread(new Runnable(){
			@Override
			public void run() {
				T t1=new T();
				try {
					System.out.println(Thread.currentThread().getName()+"启动");
					c.await();
					t1.test1();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	   new Thread(new Runnable(){
					@Override
					public void run() {
						T t1=new T();
						try {
							System.out.println(Thread.currentThread().getName()+"启动");
							c.await();
							t1.test2();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
	   c.countDown();
	  /* Thread-0启动
	   Thread-0---test1  Doing
	   Thread-1启动
	   Thread-1--- test2 Doing
      */
	}
```



同一个对象，多个线程访问对象锁测试：

```java
public class client2 {
	public static void main(String[] args) {
		final CountDownLatch c=new CountDownLatch(1);
		final T t1=new T();
		new Thread(new Runnable(){
			@Override
			public void run() {
				
				try {
					System.out.println(Thread.currentThread().getName()+"启动");
					c.await();
					t1.test1();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	   new Thread(new Runnable(){
					@Override
					public void run() {		


						try {
							System.out.println(Thread.currentThread().getName()+"启动");
							c.await();
							t1.test2();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
	   c.countDown();
	}

	/*结果 同一个对象，当其中一个线程没有释放锁时，另一个会一直等待。
	Thread-1启动
	Thread-1--- test2 Doing
	Thread-0启动
    Thread-1---test1  Doing
	Thread-1--- test2 Doing
	Thread-1---test1  Doing
    Thread-1--- test2 Doing*/

}
```



以上演示的是阻塞等待，下面演示死锁。

```java
public class E {
	public static synchronized void testclass() throws Exception{ 
   	 System.out.println(Thread.currentThread().getName()+"---E.method  Doing");
   	 Thread.currentThread().sleep(2000);
   	 E1.testclass();
	}
}
```



```java
public class E1 {
	public static synchronized void testclass() throws Exception{ 
	   	 System.out.println(Thread.currentThread().getName()+"---E1.method  Doing");
	   	Thread.currentThread().sleep(1000);
	   	 E.testclass();
		}
}
public class SiSuo {


	public static void main(String[] args) {
		final CountDownLatch c=new CountDownLatch(1);
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					c.await();
					E.testclass();
					E1.testclass();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();

		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					c.await();
                    E1.testclass();
					E.testclass();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        
        }).start();
		c.countDown();
	}
	/*执行结果，一直等待
	Thread-1---E1.method  Doing
	Thread-0---E.method  Doing*/
    
}
```


