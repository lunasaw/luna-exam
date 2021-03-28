# Mysql优化系列（1）--Innodb引擎下mysql自身配置优化



1.简单介绍
InnoDB给MySQL提供了具有提交，回滚和崩溃恢复能力的事务安全（ACID兼容）存储引擎。InnoDB锁定在行级并且也在SELECT语句提供一个Oracle风格一致的非锁定读。这些特色增加了多用户部署和性能。没有在InnoDB中扩大锁定的需要，因为在InnoDB中行级锁定适合非常小的空间。InnoDB也支持FOREIGN KEY强制。在SQL查询中，你可以自由地将InnoDB类型的表与其它MySQL的表的类型混合起来，甚至在同一个查询中也可以混合。

2.之所以选用innodb作为存储引擎的考虑
目前来说，InnoDB是为Mysql处理巨大数据量时的最大性能设计。它的CPU效率可能是任何其它基于磁盘的关系数据库引擎所不能匹敌的。在数据量大的网站或是应用中Innodb是倍受青睐的。
另一方面，在数据库的复制操作中Innodb也是能保证master和slave数据一致有一定的作用。

3.下面是对线上mysql5.6版本的数据库的配置进行的优化分析记录：
1）内存利用方面：
innodb_buffer_pool_size
这个是Innodb最重要的参数，和MyISAM的key_buffer_size有相似之处，但也是有差别的。
这个参数主要缓存innodb表的索引，数据，插入数据时的缓冲。
该参数分配内存的原则：
这个参数默认分配只有8M，可以说是非常小的一个值。
如果是一个专用DB服务器，那么他可以占到内存的70%-80%。
这个参数不能动态更改，所以分配需多考虑。分配过大，会使Swap占用过多，致使Mysql的查询特慢。
如果你的数据比较小，那么可分配是你的数据大小+10%左右做为这个参数的值。
例如：数据大小为50M,那么给这个值分配innodb_buffer_pool_size＝64M
设置方法，在my.cnf文件里：
innodb_buffer_pool_size=4G
\----------------------------------------------------------------------------------------------------------
注意：
在Mysql5.7版本之前，调整innodb_buffer_pool_size大小必须在my.cnf配置里修改，然后重启mysql进程才可以生效。
如今到了Mysql5.7版本，就可以直接动态调整这个参数，方便了很多。

尤其是在服务器内存增加之后，运维人员不能粗心大意，要记得调大Innodb_Buffer_Pool_size这个参数。
数据库配置后，要注意检查Innodb_Buffer_Pool_size这个参数的设置是否合理

需要注意的地方：
在调整innodb_buffer_pool_size 期间，用户的请求将会阻塞，直到调整完毕，所以请勿在白天调整，在凌晨3-4点低峰期调整。
调整时，内部把数据页移动到一个新的位置，单位是块。如果想增加移动的速度，需要调整innodb_buffer_pool_chunk_size参数的大小，默认是128M。

Mysql5.7中动态调整这个参数的操作记录（例如由128M增大为384M）：
134217728/1024*1024=128M
mysql> SELECT @@innodb_buffer_pool_size;

+---------------------------+

| @@innodb_buffer_pool_size |

+---------------------------+

| 134217728 |

+---------------------------+

1 row in set (0.00 sec)

mysql> SELECT @@innodb_buffer_pool_chunk_size;

+---------------------------------+

| @@innodb_buffer_pool_chunk_size |

+---------------------------------+

| 134217728 |

+---------------------------------+

1 row in set (0.00 sec)

mysql> SET GLOBAL innodb_buffer_pool_size=402653184;

Query OK, 0 rows affected (0.01 sec)

mysql> SELECT @@innodb_buffer_pool_size;

+---------------------------+

| @@innodb_buffer_pool_size |

+---------------------------+

| 402653184 |

+---------------------------+

1 row in set (0.00 sec)


innodb_buffer_pool_chunk_size的大小，计算公式是innodb_buffer_pool_size/innodb_buffer_pool_instances

比如现在初始化innodb_buffer_pool_size为2G，innodb_buffer_pool_instances实例为4，innodb_buffer_pool_chunk_size设置为1G，那么会自动把innodb_buffer_pool_chunk_size 1G调整为512M.
例：
./mysqld --innodb_buffer_pool_size=2147483648 --innodb_buffer_pool_instances=4
--innodb_buffer_pool_chunk_size=1073741824;

mysql> SELECT @@innodb_buffer_pool_size;

+---------------------------+

| @@innodb_buffer_pool_size |

+---------------------------+

| 2147483648 |

+---------------------------+

1 row in set (0.00 sec)


mysql> SELECT @@innodb_buffer_pool_instances;

+--------------------------------+

| @@innodb_buffer_pool_instances |

+--------------------------------+

| 4 |

+--------------------------------+

1 row in set (0.00 sec)


\# Chunk size was set to 1GB (1073741824 bytes) on startup but was

\# truncated to innodb_buffer_pool_size / innodb_buffer_pool_instances

mysql> SELECT @@innodb_buffer_pool_chunk_size;

+---------------------------------+

| @@innodb_buffer_pool_chunk_size |

+---------------------------------+

| 536870912 |

+---------------------------------+

1 row in set (0.00 sec)


监控Buffer Pool调整进程

mysql> SHOW STATUS WHERE Variable_name='InnoDB_buffer_pool_resize_status';

+----------------------------------+----------------------------------+

| Variable_name | Value |

+----------------------------------+----------------------------------+

| Innodb_buffer_pool_resize_status | Resizing also other hash tables. |

+----------------------------------+----------------------------------+

1 row in set (0.00 sec)


查看错误日志：
（增大）

[Note] InnoDB: Resizing buffer pool from 134217728 to 4294967296. (unit=134217728)

[Note] InnoDB: disabled adaptive hash index.

[Note] InnoDB: buffer pool 0 : 31 chunks (253952 blocks) was added.

[Note] InnoDB: buffer pool 0 : hash tables were resized.

[Note] InnoDB: Resized hash tables at lock_sys, adaptive hash index, dictionary.

[Note] InnoDB: completed to resize buffer pool from 134217728 to 4294967296.

[Note] InnoDB: re-enabled adaptive hash index.


（减少）

[Note] InnoDB: Resizing buffer pool from 4294967296 to 134217728. (unit=134217728)

[Note] InnoDB: disabled adaptive hash index.

[Note] InnoDB: buffer pool 0 : start to withdraw the last 253952 blocks.

[Note] InnoDB: buffer pool 0 : withdrew 253952 blocks from free list. tried to relocate 0 pages. (253952/253952)

[Note] InnoDB: buffer pool 0 : withdrawn target 253952 blocks.

[Note] InnoDB: buffer pool 0 : 31 chunks (253952 blocks) was freed.

[Note] InnoDB: buffer pool 0 : hash tables were resized.

[Note] InnoDB: Resized hash tables at lock_sys, adaptive hash index, dictionary.

[Note] InnoDB: completed to resize buffer pool from 4294967296 to 134217728.

[Note] InnoDB: re-enabled adaptive hash index.

\----------------------------------------------------------------------------------------------------------

