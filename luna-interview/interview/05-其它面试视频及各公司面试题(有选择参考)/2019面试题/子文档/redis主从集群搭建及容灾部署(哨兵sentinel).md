# redis主从集群搭建及容灾部署(哨兵sentinel)



Redis也用了一段时间了，记录一下相关集群搭建及配置详解，方便后续使用查阅。

### 提纲

l Redis安装

l 整体架构

l Redis主从结构搭建

l Redis容灾部署（哨兵sentinel）

l Redis常见问题

### Redis安装

发行版：CentOS-6.6 64bit

内核：2.6.32-504.el6.x86_64

CPU：intel-i7 3.6G

内存：2G

#### 下载redis，选择合适的版本

[root@rocket software]# wget http://download.redis.io/releases/redis-2.8.17.tar.gz

[root@rocket software]# cd redis-2.8.17

[root@rocket redis-2.8.17]# make

[root@rocket redis-2.8.17]# make test

cd src && make test

make[1]: Entering directory `/home/software/redis-2.8.17/src'

You need tcl 8.5 or newer in order to run the Redis test

make[1]: *** [test] Error 1

make[1]: Leaving directory `/home/software/redis-2.8.17/src'

make: *** [test] Error 2

#### make test报错，安装tcl

[root@rocket software]# wget http://prdownloads.sourceforge.net/tcl/tcl8.5.18-src.tar.gz

[root@rocket software]# tar -zxvf tcl8.5.18-src.tar.gz

[root@rocket software]# cd tcl8.5.18

[root@rocket tcl8.5.18]# cd unix/

[root@rocket unix]# ./configure;make;make test;make install

#### tcl安装成功，继续测试redis的安装情况

[root@rocket redis-2.8.17]# make test

……

Cleanup: may take some time... OK

make[1]: Leaving directory `/home/software/redis-2.8.17/src'

说明redis安装正常

### 整体架构

#### 整体架构图

这里是本文所搭建集群的整体架构，使用主从结构+哨兵（sentinel）来进行容灾。

![816350-20160114195228553-218244342](redis主从集群搭建及容灾部署(哨兵sentinel).assets/816350-20160114195228553-218244342.png)

![816350-20160114195229475-1209046897](redis主从集群搭建及容灾部署(哨兵sentinel).assets/816350-20160114195229475-1209046897.jpg)

### Redis主从结构搭建

#### 搭建redis master

##### 拷贝可执行文件

[root@rocket master]# pwd

/usr/local/redisDB/master

[root@rocket master]# cp /home/software/redis-2.8.17/src/redis-cli .

[root@rocket master]# cp /home/software/redis-2.8.17/src/redis-server .

##### 配置文件redis.conf



```
# 守护进程模式
daemonize yes 

# pid file
pidfile /var/run/redis.pid

# 监听端口
port 7003

# TCP接收队列长度，受/proc/sys/net/core/somaxconn和tcp_max_syn_backlog这两个内核参数的影响
tcp-backlog 511

# 一个客户端空闲多少秒后关闭连接(0代表禁用，永不关闭)
timeout 0

# 如果非零，则设置SO_KEEPALIVE选项来向空闲连接的客户端发送ACK
tcp-keepalive 60

# 指定服务器调试等级
# 可能值：
# debug （大量信息，对开发/测试有用）
# verbose （很多精简的有用信息，但是不像debug等级那么多）
# notice （适量的信息，基本上是你生产环境中需要的）
# warning （只有很重要/严重的信息会记录下来）
loglevel notice

# 指明日志文件名
logfile "./redis7003.log"

# 设置数据库个数
databases 16

# 会在指定秒数和数据变化次数之后把数据库写到磁盘上
# 900秒（15分钟）之后，且至少1次变更
# 300秒（5分钟）之后，且至少10次变更
# 60秒之后，且至少10000次变更
save 900 1
save 300 10
save 60 10000


# 默认如果开启RDB快照(至少一条save指令)并且最新的后台保存失败，Redis将会停止接受写操作
# 这将使用户知道数据没有正确的持久化到硬盘，否则可能没人注意到并且造成一些灾难
stop-writes-on-bgsave-error yes

# 当导出到 .rdb 数据库时是否用LZF压缩字符串对象
rdbcompression yes

# 版本5的RDB有一个CRC64算法的校验和放在了文件的最后。这将使文件格式更加可靠。
rdbchecksum yes

