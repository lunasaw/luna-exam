# Linux系统swappiness参数在内存与交换分区之间优化作用



swappiness的值的大小对如何使用swap分区是有着很大的联系的。swappiness=0的时候表示最大限度使用物理内存，然后才是 swap空间，swappiness＝100的时候表示积极的使用swap分区，并且把内存上的数据及时的搬运到swap空间里面。linux的基本默认设置为60，具体如下：

 

 

一般默认值都是60  

 

 

\```

[root@timeserver ~]# cat /proc/sys/vm/swappiness
60

\```

 

 

也就是说，你的内存在使用到100-60=40%的时候，就开始出现有交换分区的使用。大家知道，内存的速度会比磁盘快很多，这样子会加大系统io，同时造的成大量页的换进换出，严重影响系统的性能，所以我们在操作系统层面，要尽可能使用内存，对该参数进行调整。

 

 

临时调整的方法如下，我们调成10：
[root@timeserver ~]# sysctl vm.swappiness=10
vm.swappiness = 10
[root@timeserver ~]# cat /proc/sys/vm/swappiness
10
这只是临时调整的方法，重启后会回到默认设置的

要想永久调整的话，需要将
需要在/etc/sysctl.conf修改，加上：
[root@timeserver ~]# cat /etc/sysctl.conf

\# Controls the maximum number of shared memory segments, in pages
kernel.shmall = 4294967296
vm.swappiness=10

 

 

激活设置

[root@timeserver ~]# sysctl -p

 

在linux中，可以通过修改swappiness内核参数，降低系统对swap的使用，从而提高系统的性能。


遇到的问题是这样的，新版本产品发布后，每小时对内存的使用会有一个尖峰。虽然这个峰值还远没有到达服务器的物理内存，但确发现内存使用达到峰值时系统开始使用swap。在swap的过程中系统性能会有所下降，表现为较大的服务延迟。对这种情况，可以通过调节swappiness内核参数降低系统对swap的使用，从而避免不必要的swap对性能造成的影响。


简单地说这个参数定义了系统对swap的使用倾向，默认值为60，值越大表示越倾向于使用swap。可以设为0，这样做并不会禁止对swap的使用，只是最大限度地降低了使用swap的可能性。


通过sysctl -q vm.swappiness可以查看参数的当前设置。


修改参数的方法是修改/etc/sysctl.conf文件，加入vm.swappiness=xxx，并重起系统。这个操作相当于是修改虚拟系统中的/proc/sys/vm/swappiness文件，将值改为XXX数值。


如果不想重起，可以通过sysctl -p动态加载/etc/sysctl.conf文件，但建议这样做之前先清空swap。