innodb_additional_mem_pool_size
用来存放Innodb的内部目录，这个值不用分配太大，系统可以自动调。通常设置16Ｍ够用了，如果表比较多，可以适当的增大。
设置方法，在my.cnf文件里：
innodb_additional_mem_pool_size = 16M

2）关于日志方面：
innodb_log_file_size
作用：指定在一个日志组中，每个log的大小。
结合innodb_buffer_pool_size设置其大小，25%-100%。避免不需要的刷新。
注意：这个值分配的大小和数据库的写入速度，事务大小，异常重启后的恢复有很大的关系。一般取256M可以兼顾性能和recovery的速度。
分配原则：几个日值成员大小加起来差不多和你的innodb_buffer_pool_size相等。上限为每个日值上限大小为4G.一般控制在几个Log文件相加大小在2G以内为佳。具体情况还需要看你的事务大小，数据大小为依据。
说明：这个值分配的大小和数据库的写入速度，事务大小，异常重启后的恢复有很大的关系。
设置方法：在my.cnf文件里：
innodb_log_file_size = 256M

innodb_log_files_in_group
作用：指定你有几个日值组。
分配原则：　一般我们可以用2-3个日值组。默认为两个。
设置方法：在my.cnf文件里：
innodb_log_files_in_group=3

innodb_log_buffer_size：
作用：事务在内存中的缓冲，也就是日志缓冲区的大小， 默认设置即可，具有大量事务的可以考虑设置为16M。
如果这个值增长过快，可以适当的增加innodb_log_buffer_size
另外如果你需要处理大理的TEXT，或是BLOB字段，可以考虑增加这个参数的值。
设置方法：在my.cnf文件里：
innodb_log_buffer_size=3M

innodb_flush_logs_at_trx_commit
作用：控制事务的提交方式,也就是控制log的刷新到磁盘的方式。
分配原则：这个参数只有3个值（0，1，2）.默认为1，性能更高的可以设置为0或是2，这样可以适当的减少磁盘IO（但会丢失一秒钟的事务。），游戏库的MySQL建议设置为0。主库请不要更改了。
其中：
0：log buffer中的数据将以每秒一次的频率写入到log file中，且同时会进行文件系统到磁盘的同步操作，但是每个事务的commit并不会触发任何log buffer 到log file的刷新或者文件系统到磁盘的刷新操作；
1：（默认为1）在每次事务提交的时候将logbuffer 中的数据都会写入到log file，同时也会触发文件系统到磁盘的同步；
2：事务提交会触发log buffer 到log file的刷新，但并不会触发磁盘文件系统到磁盘的同步。此外，每秒会有一次文件系统到磁盘同步操作。
说明：
这个参数的设置对Ｉｎｎｏｄｂ的性能有很大的影响，所以在这里给多说明一下。
当这个值为1时：innodb 的事务LOG在每次提交后写入日值文件，并对日值做刷新到磁盘。这个可以做到不丢任何一个事务。
当这个值为2时：在每个提交，日志缓冲被写到文件，但不对日志文件做到磁盘操作的刷新,在对日志文件的刷新在值为2的情况也每秒发生一次。但需要注意的是，由于进程调用方面的问题，并不能保证每秒１００％的发生。从而在性能上是最快的。但操作系统崩溃或掉电才会删除最后一秒的事务。
当这个值为0时：日志缓冲每秒一次地被写到日志文件，并且对日志文件做到磁盘操作的刷新，但是在一个事务提交不做任何操作。mysqld进程的崩溃会删除崩溃前最后一秒的事务。
从以上分析，当这个值不为１时，可以取得较好的性能，但遇到异常会有损失，所以需要根据自已的情况去衡量。
设置方法：在my.cnf文件里：
innodb_flush_logs_at_trx_commit=1

3）文件IO分配，空间占用方面
innodb_file_per_table
作用：使每个Innodb的表，有自已独立的表空间。如删除文件后可以回收那部分空间。默认是关闭的，建议打开（innodb_file_per_table=1）
分配原则：只有使用不使用。但DB还需要有一个公共的表空间。
设置方法：在my.cnf文件里：
innodb_file_per_table=1

innodb_file_io_threads
作用：文件读写IO数，这个参数只在Windows上起作用。在Linux上只会等于4，默认即可！
设置方法：在my.cnf文件里：
innodb_file_io_threads=4

innodb_open_files
作用：限制Innodb能打开的表的数据。
分配原则：这个值默认是300。如果库里的表特别多的情况，可以适当增大为1000。innodb_open_files的大小对InnoDB效率的影响比较小。但是在InnoDBcrash的情况下，innodb_open_files设置过小会影响recovery的效率。所以用InnoDB的时候还是把innodb_open_files放大一些比较合适。
设置方法：在my.cnf文件里：
innodb_open_files=800

innodb_data_file_path
指定表数据和索引存储的空间，可以是一个或者多个文件。最后一个数据文件必须是自动扩充的，也只有最后一个文件允许自动扩充。这样，当空间用完后，自动扩充数据文件就会自动增长（以8MB为单位）以容纳额外的数据。
例如： innodb_data_file_path=/disk1/ibdata1:900M;/disk2/ibdata2:50M:autoextend 两个数据文件放在不同的磁盘上。数据首先放在ibdata1 中，当达到900M以后，数据就放在ibdata2中。
设置方法，在my.cnf文件里：
innodb_data_file_path =ibdata1:1G;ibdata2:1G;ibdata3:1G;ibdata4:1G;ibdata5:1G;ibdata6:1G:autoextend

innodb_data_home_dir
放置表空间数据的目录，默认在mysql的数据目录，设置到和MySQL安装文件不同的分区可以提高性能。
设置方法，在my.cnf文件里：（比如mysql的数据目录是/data/mysql/data，这里可以设置到不通的分区/home/mysql下）
innodb_data_home_dir = /home/mysql

4）其它相关参数（适当的增加table_cache）
这里说明一个比较重要的参数：
innodb_flush_method
作用：Innodb和系统打交道的一个IO模型
分配原则：
Windows不用设置。
linux可以选择：O_DIRECT
直接写入磁盘，禁止系统Cache了
设置方法：在my.cnf文件里：
innodb_flush_method=O_DIRECT

innodb_max_dirty_pages_pct
作用：在buffer pool缓冲中，允许Innodb的脏页的百分比，值在范围1-100,默认为90，建议保持默认。
这个参数的另一个用处：当Innodb的内存分配过大，致使Swap占用严重时，可以适当的减小调整这个值，使达到Swap空间释放出来。建义：这个值最大在90%，最小在15%。太大，缓存中每次更新需要致换数据页太多，太小，放的数据页太小，更新操作太慢。
设置方法：在my.cnf文件里：
innodb_max_dirty_pages_pct＝90
动态更改需要有管理员权限：
set global innodb_max_dirty_pages_pct=50;

innodb_thread_concurrency
同时在Innodb内核中处理的线程数量。建议默认值。
设置方法，在my.cnf文件里：
innodb_thread_concurrency = 16

5）公共参数调优
skip-external-locking
MyISAM存储引擎也同样会使用这个参数，MySQL4.0之后，这个值默认是开启的。
作用是避免MySQL的外部锁定(老版本的MySQL此参数叫做skip-locking)，减少出错几率增强稳定性。建议默认值。
设置方法，在my.cnf文件里：
skip-external-locking