# 持久化数据库的文件名
dbfilename dump.rdb

# 工作目录
dir ./

# 当master服务设置了密码保护时，slav服务连接master的密码
masterauth 0234kz9*l

# 当一个slave失去和master的连接，或者同步正在进行中，slave的行为可以有两种：
#
# 1) 如果 slave-serve-stale-data 设置为 "yes" (默认值)，slave会继续响应客户端请求，
# 可能是正常数据，或者是过时了的数据，也可能是还没获得值的空数据。
# 2) 如果 slave-serve-stale-data 设置为 "no"，slave会回复"正在从master同步
# （SYNC with master in progress）"来处理各种请求，除了 INFO 和 SLAVEOF 命令。
slave-serve-stale-data yes

# 你可以配置salve实例是否接受写操作。可写的slave实例可能对存储临时数据比较有用(因为写入salve
# 的数据在同master同步之后将很容易被删除
slave-read-only yes

# 是否在slave套接字发送SYNC之后禁用 TCP_NODELAY？
# 如果你选择“yes”Redis将使用更少的TCP包和带宽来向slaves发送数据。但是这将使数据传输到slave
# 上有延迟，Linux内核的默认配置会达到40毫秒
# 如果你选择了 "no" 数据传输到salve的延迟将会减少但要使用更多的带宽
repl-disable-tcp-nodelay no

# slave的优先级是一个整数展示在Redis的Info输出中。如果master不再正常工作了，哨兵将用它来
# 选择一个slave提升=升为master。
# 优先级数字小的salve会优先考虑提升为master，所以例如有三个slave优先级分别为10，100，25，
# 哨兵将挑选优先级最小数字为10的slave。
# 0作为一个特殊的优先级，标识这个slave不能作为master，所以一个优先级为0的slave永远不会被
# 哨兵挑选提升为master
slave-priority 100


# 密码验证
# 警告：因为Redis太快了，所以外面的人可以尝试每秒150k的密码来试图破解密码。这意味着你需要
# 一个高强度的密码，否则破解太容易了
requirepass 0234kz9*l 

# redis实例最大占用内存，不要用比设置的上限更多的内存。一旦内存使用达到上限，Redis会根据选定的回收策略（参见：
# maxmemmory-policy）删除key
maxmemory 3gb

# 最大内存策略：如果达到内存限制了，Redis如何选择删除key。你可以在下面五个行为里选：
# volatile-lru -> 根据LRU算法删除带有过期时间的key。
# allkeys-lru -> 根据LRU算法删除任何key。
# volatile-random -> 根据过期设置来随机删除key, 具备过期时间的key。 
# allkeys->random -> 无差别随机删, 任何一个key。 
# volatile-ttl -> 根据最近过期时间来删除（辅以TTL）, 这是对于有过期时间的key 
# noeviction -> 谁也不删，直接在写操作时返回错误。
maxmemory-policy volatile-lru

# 默认情况下，Redis是异步的把数据导出到磁盘上。这种模式在很多应用里已经足够好，但Redis进程
# 出问题或断电时可能造成一段时间的写操作丢失(这取决于配置的save指令)。
#
# AOF是一种提供了更可靠的替代持久化模式，例如使用默认的数据写入文件策略（参见后面的配置）
# 在遇到像服务器断电或单写情况下Redis自身进程出问题但操作系统仍正常运行等突发事件时，Redis
# 能只丢失1秒的写操作。
#
# AOF和RDB持久化能同时启动并且不会有问题。
# 如果AOF开启，那么在启动时Redis将加载AOF文件，它更能保证数据的可靠性。
appendonly no

# aof文件名
appendfilename "appendonly.aof"

# fsync() 系统调用告诉操作系统把数据写到磁盘上，而不是等更多的数据进入输出缓冲区。
# 有些操作系统会真的把数据马上刷到磁盘上；有些则会尽快去尝试这么做。
#
# Redis支持三种不同的模式：
#
# no：不要立刻刷，只有在操作系统需要刷的时候再刷。比较快。
# always：每次写操作都立刻写入到aof文件。慢，但是最安全。
# everysec：每秒写一次。折中方案。 
appendfsync everysec

