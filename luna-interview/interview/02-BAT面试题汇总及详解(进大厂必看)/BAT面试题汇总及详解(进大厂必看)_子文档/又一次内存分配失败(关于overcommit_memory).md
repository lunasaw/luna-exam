# 又一次内存分配失败(关于overcommit_memory)



1、问题现象和分析：
**测试时发现当系统中空闲内存还有很多时，就报内存分配失败了，所有进程都报内存分配失败：**
sshd@localhost:/var/log>free
       total    used    free   shared  buffers   cached
Mem:   12183700  8627972  3555728     0   289252   584444
-/+ buffers/cache:  7754276  4429424
Swap:      0     0     0
sshd@localhost:/var/log>free
-bash: fork: Cannot allocate memory
sshd@localhost:/var/log>cat /proc/meminfo 
-bash: fork: Cannot allocate memory

而messages日志中，也没有OOM相关的记录。最后确认原因为：/proc/sys/vm/overcommit_memory参数导致。
该环境中该参数设置为2，表示“No overcommit”，即系统中所有进程占用的虚拟内存空间不能超过上限：
cat /proc/meminfo
CommitLimit:  12061860 kB //虚拟地址空间的上限
Committed_AS:  8625360 kB //当前的使用量

而该参数应该默认是0，这种情况下，只有还有空闲的物理内存，就可以继续分配，不受虚拟地址空间的限制。
echo 0 > /proc/sys/vm/overcommit_memory
如此修正后解决。

**2、关于overcommit_memory说明：**

 取值为0，系统在为应用进程分配虚拟地址空间时，会判断当前申请的虚拟地址空间大小是否超过剩余内存大小，如果超过，则虚拟地址空间分配失败。因此，也就是如果进程本身占用的虚拟地址空间比较大或者剩余内存比较小时，fork、malloc等调用可能会失败。

 取值为1，系统在为应用进程分配虚拟地址空间时，完全不进行限制，这种情况下，避免了fork可能产生的失败，但由于malloc是先分配虚拟地址空间，而后通过异常陷入内核分配真正的物理内存，在内存不足的情况下，这相当于完全屏蔽了应用进程对系统内存状态的感知，即malloc总是能成功，一旦内存不足，会引起系统OOM杀进程，应用程序对于这种后果是无法预测的

 取值为2，则是根据系统内存状态确定了虚拟地址空间的上限，由于很多情况下，进程的虚拟地址空间占用远大小其实际占用的物理内存，这样一旦内存使用量上去以后，对于一些动态产生的进程(需要复制父进程地址空间)则很容易创建失败，如果业务过程没有过多的这种动态申请内存或者创建子进程，则影响不大，否则会产生比较大的影响

3、相应代码分析：

```
1. int __vm_enough_memory(struct mm_struct *mm, long pages, int cap_sys_admin)
2. {
3.   unsigned long free, allowed;
4. 
5.   vm_acct_memory(pages);
6. 
7.   /*
8.    \* Sometimes we want to use more memory than we have
9.    */
10.   if (sysctl_overcommit_memory == OVERCOMMIT_ALWAYS) //overcommit_memory=1，直接返回成功，不做任何限制。
11. ​    return 0;
12. 
13.   if (sysctl_overcommit_memory == OVERCOMMIT_GUESS) { //overcommit_memory=0，启发式方式，根据当前系统中空闲内存状况来决定是否可以分配内存。
14. ​    unsigned long n;
15. 
16. ​    free = global_page_state(NR_FILE_PAGES);
17. ​    free += nr_swap_pages;
18. 
19. ​    /*
20. ​     \* Any slabs which are created with the
21. ​     \* SLAB_RECLAIM_ACCOUNT flag claim to have contents
22. ​     \* which are reclaimable, under pressure. The dentry
23. ​     \* cache and most inode caches should fall into this
24. ​     */
25. ​    free += global_page_state(NR_SLAB_RECLAIMABLE); 
26. 
27. ​    /*
28. ​     \* Leave the last 3% for root
29. ​     */
30. ​    if (!cap_sys_admin)
31. ​      free -= free / 32; //root用户可以在free更少(3%)的时候，分配内存。
32. 
33. ​    if (free > pages) // pages为需要分配的内存大小，free为根据一定规则算出来的“空闲内存大小”，第一次free仅为NR_FILE_PAGES+NR_SLAB_RECLAIMABLE，由于直接或者系统中“实际空闲”内存代价比较大，所以进行分阶判断，提高效率。
34. ​      return 0;
35. 
36. ​    /*
37. ​     \* nr_free_pages() is very expensive on large systems,
38. ​     \* only call if we're about to fail.
39. ​     */
40. ​    n = nr_free_pages(); //当第一次判断不满足内存分配条件时，再进行“实际空闲”内存的获取操作。
41. 
42. ​    /*
43. ​     \* Leave reserved pages. The pages are not for anonymous pages.
44. ​     */
45. ​    if (n <= totalreserve_pages)
46. ​      goto error;
47. ​    else
48. ​      n -= totalreserve_pages;
49. 
50. ​    /*
51. ​     \* Leave the last 3% for root
52. ​     */
53. ​    if (!cap_sys_admin)
54. ​      n -= n / 32;
55. ​    free += n;
56. 
57. ​    if (free > pages)
58. ​      return 0;
59. 
60. ​    goto error;
61.   }
62. 
63.   allowed = (totalram_pages - hugetlb_total_pages()) //当overcommit_memory=2时，根据系统中虚拟地址空间的总量来进行限制。
64. ​     \* sysctl_overcommit_ratio / 100;
65.   /*
66.    \* Leave the last 3% for root
67.    */
68.   if (!cap_sys_admin)
69. ​    allowed -= allowed / 32;
70.   allowed += total_swap_pages;
71. 
72.   /* Don't let a single process grow too big:
73.    leave 3% of the size of this process for other processes */
74.   if (mm)
75. ​    allowed -= mm->total_vm / 32;
76. 
77.   if (percpu_counter_read_positive(&vm_committed_as) < allowed)
78. ​    return 0;
79. error:
80.   vm_unacct_memory(pages);
81. 
82.   return -ENOMEM;
83. }
```