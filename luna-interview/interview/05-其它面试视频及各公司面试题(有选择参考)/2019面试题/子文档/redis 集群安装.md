# redis 集群安装



redis集群安装

1. 下载redis源码

2. 解压并进入解压后的文件夹redis内

3. make，生成一系列的文件（mkreleasehdr.sh, redis-benchmark, redis-check-aof, redis-check-rdb, redis-cli, redis-sentinel, redis-server, redis-server, redis-trib.rb)

要部署集群，至少需要6个节点，这里使用了六台电脑centos系统，每台电脑上运行一个redis实例，端口为6379 。（如果没有6台，则可以在一台电脑上运行多个redis实例，设置不同的端口即可）

4. 设置配置文件 src/redis.conf

port 6379　　# 绑定端口，可以改为其他端口

daemon yes  # 后台运行

```
cluster-enabled yes #集群
cluster-config-file nodes.conf　　# 组成集群时自动生成的配置文件，用户不需修改这个文件
cluster-node-timeout 5000　　　　# 超时（认为宕机）
appendonly yes　　　　　　　　　　# 每次更新或插入新数据时写入日志文件
bind
```

bind <本机ip>  # 绑定ip，目的是为了让其他节点知道，如果在一台电脑上运行6个redis实例，则这里可以设为127.0.0.1（没有实测）

5. 安装ruby，rubygems，可以从网上下载源码安装或者 yum install

6. 执行指令

```
gem install redis
```

这是因为集群启动是使用ruby脚本，如果出现安装错误比如说没有zlib或者openssl，则需要yum install zlib zlib-devel openssl openssl-devel，然后需要重新编译安装ruby（否则仍然提示错误）。

7. 将redis文件夹复制到其他5台电脑上，并在6台电脑上依次启动6个redis实例

8. 执行指令

```
./redis-trib.rb create --replicas 1 127.0.0.1:7000 127.0.0.1:7001 \
127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005
```

将127.0.0.1:<port> 替换为实际的ip:port

如果出现redis waiting for the cluster to join.... 一直阻塞，则说明有的节点端口无法通信，可以开放所绑定的端口（以及 绑定端口+10000 得到的另一个端口，这个端口redis也使用）

如果出现 sorry, can't connect to node xxx.xxx.xx.xx，则很可能是防火墙没有开放端口

进行如下设置即可

开启端口

 

firewall-cmd --zone=public --add-port=6379/tcp --permanent

 firewall-cmd --zone=public --add-port=16379/tcp --permanent

命令含义：

 

--zone #作用域

 

--add-port=80/tcp  #添加端口，格式为：端口/通讯协议

 

--permanent  #永久生效，没有此参数重启后失效

 

重启防火墙

 

firewall-cmd --reload

 

9. 如果需要关闭集群，则需要在每台电脑上分别关闭

```
ps -aux | grep redis
```

得到redis的pid，然后

```
kill -9 <pid>
```

此时如果再次执行上面 7，8 步骤重启集群，则会出现类似

Node 192.168.10.219:6379 is not empty. Either the node already knows other nodes (check with CLUSTER NODES) or contains some key in database 0.

的错误，此时需要将redis启动目录中的appendonly.aof，nodes.conf，dump.rdb文件删除后再执行 7，8步骤方可

 

10. 最后登录集群

```
./redis-cli -h 192.168.10.10 -c -p 6379
```

11. 如果给集群设置密码，则注意在集群搭建完成前不要设置密码，在搭建之后通过客户端设置密码，如下（注意分别登录每个节点并设置）



```
config set masterauth <p1>
config set requirepass <p2>
auth <p2>
config rewrite
```



下次登录可使用

```
./redis-cli -h <ip> -c -p 6379 -a <p2>
```

或者使用10步骤登录后，再使用 auth <p2>验证身份

 

### redis集群管理工具

redis-trib.rb是redis官方推出的管理redis集群的工具，集成在redis的源码src目录下，是基于redis提供的集群命令封装成简单、便捷、实用的操作工具。redis-trib.rb是redis作者用ruby完成的。