skip-name-resolve
禁止MySQL对外部连接进行DNS解析（默认是关闭此项设置的，即默认解析DNS），使用这一选项可以消除MySQL进行DNS解析的时间。
但需要注意，如果开启该选项，则所有远程主机连接授权都要使用IP地址方式，否则MySQL将无法正常处理连接请求！如果需要，可以设置此项。
设置方法，在my.cnf文件里：（我这线上mysql数据库中打开了这一设置）
skip-name-resolve

max_connections
设置最大连接（用户）数，每个连接MySQL的用户均算作一个连接，max_connections的默认值为100。此值需要根据具体的连接数峰值设定。
设置方法，在my.cnf文件里：
max_connections = 3000

query_cache_size
查询缓存大小，如果表的改动非常频繁，或者每次查询都不同，查询缓存的结果会减慢系统性能。可以设置为0。
设置方法，在my.cnf文件里：
query_cache_size = 512M

sort_buffer_size
connection级的参数，排序缓存大小。一般设置为2-4MB即可。
设置方法，在my.cnf文件里：
sort_buffer_size = 1024M

read_buffer_size
connection级的参数。一般设置为2-4MB即可。
设置方法，在my.cnf文件里：
read_buffer_size = 1024M

max_allowed_packet
网络包的大小，为避免出现较大的网络包错误，建议设置为16M
设置方法，在my.cnf文件里：
max_allowed_packet = 16M

table_open_cache
当某一连接访问一个表时，MySQL会检查当前已缓存表的数量。如果该表已经在缓存中打开，则会直接访问缓存中的表，以加快查询速度；如果该表未被缓存，则会将当前的表添加进缓存并进行查询。
通过检查峰值时间的状态值Open_tables和Opened_tables，可以决定是否需要增加table_open_cache的值。
如果发现open_tables等于table_open_cache，并且opened_tables在不断增长，那么就需要增加table_open_cache的值;设置为512即可满足需求。
设置方法，在my.cnf文件里：
table_open_cache = 512

myisam_sort_buffer_size
实际上这个myisam_sort_buffer_size参数意义不大，这是个字面上蒙人的参数，它用于ALTER TABLE, OPTIMIZE TABLE, REPAIR TABLE 等命令时需要的内存。默认值即可。
设置方法，在my.cnf文件里：
myisam_sort_buffer_size = 8M

thread_cache_size
线程缓存，如果一个客户端断开连接，这个线程就会被放到thread_cache_size中（缓冲池未满），SHOW STATUS LIKE 'threads%';如果 Threads_created 不断增大，那么当前值设置要改大，改到 Threads_connected 值左右。（通常情况下，这个值改善性能不大），默认8即可
设置方法，在my.cnf文件里：
thread_cache_size = 8

innodb_thread_concurrency
线程并发数，建议设置为CPU内核数*2
设置方法，在my.cnf文件里：
innodb_thread_concurrency = 8

key_buffer_size
仅作用于 MyISAM存储引擎，用来设置用于缓存 MyISAM存储引擎中索引文件的内存区域大小。如果我们有足够的内存，这个缓存区域最好是能够存放下我们所有的 MyISAM 引擎表的所有索引，以尽可能提高性能。不要设置超过可用内存的30%。即使不用MyISAM表，也要设置该值8-64M，用于临时表。
设置方法，在my.cnf文件里：
key_buffer_size = 8M

**－－－－－－－－－－－影响InnoDB性能的一些重要参数－－－－－－－－－－－－－－**
1）InnoDB_buffer_pool_size
这个参数定义InnoDB存储引擎的表数据和索引数据的最大内存缓冲区,InnoDB_buffer_pool_size参数同时提供为数据块和索引块做缓存.这个值设置的越高,访问表中数据需要的磁盘IO就越少.

2）InnoDB_flush_log_at_trx_commit
这个参数控制缓冲区的数据写入到日志文件以及日志文件数据刷新到磁盘的操作时机.在正式环境中建议设置成1。
设置0时日志缓冲每秒一次被写到日志文件,并且对日志文件做向磁盘刷新的操作,但是在一个事物提交不做任何操作.
设置1时在每个事物提交时,日志缓冲被写到日志文件,并且对日志文件做向磁盘刷新的操作
设置2时在每个事物提交时,日志缓冲被写到日志文件,但不对日志文件做向磁盘刷新的操作,对日志文件每秒向磁盘做一次刷新操作.

3）InnoDB_additional_mem_pool_size
这个参数是InnoDB用来存储数据库结构和其他内部数据结构的内存池.应用程序的表越多,则需要从这里分配越多的内存,如果用光这个池,则会从OS层分配.

4）InnoDB_lock_wait_timeout
这个参数自动检测行锁导致的死锁并进行相应处理,但是对于表锁导致的死锁不能自动检测默认值为50秒.

5）InnoDB_support_xa
这个参数设置MySQL是否支持分布式事务

6）InnoDB_log_buffer_size
这个参数日志缓冲大小

7）InnoDB_log_file_size
这个参数是一个日志组中每个日志文件的大小,此参数在高写入负载尤其是大数据集的情况下很重要.这个值越大则性能相对越高,但好似副作用是一旦系统崩溃恢复的时间会加长.

8）Innodb_io_capacity
这个参数刷新脏页数量和合并插入数量，改善磁盘IO处理能力

9）Innodb_use_native_aio
异步I/O在一定程度上提高系统的并发能力，在Linux系统上，可以通过将MySQL的服务器此参数的值设定为ON设定InnoDB可以使用Linux的异步I/O子系统.

10）Innodb_read_io_threads
这个参数可调整的读请求的后台线程数

11）Innodb_write_io_threads
这个参数可调整的写请求的后台线程数

12）InnoDB_buffer_pool_instances
这个参数能较好的运行于多核处理器，支持使用 此参数对服务器变量建立多个缓冲池实例，每个缓冲池实例分别自我管理空闲列表、列表刷写、LRU以及其它跟缓冲池相关的数据结构，并通过各自的互斥锁进行保护

13）InnoDB_purge_threads
MySQL5.5以前碎片回收操作是主线程的一部分，这经定期调度的方式运行，但会阻塞数据库的其他操作.到5.5以后，可以将这个线程独立出来 ；这个能让碎片回收得更及时而且不影响其他线程的操作

14）Innodb_flush_method
这个参数控制着innodb数据文件及redo log的打开、刷写模式，对于这个参数，文档上是这样描述的：
有三个值：fdatasync(默认)，O_DSYNC，O_DIRECT
默认是fdatasync，调用fsync()去刷数据文件与redo log的buffer
为O_DSYNC时，innodb会使用O_SYNC方式打开和刷写redo log,使用fsync()刷写数据文件
为O_DIRECT时，innodb使用O_DIRECT打开数据文件，使用fsync()刷写数据文件跟redo log
总结一下三者写数据方式：
fdatasync模式：写数据时，write这一步并不需要真正写到磁盘才算完成（可能写入到操作系统buffer中就会返回完成），真正完成是flush操作，buffer交给操作系统去flush,并且文件的元数据信息也都需要更新到磁盘。
O_DSYNC模式：写日志操作是在write这步完成，而数据文件的写入是在flush这步通过fsync完成
O_DIRECT模式：数据文件的写入操作是直接从mysql innodb buffer到磁盘的，并不用通过操作系统的缓冲，而真正的完成也是在flush这步,日志还是要经过OS缓冲