# 如果AOF的同步策略设置成 "always" 或者 "everysec"，并且后台的存储进程（后台存储或写入AOF
# 日志）会产生很多磁盘I/O开销。某些Linux的配置下会使Redis因为 fsync()系统调用而阻塞很久。
# 注意，目前对这个情况还没有完美修正，甚至不同线程的 fsync() 会阻塞我们同步的write(2)调用。
#
# 为了缓解这个问题，可以用下面这个选项。它可以在 BGSAVE 或 BGREWRITEAOF 处理时阻止主进程进行fsync()。
# 
# 这就意味着如果有子进程在进行保存操作，那么Redis就处于"不可同步"的状态。
# 这实际上是说，在最差的情况下可能会丢掉30秒钟的日志数据。（默认Linux设定）
# 
# 如果你有延时问题把这个设置成"yes"，否则就保持"no"，这是保存持久数据的最安全的方式。
no-appendfsync-on-rewrite yes

# 自动重写AOF文件
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb

# AOF文件可能在尾部是不完整的（这跟system关闭有问题，尤其是mount ext4文件系统时
# 没有加上data=ordered选项。只会发生在os死时，redis自己死不会不完整）。
# 那redis重启时load进内存的时候就有问题了。
# 发生的时候，可以选择redis启动报错，并且通知用户和写日志，或者load尽量多正常的数据。
# 如果aof-load-truncated是yes，会自动发布一个log给客户端然后load（默认）。
# 如果是no，用户必须手动redis-check-aof修复AOF文件才可以。
# 注意，如果在读取的过程中，发现这个aof是损坏的，服务器也是会退出的，
# 这个选项仅仅用于当服务器尝试读取更多的数据但又找不到相应的数据时。
aof-load-truncated yes

# Lua 脚本的最大执行时间，毫秒为单位
lua-time-limit 5000

# Redis慢查询日志可以记录超过指定时间的查询
slowlog-log-slower-than 10000

# 这个长度没有限制。只是要主要会消耗内存。你可以通过 SLOWLOG RESET 来回收内存。
slowlog-max-len 128

# redis延时监控系统在运行时会采样一些操作，以便收集可能导致延时的数据根源。
# 通过 LATENCY命令 可以打印一些图样和获取一些报告，方便监控
# 这个系统仅仅记录那个执行时间大于或等于预定时间（毫秒）的操作, 
# 这个预定时间是通过latency-monitor-threshold配置来指定的，
# 当设置为0时，这个监控系统处于停止状态
latency-monitor-threshold 0

# Redis能通知 Pub/Sub 客户端关于键空间发生的事件，默认关闭
notify-keyspace-events ""

# 当hash只有少量的entry时，并且最大的entry所占空间没有超过指定的限制时，会用一种节省内存的
# 数据结构来编码。可以通过下面的指令来设定限制
hash-max-ziplist-entries 512
hash-max-ziplist-value 64

# 与hash似，数据元素较少的list，可以用另一种方式来编码从而节省大量空间。
# 这种特殊的方式只有在符合下面限制时才可以用
list-max-ziplist-entries 512
list-max-ziplist-value 64

# set有一种特殊编码的情况：当set数据全是十进制64位有符号整型数字构成的字符串时。
# 下面这个配置项就是用来设置set使用这种编码来节省内存的最大长度。
set-max-intset-entries 512

# 与hash和list相似，有序集合也可以用一种特别的编码方式来节省大量空间。
# 这种编码只适合长度和元素都小于下面限制的有序集合
zset-max-ziplist-entries 128
zset-max-ziplist-value 64

# HyperLogLog稀疏结构表示字节的限制。该限制包括
# 16个字节的头。当HyperLogLog使用稀疏结构表示
# 这些限制，它会被转换成密度表示。
# 值大于16000是完全没用的，因为在该点
# 密集的表示是更多的内存效率。
# 建议值是3000左右，以便具有的内存好处, 减少内存的消耗
hll-sparse-max-bytes 3000

# 启用哈希刷新，每100个CPU毫秒会拿出1个毫秒来刷新Redis的主哈希表（顶级键值映射表）
activerehashing yes

# 客户端的输出缓冲区的限制，可用于强制断开那些因为某种原因从服务器读取数据的速度不够快的客户端
client-output-buffer-limit normal 0 0 0
client-output-buffer-limit slave 256mb 64mb 60
client-output-buffer-limit pubsub 32mb 8mb 60

# 默认情况下，“hz”的被设定为10。提高该值将在Redis空闲时使用更多的CPU时，但同时当有多个key
# 同时到期会使Redis的反应更灵敏，以及超时可以更精确地处理
hz 10