先从redis-trib.rb的help信息，看下redis-trib.rb提供了哪些功能。
redis-trib
选项：
 create     host1:port1 ... hostN:portN 创建集群，只有大于等于3个节点才能创建集群
           --replicas                每个节点需要几个slave
 check      host:port               检查集群，只需要选择一个集群中的一个节点即可
 info       host:port                查看集群信息，只需要选择一个集群中的一个节点即可
 fix        host:port                修复集群，目前fix命令能修复两种异常，一种是集群有处
                                 于迁移中的slot的节点，一种是slot未完全分配的异常
           --timeout                超时时间
 reshard     host:port               在线迁移slot
          --from          需要从哪些源节点上迁移slot，可从多个源节点完成迁移，
                         以逗号隔开，传递的是节点的node id，
                         还可以直接传递--from all，这样源节点就是集群的所有节点，
                         不传递该参数的话，则会在迁移过程中提示用户输入。
         --to           Slot需要迁移的目的节点的node id，目的节点只能填写一个，
                      不传递该参数的话，则会在迁移过程中提示用户输入。
         --slots         需要迁移的slot数量，不传递该参数的话，则会在迁移过程中提示用户输入。
         --yes            设置该参数，可以在打印执行reshard计划的时候，提示用户输入yes确认后再执行reshard
         --timeout        设置migrate命令的超时时间。
         --pipeline        定义cluster getkeysinslot命令一次取出的key数量，
                        不传的话使用默认值为10。
 rebalance    host:port     平衡集群节点slot数量，这个是必传参数，
                        用来从一个节点获取整个集群信息，相当于获取集群信息的入口
         --weight         节点的权重，格式为node_id=weight，如果需要为多个节点分配权
                        重的话，需要添加多个--weight 参数，
                        即--weight b31e3a2e=5 --weight 60b8e3a1=5，node_id可为节点
                        名称的前缀，只要保证前缀位数能唯一区分该节点即可。
                        没有传递–weight的节点的权重默认为1。
         --auto-weights        这个参数在rebalance流程中并未用到
         --use-empty-masters     rebalance是否考虑没有节点的master，
                        默认没有分配slot节点的master是不参与rebalance的，
                        设置--use-empty-masters可以让没有分配slot的节点参与rebalance
         --timeout        设置migrate命令的超时时间
         --simulate       设置该参数，可以模拟rebalance操作，提示用户会迁移哪些slots，
                        而不会真正执行迁移操作
         --pipeline        与reshar的pipeline参数一样，定义cluster getkeysinslot命令一次
                        取出的key数量，不传的话使用默认值为10。
         --threshold       只有节点需要迁移的slot阈值超过threshold，才会执行rebalance操作
 add-node    new_host:new_port existing_host:existing_port 将新节点加入集群，
                        命令可以将新节点加入集群，节点可以为master，也可
                        以为某个master节点的slave
         --slave         设置该参数，则新节点以slave的角色加入集群
         --master-id      这个参数需要设置了--slave才能生效，--master-id用来指定新节点的
                        master节点。如果不设置该参数，则会随机为节点选择master节点。
 del-node    host:port node_id      从集群中删除节点，
 set-timeout   host:port milliseconds    设置集群节点间心跳连接的超时时间，单位是毫秒
 call      host:port command arg arg .. arg 在集群全部节点上执行命令，
                       call命令也是需要通过集群的一个节点地址，连上整个集群，
                       然后在集群的每个节点执行该命令。
 import     host:port    将外部redis节点数据导入集群，
                       import命令更适合离线的把外部redis数据导入，
                        以slave的方式连接redis节点去同步节点数据应该是更好的方式。
         --from          源节点
         --copy            拷贝
         --replace          替换
 help      打印帮助信息
 
**示例:**
*** 创建Redis集群**
$ redis-trib.rb create --replicas 1 172.16.0.7:6379 172.16.0.7:6380 172.16.0.7:6381 172.16.0.7:6382 172.16.0.7:6383 172.16.0.7:6384
\>>> Creating cluster
\>>> Performing hash slots allocation on 6 nodes...
Using 3 masters:
172.16.0.7:6379
172.16.0.7:6380
172.16.0.7:6381
Adding replica 172.16.0.7:6382 to 172.16.0.7:6379
Adding replica 172.16.0.7:6383 to 172.16.0.7:6380
Adding replica 172.16.0.7:6384 to 172.16.0.7:6381
M: 8b3809e6e5591dcfec067216906a25d7cd482598 172.16.0.7:6379
  slots:0-5460 (5461 slots) master
M: a1ac1fc64549ca15eb889395c46d001162bf2aa6 172.16.0.7:6380
  slots:5461-10922 (5462 slots) master
M: 01a0cccbb8b15e00469d1cafd2eedfb6bd8bd2b5 172.16.0.7:6381
  slots:10923-16383 (5461 slots) master
S: 7d033c0df5b7db4f872afa94566fb061c4ebe16a 172.16.0.7:6382
  replicates 8b3809e6e5591dcfec067216906a25d7cd482598