使用下面命令就可以查看到上面参数的设置：
mysql> show variables like "%innodb%";

\-----------------------------------------------------------------------------------------------------------------------------------------------
下面是线上mysql（innodb）的my.cnf配置参考：
[client]
port = 3306
socket = /usr/local/mysql/var/mysql.sock

[mysqld]
port = 3306
socket = /usr/local/mysql/var/mysql.sock

basedir = /usr/local/mysql/
datadir = /data/mysql/data
pid-file = /data/mysql/data/mysql.pid
user = mysql
bind-address = 0.0.0.0
server-id = 1
sync_binlog=1
log_bin = mysql-bin

skip-name-resolve
back_log = 600

max_connections = 3000
max_connect_errors = 3000
table_open_cache = 512
max_allowed_packet = 16M
binlog_cache_size = 16M
max_heap_table_size = 16M
tmp_table_size = 256M

read_buffer_size = 1024M
read_rnd_buffer_size = 1024M
sort_buffer_size = 1024M
join_buffer_size = 1024M
key_buffer_size = 8192M

thread_cache_size = 8

query_cache_size = 512M
query_cache_limit = 1024M

ft_min_word_len = 4

binlog_format = mixed
expire_logs_days = 30

log_error = /data/mysql/data/mysql-error.log
slow_query_log = 1
long_query_time = 1
slow_query_log_file = /data/mysql/data/mysql-slow.log

performance_schema = 0
explicit_defaults_for_timestamp

skip-external-locking

 

default_storage_engine = InnoDB
innodb_file_per_table = 1
innodb_open_files = 500
innodb_buffer_pool_size = 1024M
innodb_write_io_threads = 1000
innodb_read_io_threads = 1000
innodb_thread_concurrency = 8
innodb_purge_threads = 1
innodb_flush_log_at_trx_commit = 2
innodb_log_buffer_size = 4M
innodb_log_file_size = 32M
innodb_log_files_in_group = 3
innodb_max_dirty_pages_pct = 90
innodb_lock_wait_timeout = 120

bulk_insert_buffer_size = 8M
myisam_sort_buffer_size = 8M
myisam_max_sort_file_size = 10G
myisam_repair_threads = 1

interactive_timeout = 28800
wait_timeout = 28800

[mysqldump]
quick
max_allowed_packet = 16M

[myisamchk]
key_buffer_size = 8M
sort_buffer_size = 8M
read_buffer = 4M
write_buffer = 4M

sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
port = 3306

\--------------------------------------------------------------------------------------------------------------------------------------

下面分享一个mysql5.6下my.cnf的优化配置，能使mysql性能大大提升：
\# For advice on how to change settings please see
\# http://dev.mysql.com/doc/refman/5.6/en/server-configuration-defaults.html
\# *** DO NOT EDIT THIS FILE. It's a template which will be copied to the
\# *** default location during install, and will be replaced if you
\# *** upgrade to a newer version of MySQL.
[mysqld]
\# Remove leading # and set to the amount of RAM for the most important data
\# cache in MySQL. Start at 70% of total RAM for dedicated server, else 10%.
\# innodb_buffer_pool_size = 128M
\# Remove leading # to turn on a very important data integrity option: logging
\# changes to the binary log between backups.
\# log_bin
\# These are commonly set, remove the # and set as required.
\# basedir = .....
\# datadir = .....
\# port = .....
\# server_id = .....
\# socket = .....

\# Remove leading # to set options mainly useful for reporting servers.
\# The server defaults are faster for transactions and fast SELECTs.
\# Adjust sizes as needed, experiment to find the optimal values.
\##################################################
\#innodb
user=mysql
innodb_buffer_pool_size=6G
innodb_log_file_size=4G
innodb_log_buffer_size = 8M
innodb_flush_log_at_trx_commit=2
innodb_file_per_table=1
innodb_file_io_threads=4
innodb_flush_method=O_DIRECT
innodb_io_capacity=2000
innodb_io_capacity_max=6000
innodb_lru_scan_depth=2000
innodb_thread_concurrency = 0
innodb_additional_mem_pool_size=16M
innodb_autoinc_lock_mode = 2
\##################################################
\# Binary log/replication
log-bin
sync_binlog=1
sync_relay_log=1
relay-log-info-repository=TABLE
master-info-repository=TABLE
expire_logs_days=7
binlog_format=ROW
transaction-isolation=READ-COMMITTED
\#################################################
\#cache
tmp_table_size=512M
character-set-server=utf8
collation-server=utf8_general_ci
skip-external-locking
back_log=1024
key_buffer_size=1024M
thread_stack=256k
read_buffer_size=8M
thread_cache_size=64
query_cache_size=128M
max_heap_table_size=256M
query_cache_type=1
binlog_cache_size = 2M
table_open_cache=128
thread_cache=1024
thread_concurrency=8
wait_timeout=30
join_buffer_size = 1024M
sort_buffer_size = 8M
read_rnd_buffer_size = 8M
\#################################################
\#connect
max-connect-errors=100000
max-connections=1000
\#################################################
explicit_defaults_for_timestamp=true
sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
\##################################################

参数解释：

\# Binary log/replication(这里主要是复制功能，也就是主从,提前配置好，后面讲主从配置)

\#二进制日志
log-bin
\#为了在最大程序上保证复制的InnoDB事务持久性和一致性
sync_binlog=1
sync_relay_log=1
\#启用此两项,可用于实现在崩溃时保证二进制及从服务器安全的功能
relay-log-info-repository=TABLE
master-info-repository=TABLE
\#设置清除日志时间
expire_logs_days=7
\#行复制
binlog_format=ROW
\#mysql数据库事务隔离级别有四种(READ UNCOMMITTED,READ COMMITTED,REPEATABLE READ,SERIALIZABLE)
transaction-isolation=READ-COMMITTED

\#cache
\#内部内存临时表的最大值
tmp_table_size=512M
character-set-server=utf8
collation-server=utf8_general_ci
\#即跳过外部锁定
skip-external-locking
\#MySQL能暂存的连接数量(根据实际设置)
back_log=1024
\#指定索引缓冲区的大小,只对MyISAM表起作用,这里写上也没有关系
key_buffer_size=1024M
\#这条指令限定用于每个数据库线程的栈大小
thread_stack=256k
\#当一个查询不断地扫描某一个表,MySQL会为它分配一段内存缓冲区
read_buffer_size=8M
\#线程缓存
thread_cache_size=64
\#查询缓存大小
query_cache_size=128M
\#内部内存临时表的最大值,每个线程都要分配
max_heap_table_size=256M
\#将查询结果放入查询缓存中
query_cache_type=1
\#代表在事务过程中容纳二进制日志SQL语句的缓存大小
binlog_cache_size = 2M
\#同样是缓存表大小
table_open_cache=128
\#缓存线程
thread_cache=1024
\#推荐设置为服务器 CPU核数的2倍
thread_concurrency=8
wait_timeout=30
\#表和表联接的缓冲区的大小
join_buffer_size = 1024M
\#是一个connection级参数,在每个connection第一次需要使用这个buffer的时候,一次性分配设置的内存
sort_buffer_size=8M
\#随机读取数据缓冲区使用内存
read_rnd_buffer_size = 8M