# 当一个子进程重写AOF文件时，如果启用下面的选项，则文件每生成32M数据会被同步
aof-rewrite-incremental-fsync yes
```



##### 启动master

[root@rocket master]# ./redis-server ./redis.conf

[root@rocket master]# ps axu|grep redis

root   24000 0.1 0.7 137356 7440 ?    Ssl 23:28  0:00 ./redis-server *:7003

##### 使用客户端连接测试

[root@rocket master]# ./redis-cli -a 0234kz9*l -p 7003

127.0.0.1:7003> select 1

OK

127.0.0.1:7003[1]> set name zhangsan

OK

127.0.0.1:7003[1]> get name

"zhangsan"

127.0.0.1:7003[1]> quit

可以看到，redis启动成功并且可以开始读写数据。

#### 搭建redis slave

slave的配置和master基本一致，只需要修改相应的pidfile，端口，日志文件名，并配上master的地址和认证密码。

##### 配置文件redis_slave.conf（和redis master差异的地方）

\# pid file

pidfile /var/run/redis_slave.pid

 

\# 监听端口

port 8003

 

\# 指明日志文件名

logfile "./redis8003.log"

 

\# 设置当本机为slav服务时，设置master服务的IP地址及端口，在Redis启动时，它会自动从master进行数据同步

slaveof 127.0.0.1 7003

 

\# 当master服务设置了密码保护时，slav服务连接master的密码

masterauth 0234kz9*l

##### 启动slave并查看数据同步情况

[root@rocket slave]# ./redis-server ./redis_slave.conf

[root@rocket slave]# ./redis-cli -a 0234kz9*l -p 8003

127.0.0.1:8003> select 1

OK

127.0.0.1:8003[1]> get name

"zhangsan"

可以看到，master中设置的key-value已经成功同步过来。

 

### Redis容灾部署（哨兵Sentinel）

#### 哨兵的作用

1. 监控：监控主从是否正常
2. 通知：出现问题时，可以通知相关人员

3. 故障迁移：自动主从切换

4. 统一的配置管理：连接者询问sentinel取得主从的地址

#### Raft分布式算法

1. 主要用途：用于分布式系统，系统容错，以及选出领头羊

2. 作者：Diego Ongaro，毕业于哈佛

3. 目前用到这个算法的项目有：
   a. CoreOS : 见下面
   b. ectd : a distributed, consistent shared configuration
   c. LogCabin : 分布式存储系统
   d. redis sentinel : redis 的监控系统

#### Sentinel使用的Raft算法核心: 原则

1. 所有sentinel都有选举的领头羊的权利

2. 每个sentinel都会要求其他sentinel选举自己为领头羊(主要由发现redis客观下线的sentinel先发起选举)

3. 每个sentinel只有一次选举的机会

4. 采用先到先得的原则

5. 一旦加入到系统了，则不会自动清除(这一点很重要, why?)

6. 每个sentinel都有唯一的uid，不会因为重启而变更

7. 达到领头羊的条件是 N/2 + 1个sentinel选择了自己

8. 采用配置纪元，如果一次选举出现脑裂，则配置纪元会递增，进入下一次选举，所有sentinel都会处于统一配置纪元，以最新的为标准。

#### Raft算法核心: 可视图

Raft Visualization (算法演示)

![816350-20160114195231163-895776827](redis主从集群搭建及容灾部署(哨兵sentinel).assets/816350-20160114195231163-895776827.jpg)

#### Raft分布式算法的应用

coreos:云计算新星 Docker 正在以火箭般的速度发展，与它相关的生态圈也渐入佳境，CoreOS 就是其中之一。CoreOS 是一个全新的、面向数据中心设计的 Linux 操作系统，在2014年7月发布了首个稳定版本，目前已经完成了800万美元的A轮融资。

![816350-20160114195232053-933368955](redis主从集群搭建及容灾部署(哨兵sentinel).assets/816350-20160114195232053-933368955.jpg)

#### Sentinel实现Redis容灾部署

#### 三哨兵架构

[root@rocket sentinel]# tree

.

├── redis-cli

├── redis-sentinel

├── redis-server

├── sentinel1

│ ├── sentinel1.conf

│ └── sentinel1.log

├── sentinel2

│ ├── sentinel2.conf

│ └── sentinel2.log

└── sentinel3

  ├── sentinel3.conf

└── sentinel3.log

##### 哨兵一配置sentinel1.conf



```
# Example sentinel.conf