S: 72b5baa2dbdb315fef604ea8b6deb0143372ae1b 172.16.0.7:6383
  replicates a1ac1fc64549ca15eb889395c46d001162bf2aa6
S: 8641d66fce1f5cc982ef7649d53a1eba2fa9b7c5 172.16.0.7:6384
  replicates 01a0cccbb8b15e00469d1cafd2eedfb6bd8bd2b5
Can I set the above configuration? (type 'yes' to accept): yes
\>>> Nodes configuration updated
\>>> Assign a different config epoch to each node
\>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join......
\>>> Performing Cluster Check (using node 172.16.0.7:6379)
M: 8b3809e6e5591dcfec067216906a25d7cd482598 172.16.0.7:6379
  slots:0-5460 (5461 slots) master
M: a1ac1fc64549ca15eb889395c46d001162bf2aa6 172.16.0.7:6380
  slots:5461-10922 (5462 slots) master
M: 01a0cccbb8b15e00469d1cafd2eedfb6bd8bd2b5 172.16.0.7:6381
  slots:10923-16383 (5461 slots) master
M: 7d033c0df5b7db4f872afa94566fb061c4ebe16a 172.16.0.7:6382
  slots: (0 slots) master
  replicates 8b3809e6e5591dcfec067216906a25d7cd482598
M: 72b5baa2dbdb315fef604ea8b6deb0143372ae1b 172.16.0.7:6383
  slots: (0 slots) master
  replicates a1ac1fc64549ca15eb889395c46d001162bf2aa6
M: 8641d66fce1f5cc982ef7649d53a1eba2fa9b7c5 172.16.0.7:6384
  slots: (0 slots) master
  replicates 01a0cccbb8b15e00469d1cafd2eedfb6bd8bd2b5
[OK] All nodes agree about slots configuration.
\>>> Check for open slots...
\>>> Check slots coverage...
[OK] All 16384 slots covered.

*** 检查Redis集群**
$ redis-trib.rb check 172.16.0.7:6379
\>>> Performing Cluster Check (using node 172.16.0.7:6379)
M: 8b3809e6e5591dcfec067216906a25d7cd482598 172.16.0.7:6379
  slots:0-5460 (5461 slots) master
  1 additional replica(s)
S: 8641d66fce1f5cc982ef7649d53a1eba2fa9b7c5 172.16.0.7:6384
  slots: (0 slots) slave
  replicates 01a0cccbb8b15e00469d1cafd2eedfb6bd8bd2b5
S: 7d033c0df5b7db4f872afa94566fb061c4ebe16a 172.16.0.7:6382
  slots: (0 slots) slave
  replicates 8b3809e6e5591dcfec067216906a25d7cd482598
M: a1ac1fc64549ca15eb889395c46d001162bf2aa6 172.16.0.7:6380
  slots:5461-10922 (5462 slots) master
  1 additional replica(s)
S: 72b5baa2dbdb315fef604ea8b6deb0143372ae1b 172.16.0.7:6383
  slots: (0 slots) slave
  replicates a1ac1fc64549ca15eb889395c46d001162bf2aa6
M: 01a0cccbb8b15e00469d1cafd2eedfb6bd8bd2b5 172.16.0.7:6381
  slots:10923-16383 (5461 slots) master
  1 additional replica(s)
[OK] All nodes agree about slots configuration.

*** 查看Redis集群信息**
$ redis-trib.rb info 172.16.0.7:6379
172.16.0.7:6379 (8b3809e6...) -> 0 keys | 5461 slots | 1 slaves.
172.16.0.7:6380 (a1ac1fc6...) -> 0 keys | 5462 slots | 1 slaves.
172.16.0.7:6381 (01a0cccb...) -> 0 keys | 5461 slots | 1 slaves.
[OK] 0 keys in 3 masters.
0.00 keys per slot on average.

*** 添加Redis节点**
$ redis-trib.rb add-node 172.16.0.7:6385 172.16.0.7:6379
\>>> Adding node 172.16.0.7:6385 to cluster 172.16.0.7:6379
\>>> Performing Cluster Check (using node 172.16.0.7:6379)
M: 328fe71182234df381fb4845e3f4bb4c3288320d 172.16.0.7:6379
  slots:0-5460 (5461 slots) master
  1 additional replica(s)
S: da6149e83031f8e1d790784b8650732365134dee 172.16.0.7:6383
  slots: (0 slots) slave
  replicates 237d335822d545e97723224dc965bb986586c98e
