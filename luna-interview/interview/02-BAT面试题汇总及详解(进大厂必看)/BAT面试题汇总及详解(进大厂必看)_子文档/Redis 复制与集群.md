# Redis 复制与集群



## 复制

为提高高可用性，排除单点故障，redis支持主从复制功能。
其整体结构是一个有向无环图。

### 同步方式

分为两种：

- 全同步
  全同步是第一次从机连主机是进行的同步，主机会生成一个RDB文件给从机，然后从机加载该文件。
  并且如果从机掉线时间很长时也会触发这个同步，掉线时间短时使用另外的策略
- 部分同步
  当主机收到修改命令之后会把命令发给从机进行部分同步。
  这里会有一个缓存区，主要是用来，如果有从机掉线，再次连接的时候会优先使用缓存区中的数据进行同步，是在不行才使用全同步
- 同步过程

```
public void sync(String syncCommond){
    if(valid(masterid, offset)){
        // 表示能够通过部分同步找回
        sendCommandCache();
    }else{
        // 进行全同步
        generateRDB();
        transferRDB();
    }
}
```

### 问题

单主单从的情况下，读写分离很好，但是如果万一主挂了，这样就无法写了
或者单主多从时，如果主挂了，也无法进行同步了。这样就需要选举出一个新的主来作为主机。

### 主从切换

使用Sentinel，其包含如下功能：

- 监控
  监控服务器节点
- 提醒
  当监控的节点出现问题时，可以通过api通知其他应用等
- 故障转移
  当主挂掉时会选举新的从服务器为主服务器，代替原来主服务器的地位

## 集群

主从为了提高可用性，防止单点故障。
集群则是为了伸缩性了

### key映射到节点的算法

对于集群的情况，经常会涉及一个key存在哪个节点中去。
一般有hash/mod的方式，但是有着增删节点重算的致命问题。
另外还有一致性hash算法，memcache客户端的算法就是这种算法，分成一个2^31的槽圆环，对节点进行hash,对key进行hash,选择顺时针离key最近的节点保存key-value，这样可以最少的影响原数据，还可以具有hash的平衡性等好的优点。
Redis使用的是另外一种：
内置16384 个hash槽，把这些槽大致均匀的分到节点中，每个节点都记录哪些槽给了自己以及给了别人。然后对key算hash/16384, 放到对应的节点中，如果新的节点来了，那么重新分割他一些槽同时更新各个节点中的记录，并且把槽中的记录同时也发给新的节点。

### 握手

节点信息的结构

```
class Node{
    private long time; // 创建时间
    private String name; // 名称
    private int flag;   // 标示主从，或者在线状态
    private String ip;
    private int port;
    private ClusterState clusterState; // 集群信息
}

class ClusterState{
    private Node myself;
    private int state;
    private int size; // 知道包含一个槽的节点数量
    private Dictionary nodes;  // 集群节点名单
}
```

size为0， 则state为集群下线。
握手就是节点加入到已有集群的一种方式，主要是为了丰富nodeList。握手的过程如下：

- B向A发送CLUSTER MEET
- A为B创建Node结构，并且加到clusterState.nodes中
- A向B发送MEET消息，B再未A创建Node,加入自己
- 然后B向A发送PONG,A向B返回PING完成握手

### 集群行为

当集群接收到请求之后：

- 一个节点接收到了请求，会检查是否自己的槽，不是则返回MOVED，告诉客户端去哪个槽
- 重新分片是由客户端去做的， 把一个槽的所有键值转到另外的槽
- 如果正在进行转移，客户请求没有命中，则会返回ask消息，让客户端去另外的节点去找
- 集群中如果有主从，那么从节点复制主节点，主几点下线之后，从节点升级代替主节点