\#connect
\#是一个MySQL中与安全有关的计数器值,它负责阻止过多尝试失败的客户端以防止暴力破解密码
max-connect-errors=100000
\#连接数
max-connections=1000
\#开启查询缓存
explicit_defaults_for_timestamp=true
\#mysql服务器能够工作在不同的模式下,并能针对不同的客户端以不同的方式应用这些模式
sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES

下面列出了对性能优化影响较大的主要变量，主要分为连接请求的变量和缓冲区变量。
1.连接请求的变量：
1) max_connections
MySQL的最大连接数，增加该值增加mysqld 要求的文件描述符的数量。如果服务器的并发连接请求量比较大，建议调高此值，以增加并行连接数量，当然这建立在机器能支撑的情况下，因为如果连接数越多， 介于MySQL会为每个连接提供连接缓冲区，就会开销越多的内存，所以要适当调整该值，不能盲目提高设值。
数值过小会经常出现ERROR 1040: Too many connections错误，可以过’conn%’通配符查看当前状态的连接数量，以定夺该值的大小。
show variables like ‘max_connections’ 最大连接数
show status like ‘max_used_connections’响应的连接数
如下：
mysql> show variables like ‘max_connections‘;
+———————–+——-+
| Variable_name　| Value |
+———————–+——-+
| max_connections | 256　　|
+———————–+——-+
mysql> show status like ‘max%connections‘;
+———————–+——-+
| Variable_name　 | Value |
+—————————-+——-+
| max_used_connections | 256|
+—————————-+——-+
max_used_connections / max_connections * 100% （理想值≈ 85%）
如果max_used_connections跟max_connections相同 那么就是max_connections设置过低或者超过服务器负载上限了，低于10%则设置过大。
2) back_log
MySQL能暂存的连接数量。当主要MySQL线程在一个很短时间内得到非常多的连接请求，这就起作用。如果MySQL的连接数据达到 max_connections时，新来的请求将会被存在堆栈中，以等待某一连接释放资源，该堆栈的数量即back_log，如果等待连接的数量超过 back_log，将不被授予连接资源。
back_log值指出在MySQL暂时停止回答新请求之前的短时间内有多少个请求可以被存在堆栈中。只有如果期望在一个短时间内有很多连接，你需要增加它，换句话说，这值对到来的TCP/IP连接的侦听队列的大小。
当观察你主机进程列表（mysql> show full processlist），发现大量264084 | unauthenticated user | xxx.xxx.xxx.xxx | NULL | Connect | NULL | login | NULL 的待连接进程时，就要加大back_log 的值了。
默认数值是50，可调优为128，对系统设置范围为小于512的整数。
3) interactive_timeout
一个交互连接在被服务器在关闭前等待行动的秒数。一个交互的客户被定义为对mysql_real_connect()使用CLIENT_INTERACTIVE 选项的客户。
默认数值是28800，可调优为7200。
\2. 缓冲区变量
全局缓冲：
4) key_buffer_size
key_buffer_size指定索引缓冲区的大小，它决定索引处理的速度，尤其是索引读的速度。通过检查状态值 Key_read_requests和Key_reads，可以知道key_buffer_size设置是否合理。比例key_reads / key_read_requests应该尽可能的低，至少是1:100，1:1000更好（上述状态值可以使用SHOW STATUS LIKE ‘key_read%’获得）。
key_buffer_size只对MyISAM表起作用。即使你不使用MyISAM表，但是内部的临时磁盘表是MyISAM表，也要使用该值。可以使用检查状态值created_tmp_disk_tables得知详情。
举例如下：
mysql> show variables like ‘key_buffer_size‘;
+——————-+————+
| Variable_name | Value |
+———————+————+
| key_buffer_size | 536870912 |
+———— ———-+————+
key_buffer_size为512MB，我们再看一下key_buffer_size的使用情况：
mysql> show global status like ‘key_read%‘;
+————————+————-+
| Variable_name　 | Value |
+————————+————-+
| Key_read_requests| 27813678764 |
| Key_reads　　　| 6798830 |
+————————+————-+
一共有27813678764个索引读取请求，有6798830个请求在内存中没有找到直接从硬盘读取索引，计算索引未命中缓存的概率：
key_cache_miss_rate ＝Key_reads / Key_read_requests * 100%，设置在1/1000左右较好
默认配置数值是8388600(8M)，主机有4GB内存，可以调优值为268435456(256MB)。
5) query_cache_size
使用查询缓冲，MySQL将查询结果存放在缓冲区中，今后对于同样的SELECT语句（区分大小写），将直接从缓冲区中读取结果。
通过检查状态值Qcache_*，可以知道query_cache_size设置是否合理（上述状态值可以使用SHOW STATUS LIKE ‘Qcache%’获得）。如果Qcache_lowmem_prunes的值非常大，则表明经常出现缓冲不够的情况，如果Qcache_hits的值也 非常大，则表明查询缓冲使用非常频繁，此时需要增加缓冲大小；如果Qcache_hits的值不大，则表明你的查询重复率很低，这种情况下使用查询缓冲反 而会影响效率，那么可以考虑不用查询缓冲。此外，在SELECT语句中加入SQL_NO_CACHE可以明确表示不使用查询缓冲。