M: 6d7ad892ceb964063db08fbb6c1d7b0f6226418c 172.16.0.7:6381
  slots:10923-16383 (5461 slots) master
  1 additional replica(s)
M: 237d335822d545e97723224dc965bb986586c98e 172.16.0.7:6380
  slots:5461-10922 (5462 slots) master
  1 additional replica(s)
S: 23c093add21934a6b3eeba18897f536947c89f42 172.16.0.7:6384
  slots: (0 slots) slave
  replicates 6d7ad892ceb964063db08fbb6c1d7b0f6226418c
S: 4341c643d2b785019bc940e1ffcf5e44b2343e09 172.16.0.7:6382
  slots: (0 slots) slave
  replicates 328fe71182234df381fb4845e3f4bb4c3288320d
[OK] All nodes agree about slots configuration.
\>>> Check for open slots...
\>>> Check slots coverage...
[OK] All 16384 slots covered.
\>>> Send CLUSTER MEET to node 172.16.0.7:6385 to make it join the cluster.
[OK] New node added correctly.

*** 添加Redis节点**

```
redis−trib.rbadd−node−−slave−−master−id9d6229abfea1fa5a3faa639a7b54ac3a7810f9ff172.16.0.7:6386172.16.0.7:6379>>>Addingnode172.16.0.7:6386tocluster172.16.0.7:6379>>>PerformingClusterCheck(usingnode172.16.0.7:6379)M:328fe71182234df381fb4845e3f4bb4c3288320d172.16.0.7:6379slots:0−5460(5461slots)master1additionalreplica(s)S:da6149e83031f8e1d790784b8650732365134dee172.16.0.7:6383slots:(0slots)slavereplicates237d335822d545e97723224dc965bb986586c98eM:6d7ad892ceb964063db08fbb6c1d7b0f6226418c172.16.0.7:6381slots:10923−16383(5461slots)master1additionalreplica(s)M:9d6229abfea1fa5a3faa639a7b54ac3a7810f9ff172.16.0.7:6385slots:(0slots)master0additionalreplica(s)M:237d335822d545e97723224dc965bb986586c98e172.16.0.7:6380slots:5461−10922(5462slots)master1additionalreplica(s)S:23c093add21934a6b3eeba18897f536947c89f42172.16.0.7:6384slots:(0slots)slavereplicates6d7ad892ceb964063db08fbb6c1d7b0f6226418cS:4341c643d2b785019bc940e1ffcf5e44b2343e09172.16.0.7:6382slots:(0slots)slavereplicates328fe71182234df381fb4845e3f4bb4c3288320d[OK]Allnodesagreeaboutslotsconfiguration.>>>Checkforopenslots...>>>Checkslotscoverage...[OK]All16384slotscovered.>>>SendCLUSTERMEETtonode172.16.0.7:6386tomakeitjointhecluster.Waitingfortheclustertojoin.>>>Configurenodeasreplicaof172.16.0.7:6385.[OK]Newnodeaddedcorrectly.2.6平衡集群节点slot数量
```

redis-trib.rb rebalance --use-empty-masters 172.16.0.7:6379
\>>> Performing Cluster Check (using node 172.16.0.7:6379)
[OK] All nodes agree about slots configuration.
\>>> Check for open slots...
\>>> Check slots coverage...
[OK] All 16384 slots covered.
\>>> Rebalancing across 4 nodes. Total weight = 4
Moving 1366 slots from 172.16.0.7:6380 to 172.16.0.7:6385
\##########################################################################
Moving 1365 slots from 172.16.0.7:6381 to 172.16.0.7:6385
\##########################################################################
Moving 1365 slots from 172.16.0.7:6379 to 172.16.0.7:6385
\##########################################################################

*** 在线迁移Slot**
$ redis-trib.rb reshard --from 9d6229abfea1fa5a3faa639a7b54ac3a7810f9ff --to 328fe71182234df381fb4845e3f4bb4c3288320d --slots 4096 172.16.0.7:6379

*** 从集群中删除节点**
$ redis-trib.rb del-node 172.16.0.7:6379 9d6229abfea1fa5a3faa639a7b54ac3a7810f9ff
\>>> Removing node 9d6229abfea1fa5a3faa639a7b54ac3a7810f9ff from cluster 172.16.0.7:6379
\>>> Sending CLUSTER FORGET messages to the cluster...
\>>> SHUTDOWN the node.

注意：必须确保数据迁移后，才能从集群中删除节点，否则报以下错误：
[ERR] Node 172.16.0.7:6385 is not empty! Reshard data away and try again.