# port <sentinel-port>
port 26371

# 守护进程模式
daemonize yes

# 指明日志文件名
logfile "./sentinel1.log"

# 工作路径，sentinel一般指定/tmp比较简单
dir ./

# 哨兵监控这个master，在至少quorum个哨兵实例都认为master down后把master标记为odown
# （objective down客观down；相对应的存在sdown，subjective down，主观down）状态。
# slaves是自动发现，所以你没必要明确指定slaves。
sentinel monitor TestMaster 127.0.0.1 7003 1

# master或slave多长时间（默认30秒）不能使用后标记为s_down状态。
sentinel down-after-milliseconds TestMaster 1500

# 若sentinel在该配置值内未能完成failover操作（即故障时master/slave自动切换），则认为本次failover失败。
sentinel failover-timeout TestMaster 10000

# 设置master和slaves验证密码
sentinel auth-pass TestMaster 0234kz9*l

sentinel config-epoch TestMaster 15
sentinel leader-epoch TestMaster 8394

# #除了当前哨兵, 还有哪些在监控这个master的哨兵
sentinel known-sentinel TestMaster 127.0.0.1 26372 0aca3a57038e2907c8a07be2b3c0d15171e44da5
sentinel known-sentinel TestMaster 127.0.0.1 26373 ac1ef015411583d4b9f3d81cee830060b2f29862

sentinel current-epoch 8394
```



##### 哨兵二配置sentinel2.conf



```
# Example sentinel.conf

# port <sentinel-port>
port 26372

# 守护进程模式
daemonize yes

# 指明日志文件名
logfile "./sentinel2.log"

# 工作路径，sentinel一般指定/tmp比较简单
dir ./

# 哨兵监控这个master，在至少quorum个哨兵实例都认为master down后把master标记为odown
# （objective down客观down；相对应的存在sdown，subjective down，主观down）状态。
# slaves是自动发现，所以你没必要明确指定slaves。
sentinel monitor TestMaster 127.0.0.1 7003 1

# master或slave多长时间（默认30秒）不能使用后标记为s_down状态。
sentinel down-after-milliseconds TestMaster 1500

# 若sentinel在该配置值内未能完成failover操作（即故障时master/slave自动切换），则认为本次failover失败。
sentinel failover-timeout TestMaster 10000

# 设置master和slaves验证密码
sentinel auth-pass TestMaster 0234kz9*l

sentinel config-epoch TestMaster 15
sentinel leader-epoch TestMaster 8394

# #除了当前哨兵, 还有哪些在监控这个master的哨兵
sentinel known-sentinel TestMaster 127.0.0.1 26371 b780bbc20fdea6d3789637053600c5fc58dd0690
sentinel known-sentinel TestMaster 127.0.0.1 26373 ac1ef015411583d4b9f3d81cee830060b2f29862

sentinel current-epoch 8394
```



##### 哨兵三配置sentinel3.conf



```
# Example sentinel.conf

# port <sentinel-port>
port 26373

# 守护进程模式
daemonize yes

# 指明日志文件名
logfile "./sentinel3.log"

# 工作路径，sentinel一般指定/tmp比较简单
dir ./

# 哨兵监控这个master，在至少quorum个哨兵实例都认为master down后把master标记为odown
# （objective down客观down；相对应的存在sdown，subjective down，主观down）状态。
# slaves是自动发现，所以你没必要明确指定slaves。
sentinel monitor TestMaster 127.0.0.1 7003 1

# master或slave多长时间（默认30秒）不能使用后标记为s_down状态。
sentinel down-after-milliseconds TestMaster 1500

# 若sentinel在该配置值内未能完成failover操作（即故障时master/slave自动切换），则认为本次failover失败。
sentinel failover-timeout TestMaster 10000

# 设置master和slaves验证密码
sentinel auth-pass TestMaster 0234kz9*l

sentinel config-epoch TestMaster 15
sentinel leader-epoch TestMaster 8394

# #除了当前哨兵, 还有哪些在监控这个master的哨兵
sentinel known-sentinel TestMaster 127.0.0.1 26371 b780bbc20fdea6d3789637053600c5fc58dd0690
sentinel known-sentinel TestMaster 127.0.0.1 26372 0aca3a57038e2907c8a07be2b3c0d15171e44da5