与查询缓冲有关的参数还有query_cache_type、query_cache_limit、query_cache_min_res_unit。
query_cache_type指定是否使用查询缓冲，可以设置为0、1、2，该变量是SESSION级的变量。
query_cache_limit指定单个查询能够使用的缓冲区大小，缺省为1M。
query_cache_min_res_unit是在4.1版本以后引入的，它指定分配缓冲区空间的最小单位，缺省为4K。检查状态值 Qcache_free_blocks，如果该值非常大，则表明缓冲区中碎片很多，这就表明查询结果都比较小，此时需要减小 query_cache_min_res_unit。
举例如下：
mysql> show global status like ‘qcache%‘;
+——————————-+—————–+
| Variable_name | Value　 |
+——————————-+—————–+
| Qcache_free_blocks　 | 22756　 |
| Qcache_free_memory　 | 76764704 |
| Qcache_hits　　　　　 | 213028692 |
| Qcache_inserts　　　　 | 208894227 |
| Qcache_lowmem_prunes | 4010916 |
| Qcache_not_cached　| 13385031 |
| Qcache_queries_in_cache | 43560　|
| Qcache_total_blocks | 111212　 |
+——————————-+—————–+
mysql> show variables like ‘query_cache%‘;
+————————————–+————–+
| Variable_name　　　　　 | Value　 |
+————————————–+———–+
| query_cache_limit　　　　　 | 2097152 |
| query_cache_min_res_unit　 | 4096　　 |
| query_cache_size　　　　　 | 203423744 |
| query_cache_type　　　　　 | ON　 |
| query_cache_wlock_invalidate | OFF　 |
+————————————–+—————+
查询缓存碎片率= Qcache_free_blocks / Qcache_total_blocks * 100%
如果查询缓存碎片率超过20%，可以用FLUSH QUERY CACHE整理缓存碎片，或者试试减小query_cache_min_res_unit，如果你的查询都是小数据量的话。
查询缓存利用率= (query_cache_size – Qcache_free_memory) / query_cache_size * 100%
查询缓存利用率在25%以下的话说明query_cache_size设置的过大，可适当减小；查询缓存利用率在80％以上而且Qcache_lowmem_prunes > 50的话说明query_cache_size可能有点小，要不就是碎片太多。
查询缓存命中率= (Qcache_hits – Qcache_inserts) / Qcache_hits * 100%
示例服务器查询缓存碎片率＝20.46％，查询缓存利用率＝62.26％，查询缓存命中率＝1.94％，命中率很差，可能写操作比较频繁吧，而且可能有些碎片。
每个连接的缓冲
6) record_buffer_size
每个进行一个顺序扫描的线程为其扫描的每张表分配这个大小的一个缓冲区。如果你做很多顺序扫描，你可能想要增加该值。
默认数值是131072(128K)，可改为16773120 (16M)
7) read_rnd_buffer_size
随机读缓冲区大小。当按任意顺序读取行时(例如，按照排序顺序)，将分配一个随机读缓存区。进行排序查询时，MySQL会首先扫描一遍该缓冲，以避 免磁盘搜索，提高查询速度，如果需要排序大量数据，可适当调高该值。但MySQL会为每个客户连接发放该缓冲空间，所以应尽量适当设置该值，以避免内存开 销过大。
一般可设置为16M
8) sort_buffer_size
每个需要进行排序的线程分配该大小的一个缓冲区。增加这值加速ORDER BY或GROUP BY操作。
默认数值是2097144(2M)，可改为16777208 (16M)。
9) join_buffer_size
联合查询操作所能使用的缓冲区大小
record_buffer_size，read_rnd_buffer_size，sort_buffer_size，join_buffer_size为每个线程独占，也就是说，如果有100个线程连接，则占用为16M*100
10) table_cache
表高速缓存的大小。每当MySQL访问一个表时，如果在表缓冲区中还有空间，该表就被打开并放入其中，这样可以更快地访问表内容。通过检查峰值时间的状态值Open_tables和Opened_tables，可以决定是否需要增加table_cache的值。如 果你发现open_tables等于table_cache，并且opened_tables在不断增长，那么你就需要增加table_cache的值了 （上述状态值可以使用SHOW STATUS LIKE ‘Open%tables’获得）。注意，不能盲目地把table_cache设置成很大的值。如果设置得太高，可能会造成文件描述符不足，从而造成性能 不稳定或者连接失败。
1G内存机器，推荐值是128－256。内存在4GB左右的服务器该参数可设置为256M或384M。
11) max_heap_table_size
用户可以创建的内存表(memory table)的大小。这个值用来计算内存表的最大行数值。这个变量支持动态改变，即set @max_heap_table_size=#
这个变量和tmp_table_size一起限制了内部内存表的大小。如果某个内部heap（堆积）表大小超过tmp_table_size，MySQL可以根据需要自动将内存中的heap表改为基于硬盘的MyISAM表。
12) tmp_table_size
通过设置tmp_table_size选项来增加一张临时表的大小，例如做高级GROUP BY操作生成的临时表。如果调高该值，MySQL同时将增加heap表的大小，可达到提高联接查询速度的效果，建议尽量优化查询，要确保查询过程中生成的临时表在内存中，避免临时表过大导致生成基于硬盘的MyISAM表。
mysql> show global status like ‘created_tmp%‘;
+——————————–+———+
| Variable_name　　 | Value　|
+———————————-+———+
| Created_tmp_disk_tables | 21197 |
| Created_tmp_files　　　| 58　　|
| Created_tmp_tables　　| 1771587 |
+——————————–+———–+
每次创建临时表，Created_tmp_tables增加，如果临时表大小超过tmp_table_size，则是在磁盘上创建临时 表，Created_tmp_disk_tables也增加,Created_tmp_files表示MySQL服务创建的临时文件文件数，比较理想的配 置是：
Created_tmp_disk_tables / Created_tmp_tables * 100% <= 25%比如上面的服务器Created_tmp_disk_tables / Created_tmp_tables * 100% ＝1.20%，应该相当好了
默认为16M，可调到64-256最佳，线程独占，太大可能内存不够I/O堵塞
13) thread_cache_size
可以复用的保存在中的线程的数量。如果有，新的线程从缓存中取得，当断开连接的时候如果有空间，客户的线置在缓存中。如果有很多新的线程，为了提高性能可以这个变量值。
通过比较 Connections和Threads_created状态的变量，可以看到这个变量的作用。
默认值为110，可调优为80。
14) thread_concurrency
推荐设置为服务器 CPU核数的2倍，例如双核的CPU, 那么thread_concurrency的应该为4；2个双核的cpu, thread_concurrency的值应为8。默认为8
15) wait_timeout
指定一个请求的最大连接时间，对于4GB左右内存的服务器可以设置为5-10。
\3. 配置InnoDB的几个变量
innodb_buffer_pool_size
对于InnoDB表来说，innodb_buffer_pool_size的作用就相当于key_buffer_size对于MyISAM表的作用一样。InnoDB使用该参数指定大小的内存来缓冲数据和索引。对于单独的MySQL数据库服务器，最大可以把该值设置成物理内存的80%。
根据MySQL手册，对于2G内存的机器，推荐值是1G（50%）。

innodb_flush_log_at_trx_commit
主要控制了innodb将log buffer中的数据写入日志文件并flush磁盘的时间点，取值分别为0、1、2三个。0，表示当事务提交时，不做日志写入操作，而是每秒钟将log buffer中的数据写入日志文件并flush磁盘一次；1，则在每秒钟或是每次事物的提交都会引起日志文件写入、flush磁盘的操作，确保了事务的 ACID；设置为2，每次事务提交引起写入日志文件的动作，但每秒钟完成一次flush磁盘操作。
实际测试发现，该值对插入数据的速度影响非常大，设置为2时插入10000条记录只需要2秒，设置为0时只需要1秒，而设置为1时则需要229秒。因此，MySQL手册也建议尽量将插入操作合并成一个事务，这样可以大幅提高速度。
根据MySQL手册，在允许丢失最近部分事务的危险的前提下，可以把该值设为0或2。

innodb_log_buffer_size
log缓存大小，一般为1-8M，默认为1M，对于较大的事务，可以增大缓存大小。
可设置为4M或8M。

innodb_additional_mem_pool_size
该参数指定InnoDB用来存储数据字典和其他内部数据结构的内存池大小。缺省值是1M。通常不用太大，只要够用就行，应该与表结构的复杂度有关系。如果不够用，MySQL会在错误日志中写入一条警告信息。
根据MySQL手册，对于2G内存的机器，推荐值是20M，可适当增加。

innodb_thread_concurrency=8
推荐设置为 2*(NumCPUs+NumDisks)，默认一般为8

MySQL 5.6相比于前代GA版本性能提升显著，但默认缓存设置对于小型站点并不合理。通过修改my.ini文件中的performance_schema_max_table_instances参数，能够有效降低内存占用。
以下是5.6默认的设置
performance_schema_max_table_instances 12500
table_definition_cache 1400
table_open_cache 2000
可以调成，或者在小点都可以。

performance_schema_max_table_instances=600
table_definition_cache=400
table_open_cache=256

performance_schema_max_table_instances
The maximum number of instrumented table objects 检测的表对象的最大数目。
table_definition_cache
The number of table definitions (from .frm files) that can be stored in the definition cache. If you use a large number of tables, you can create a large table definition cache to speed up opening of tables. The table definition cache takes less space and does not use file descriptors, unlike the normal table cache. The minimum and default values are both 400.
缓存frm文件

table_open_cache
The number of open tables for all threads. Increasing this value increases the number of file descriptors that mysqld requires.
table_open_cache 指的是缓存数据文件的描述符(Linux/Unix)相关信息
这个很重要啊，之前mount个单独的文件，数据库一直不成功，原来是这个在作怪啊。
chcon -R -t mysqld_db_t /home/myusqldata


mysql> show variables;
一、慢查询
mysql> show variables like '%slow%';
+------------------+-------+
| Variable_name | Value |
+------------------+-------+
| log_slow_queries | ON |
| slow_launch_time | 2 |
+------------------+-------+

mysql> show global status like '%slow%';
+---------------------+-------+
| Variable_name | Value |
+---------------------+-------+
| Slow_launch_threads | 0 |
| Slow_queries | 4148 |
+---------------------+-------+
配置中打开了记录慢查询，执行时间超过2秒的即为慢查询，系统显示有4148个慢查询，你可以分析慢查询日志，找出有问题的SQL语句，慢查询时间不宜设置过长，否则意义不大，最好在5秒以内，如果你需要微秒级别的慢查询，可以考虑给MySQL打补丁，记得找对应的版本。
打开慢查询日志可能会对系统性能有一点点影响，如果你的MySQL是主－从结构，可以考虑打开其中一台从服务器的慢查询日志，这样既可以监控慢查询，对系统性能影响又小。
二、连接数
经常会遇见”MySQL: ERROR 1040: Too manyconnections”的情况，一种是访问量确实很高，MySQL服务器抗不住，这个时候就要考虑增加从服务器分散读压力，另外一种情况是MySQL配置文件中max_connections值过小：
mysql> show variables like 'max_connections';
+-----------------+-------+
| Variable_name | Value |
+-----------------+-------+
| max_connections | 256 |
+-----------------+-------+
这台MySQL服务器最大连接数是256，然后查询一下服务器响应的最大连接数：
mysql> show global status like 'Max_used_connections';
+----------------------+-------+
| Variable_name | Value |
+----------------------+-------+
| Max_used_connections | 245 |
+----------------------+-------+
MySQL服务器过去的最大连接数是245，没有达到服务器连接数上限256，应该没有出现1040错误，比较理想的设置是：
Max_used_connections / max_connections * 100% ≈ 85%
最大连接数占上限连接数的85％左右，如果发现比例在10%以下，MySQL服务器连接数上限设置的过高了。
三、Key_buffer_size
key_buffer_size是对MyISAM表性能影响最大的一个参数，下面一台以MyISAM为主要存储引擎服务器的配置：
mysql> show variables like 'key_buffer_size';
+-----------------+------------+
| Variable_name | Value |
+-----------------+------------+
| key_buffer_size | 536870912 |
+-----------------+------------+
分配了512MB内存给key_buffer_size，我们再看一下key_buffer_size的使用情况：
mysql> show global status like 'key_read%';
+------------------------+-------------+
| Variable_name | Value |
+------------------------+-------------+
| Key_read_requests | 27813678764 |
| Key_reads | 6798830 |
+------------------------+-------------+
一共有27813678764个索引读取请求，有6798830个请求在内存中没有找到直接从硬盘读取索引，计算索引未命中缓存的概率：
key_cache_miss_rate ＝ Key_reads / Key_read_requests * 100%
比 如上面的数据，key_cache_miss_rate为0.0244%，4000个索引读取请求才有一个直接读硬盘，已经很BT 了，key_cache_miss_rate在0.1%以下都很好（每1000个请求有一个直接读硬盘），如果key_cache_miss_rate在 0.01%以下的话，key_buffer_size分配的过多，可以适当减少。
MySQL服务器还提供了key_blocks_*参数：
mysql> show global status like 'key_blocks_u%';
+------------------------+-------------+
| Variable_name | Value |
+------------------------+-------------+
| Key_blocks_unused | 0 |
| Key_blocks_used | 413543 |
+------------------------+-------------+
Key_blocks_unused 表示未使用的缓存簇(blocks)数，Key_blocks_used表示曾经用到的最大的blocks数，比如这台服务器，所有的缓存都用到了，要么 增加key_buffer_size，要么就是过渡索引了，把缓存占满了。比较理想的设置：
Key_blocks_used / (Key_blocks_unused + Key_blocks_used) * 100% ≈ 80%
四、临时表
mysql> show global status like 'created_tmp%';
+-------------------------+---------+
| Variable_name | Value |
+-------------------------+---------+
| Created_tmp_disk_tables | 21197 |
| Created_tmp_files | 58 |
| Created_tmp_tables | 1771587 |
+-------------------------+---------+
每次创建临时表，Created_tmp_tables增加，如果是在磁盘上创建临时表，Created_tmp_disk_tables也增加,Created_tmp_files表示MySQL服务创建的临时文件文件数，比较理想的配置是：
Created_tmp_disk_tables / Created_tmp_tables * 100% <= 25%
比如上面的服务器Created_tmp_disk_tables / Created_tmp_tables * 100% ＝ 1.20%，应该相当好了。我们再看一下MySQL服务器对临时表的配置：
mysql> show variables where Variable_name in ('tmp_table_size', 'max_heap_table_size');
+---------------------+-----------+
| Variable_name | Value |
+---------------------+-----------+
| max_heap_table_size | 268435456 |
| tmp_table_size | 536870912 |
+---------------------+-----------+
只有256MB以下的临时表才能全部放内存，超过的就会用到硬盘临时表。
五、Open Table情况
mysql> show global status like 'open%tables%';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| Open_tables | 919 |
| Opened_tables | 1951 |
+---------------+-------+
Open_tables 表示打开表的数量，Opened_tables表示打开过的表数量，如果Opened_tables数量过大，说明配置中 table_cache(5.1.3之后这个值叫做table_open_cache)值可能太小，我们查询一下服务器table_cache值：
mysql> show variables like 'table_cache';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| table_cache | 2048 |

+---------------+-------+
比较合适的值为：
Open_tables / Opened_tables * 100% >= 85%
Open_tables / table_cache * 100% <= 95%