sentinel current-epoch 8394
```



##### 在sentinel中查看所监控的master和slave

[root@rocket sentinel]# ./redis-cli -p 26371

127.0.0.1:26371> SENTINEL masters

1) 1) "name"

  2) "TestMaster"

  3) "ip"

  4) "127.0.0.1"

  5) "port"

  6) "7003"

  7) "runid"

  8) "de0896e3799706bda49cb92048776e233841e25d"

  9) "flags"

  10) "master"

127.0.0.1:26371> SENTINEL slaves TestMaster

1) 1) "name"

  2) "127.0.0.1:8003"

  3) "ip"

  4) "127.0.0.1"

  5) "port"

  6) "8003"

  7) "runid"

  8) "9b2a75596c828d6d605cc8529e96edcf951de25d"

  9) "flags"

  10) "slave"

查看当前的master

127.0.0.1:26371> SENTINEL get-master-addr-by-name TestMaster

1) "127.0.0.1"

2) "7003"

##### 停掉master，查看容灾切换情况

[root@rocket master]# ps axu|grep redis

root   24000 0.2 0.9 137356 9556 ?    Ssl Jan12  0:30 ./redis-server *:7003   

root   24240 0.2 0.7 137356 7504 ?    Ssl Jan12  0:26 ./redis-server *:8003      

root   24873 0.3 0.7 137356 7524 ?    Ssl 01:31  0:25 ../redis-sentinel *:26371    

root   24971 0.3 0.7 137356 7524 ?    Ssl 01:33  0:25 ../redis-sentinel *:26372    

root   24981 0.3 0.7 137356 7520 ?    Ssl 01:33  0:25 ../redis-sentinel *:26373    

root   24995 0.0 0.5 19404 5080 pts/2  S+  01:34  0:00 ./redis-cli -p 26371

root   25969 0.0 0.0 103252  844 pts/0  S+  03:33  0:00 grep redis

[root@rocket master]# kill -QUIT 24000

 

再查看master，发现已经master已经切换为原来的slave

127.0.0.1:26371> SENTINEL get-master-addr-by-name TestMaster

1) "127.0.0.1"

2) "8003"

 

查看sentinel日志

![816350-20160114195232460-1530327945](redis主从集群搭建及容灾部署(哨兵sentinel).assets/816350-20160114195232460-1530327945.jpg)

启动原来的master，发现变成了slave

[root@rocket master]# ./redis-server ./redis.conf

127.0.0.1:26371> SENTINEL slaves TestMaster

1) 1) "name"

  2) "127.0.0.1:7003"

  3) "ip"

  4) "127.0.0.1"

  5) "port"

6) "7003"

发现主从发生了对调。

#### sentinel自动发现

每个Sentinel 都订阅了被它监视的所有主服务器和从服务器的__sentinel__:hello 频道，查找之前未出现过的sentinel（looking for unknown sentinels）。当一个Sentinel 发现一个新的Sentinel 时，它会将新的Sentinel 添加到一个列表中，这个列表保存了Sentinel 已知的，监视同一个主服务器的所有其他Sentinel。

127.0.0.1:7003[1]> SUBSCRIBE __sentinel__:hello

Reading messages... (press Ctrl-C to quit)

1) "subscribe"

2) "__sentinel__:hello"

3) (integer) 1

1) "message"

2) "__sentinel__:hello"

3) "127.0.0.1,26373,7d919ccfb5752caf6812da2d0dba4ed0a528ceda,8436,TestMaster,127.0.0.1,7003,8436"

1) "message"

2) "__sentinel__:hello"

3) "127.0.0.1,26372,9eda79e93e6d1aa4541564ac28e3dc899d39e43b,8436,TestMaster,127.0.0.1,7003,8436"

1) "message"

2) "__sentinel__:hello"

3) "127.0.0.1,26371,8d63bebfbca9e1205a43bc13b52079de6015758e,8436,TestMaster,127.0.0.1,7003,8436"

### Redis常见问题

最大内存问题：要设置好最大内存，以防不停的申请内存，造成系统内存都被用完。

Fork进程问题：'vm.overcommit_memory = 1'这一个选项要加到系统的配置中，防止fork因内存不足而失败。

密码问题：需要设置复杂一些，防止暴力破解。