六、进程使用情况
mysql> show global status like 'Thread%';
+-------------------+-------+
| Variable_name | Value |
+-------------------+-------+
| Threads_cached | 46 |
| Threads_connected | 2 |
| Threads_created | 570 |
| Threads_running | 1 |
+-------------------+-------+
如 果我们在MySQL服务器配置文件中设置了thread_cache_size，当客户端断开之后，服务器处理此客户的线程将会缓存起来以响应下一个客户 而不是销毁（前提是缓存数未达上限）。Threads_created表示创建过的线程数，如果发现Threads_created值过大的话，表明 MySQL服务器一直在创建线程，这也是比较耗资源，可以适当增加配置文件中thread_cache_size值，查询服务器 thread_cache_size配置：
mysql> show variables like 'thread_cache_size';
+-------------------+-------+
| Variable_name | Value |
+-------------------+-------+
| thread_cache_size | 64 |
+-------------------+-------+
示例中的服务器还是挺健康的。
七、查询缓存(query cache)
mysql> show global status like 'qcache%';
+-------------------------+-----------+
| Variable_name | Value |
+-------------------------+-----------+
| Qcache_free_blocks | 22756 |
| Qcache_free_memory | 76764704 |
| Qcache_hits | 213028692 |
| Qcache_inserts | 208894227 |
| Qcache_lowmem_prunes | 4010916 |
| Qcache_not_cached | 13385031 |
| Qcache_queries_in_cache | 43560 |
| Qcache_total_blocks | 111212 |
+-------------------------+-----------+
MySQL查询缓存变量解释：
Qcache_free_blocks：缓存中相邻内存块的个数。数目大说明可能有碎片。FLUSH QUERY CACHE会对缓存中的碎片进行整理，从而得到一个空闲块。
Qcache_free_memory：缓存中的空闲内存。
Qcache_hits：每次查询在缓存中命中时就增大
Qcache_inserts：每次插入一个查询时就增大。命中次数除以插入次数就是不中比率。
Qcache_lowmem_prunes： 缓存出现内存不足并且必须要进行清理以便为更多查询提供空间的次数。这个数字最好长时间来看；如果这个数字在不断增长，就表示可能碎片非常严重，或者内存 很少。（上面的 free_blocks和free_memory可以告诉您属于哪种情况）
Qcache_not_cached：不适合进行缓存的查询的数量，通常是由于这些查询不是 SELECT 语句或者用了now()之类的函数。
Qcache_queries_in_cache：当前缓存的查询（和响应）的数量。
Qcache_total_blocks：缓存中块的数量。
我们再查询一下服务器关于query_cache的配置：
mysql> show variables like 'query_cache%';
+------------------------------+-----------+
| Variable_name | Value |
+------------------------------+-----------+
| query_cache_limit | 2097152 |
| query_cache_min_res_unit | 4096 |
| query_cache_size | 203423744 |
| query_cache_type | ON |
| query_cache_wlock_invalidate | OFF |
+------------------------------+-----------+
各字段的解释：
query_cache_limit：超过此大小的查询将不缓存
query_cache_min_res_unit：缓存块的最小大小
query_cache_size：查询缓存大小
query_cache_type：缓存类型，决定缓存什么样的查询，示例中表示不缓存 select sql_no_cache 查询
query_cache_wlock_invalidate：当有其他客户端正在对MyISAM表进行写操作时，如果查询在query cache中，是否返回cache结果还是等写操作完成再读表获取结果。
query_cache_min_res_unit的配置是一柄”双刃剑”，默认是4KB，设置值大对大数据查询有好处，但如果你的查询都是小数据查询，就容易造成内存碎片和浪费。
查询缓存碎片率 = Qcache_free_blocks / Qcache_total_blocks * 100%
如果查询缓存碎片率超过20%，可以用FLUSH QUERY CACHE整理缓存碎片，或者试试减小query_cache_min_res_unit，如果你的查询都是小数据量的话。
查询缓存利用率 = (query_cache_size - Qcache_free_memory) / query_cache_size * 100%
查询缓存利用率在25%以下的话说明query_cache_size设置的过大，可适当减小；查询缓存利用率在80％以上而且Qcache_lowmem_prunes > 50的话说明query_cache_size可能有点小，要不就是碎片太多。
查询缓存命中率 = (Qcache_hits - Qcache_inserts) / Qcache_hits * 100%
示例服务器 查询缓存碎片率 ＝ 20.46％，查询缓存利用率 ＝ 62.26％，查询缓存命中率 ＝ 1.94％，命中率很差，可能写操作比较频繁吧，而且可能有些碎片。
八、排序使用情况
mysql> show global status like 'sort%';
+-------------------+------------+
| Variable_name | Value |
+-------------------+------------+
| Sort_merge_passes | 29 |
| Sort_range | 37432840 |
| Sort_rows | 9178691532 |
| Sort_scan | 1860569 |
+-------------------+------------+
Sort_merge_passes 包括两步。MySQL 首先会尝试在内存中做排序，使用的内存大小由系统变量Sort_buffer_size 决定，如果它的大小不够把所有的记录都读到内存中，MySQL 就会把每次在内存中排序的结果存到临时文件中，等MySQL 找到所有记录之后，再把临时文件中的记录做一次排序。这再次排序就会增加 Sort_merge_passes。实际上，MySQL会用另一个临时文件来存再次排序的结果，所以通常会看到 Sort_merge_passes增加的数值是建临时文件数的两倍。因为用到了临时文件，所以速度可能会比较慢，增加 Sort_buffer_size 会减少Sort_merge_passes 和 创建临时文件的次数。但盲目的增加 Sort_buffer_size 并不一定能提高速度，
另外，增加read_rnd_buffer_size(3.2.3是record_rnd_buffer_size)的值对排序的操作也有一点的好处，
九、文件打开数(open_files)
mysql> show global status like 'open_files';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| Open_files | 1410 |
+---------------+-------+

mysql> show variables like 'open_files_limit';
+------------------+-------+
| Variable_name | Value |
+------------------+-------+
| open_files_limit | 4590 |
+------------------+-------+
比较合适的设置：Open_files / open_files_limit * 100% <= 75％
十、表锁情况
mysql> show global status like 'table_locks%';
+-----------------------+-----------+
| Variable_name | Value |
+-----------------------+-----------+
| Table_locks_immediate | 490206328 |
| Table_locks_waited | 2084912 |
+-----------------------+-----------+
Table_locks_immediate 表示立即释放表锁数，Table_locks_waited表示需要等待的表锁数，如果Table_locks_immediate / Table_locks_waited >5000，最好采用InnoDB引擎，因为InnoDB是行锁而MyISAM是表锁，对于高并发写入的应用InnoDB效果会好些。示例中的服务 器Table_locks_immediate / Table_locks_waited ＝ 235，MyISAM就足够了。
十一、表扫描情况
mysql> show global status like 'handler_read%';
+-----------------------+-------------+
| Variable_name | Value |
+-----------------------+-------------+
| Handler_read_first | 5803750 |
| Handler_read_key | 6049319850 |
| Handler_read_next | 94440908210 |
| Handler_read_prev | 34822001724 |
| Handler_read_rnd | 405482605 |
| Handler_read_rnd_next | 18912877839 |
+-----------------------+-------------+
各字段解释参见,调出服务器完成的查询请求次数：
mysql> show global status like 'com_select';
+---------------+-----------+
| Variable_name | Value |
+---------------+-----------+
| Com_select | 222693559 |
+---------------+-----------+
计算表扫描率：
表扫描率 ＝ Handler_read_rnd_next / Com_select
如果表扫描率超过4000，说明进行了太多表扫描，很有可能索引没有建好，增加read_buffer_size值会有一些好处，但最好不要超过8MB。

要查看死锁，你要show engine innodb status\G;
在MySQL5.6版本，在my.cnf配置文件里，加入
innodb_print_all_deadlocks = 1
就可以把死锁信息打印到错误日志里