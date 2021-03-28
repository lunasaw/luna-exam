# redis集群实现（四） 数据的和槽位的分配



不知道有没有人思考过redis是如何把数据分配到集群中的每一个节点的，可能有人会说，把集群中的每一个节点编号，先放第一个节点，放满了就放第二个节点，以此类推。。如果真的是这样的话，服务器的利用率和性能就太低了，因为先放第一个，其他的服务器节点就闲置下来了，单个节点的压力就会非常的大，其实就相当于退化成为了单机服务器，从而违背了集群发挥每一个节点的性能的初衷。

在redis官方给出的集群方案中，数据的分配是按照槽位来进行分配的，每一个数据的键被哈希函数映射到一个槽位，redis-3.0.0规定一共有16384个槽位，当然这个可以根据用户的喜好进行配置。当用户put或者是get一个数据的时候，首先会查找这个数据对应的槽位是多少，然后查找对应的节点，然后才把数据放入这个节点。这样就做到了把数据均匀的分配到集群中的每一个节点上，从而做到了每一个节点的负载均衡，充分发挥了集群的威力。

在redis中，把一个key-value键值对放入的最简单的方式就是set key value，如下所示：



```cpp
127.0.0.1:7000> set key value



-> Redirected to slot [12539] located at 192.168.39.153:7002



OK



192.168.39.153:7002> get key



"value"



192.168.39.153:7002> 
```



可以看出，当我们把key的值设置成为value的时候，客户端被重定向到了另一个节点192.168.39.153:7002，这是因为key对应的槽位是12359，所以我们的key-value就被放到了槽12359对应的节点，192.168.39.153:7002了。接下来，我们来看看redis是怎么把一个key-value键值对映射成槽，然后又如何存放进集群中的。

首先在redis.c文件里定义了客户端命令和函数的对应关系，



```cpp
	struct redisCommand redisCommandTable[] = {



	————————————————————————



	{"set",setCommand,-3,"wm",0,NULL,1,1,1,0,0},



	————————————————————————
```



可以看出，set命令会执行setCommand函数进行解析，继续进入setCommand函数查看



```cpp
void setCommand(redisClient *c) {                                              



    int j;                                                                     



    robj *expire = NULL;                                                       



    int unit = UNIT_SECONDS;                                                   



    int flags = REDIS_SET_NO_FLAGS;                                            



                                                               



    ————————————————————————                



                          



    // 对value编码                                                    



    c->argv[2] = tryObjectEncoding(c->argv[2]); 



    //真正执行set命令的地方                                                                                                                                                     



    setGenericCommand(c,flags,c->argv[1],c->argv[2],expire,unit,NULL,NULL);    



}     
```



继续进入setGenericCommand函数



```cpp
void setGenericCommand(redisClient *c, int flags, robj *key, robj *val, robj *expire, int unit, robj *ok_reply, robj *abort_reply) {



    //参数检查和过期时间的检查



    ————————————————————————    



    //在数据库里设置key  value    



    setKey(c->db,key,val);



    //设置完成以后的时间通知



    ————————————————————————        



}
```



接着看数据库的setKey函数



```cpp
void setKey(redisDb *db, robj *key, robj *val) {



 



    // 添加或覆写数据库中的键值对



    if (lookupKeyWrite(db,key) == NULL) {



        dbAdd(db,key,val);



    } else {



        dbOverwrite(db,key,val);



    }



------------------------------------------------------------------------------------------
```



当没有在数据库中发现key的时候，我们需要执行dbAdd函数把key-value添加到数据库里。



```cpp
void dbAdd(redisDb *db, robj *key, robj *val) {



 



    // 赋值key的名字



    sds copy = sdsdup(key->ptr);



 



    // 添加键值对到字典中



    int retval = dictAdd(db->dict, copy, val);



 



    // 如果键已经存在，那么停止



    redisAssertWithInfo(NULL,key,retval == REDIS_OK);



 



    // 如果开启了集群模式，就把键保存到槽里面



    if (server.cluster_enabled) slotToKeyAdd(key);



 }
```



继续进入slotToKeyAdd函数



```cpp
//把键key添加到槽里边



void slotToKeyAdd(robj *key) {



 



    // 通过字符串key计算出键对应的槽



    unsigned int hashslot = keyHashSlot(key->ptr,sdslen(key->ptr));



 



    // 将槽 slot 作为分数，键作为成员，添加到 slots_to_keys 跳跃表里面



    zslInsert(server.cluster->slots_to_keys,hashslot,key);                                                                                                   



    incrRefCount(key);



}
```



keyHashSlot是一个哈希函数，通过key映射到一个0-16384的整数，我们来看一下实现



```cpp
unsigned int keyHashSlot(char *key, int keylen) {                                                                                                           



    //start 和end



    int s, e; 



 



    for (s = 0; s < keylen; s++) 



        if (key[s] == '{') break;



 



    /* 没有发现和{对应的}，就直接哈希整个字符串 */



    if (s == keylen) return crc16(key,keylen) & 0x3FFF;



 



    /* 如果发现了{，看看是不是又}匹配 */



    for (e = s+1; e < keylen; e++) 



        if (key[e] == '}') break;



 



    /* 如果没有发现}，哈希函数就计算整个字符串. */



    if (e == keylen || e == s+1) return crc16(key,keylen) & 0x3FFF;



 



    /*如果{}在我们的两边，哈希中间的字符 */



    return crc16(key+s+1,e-s-1) & 0x3FFF;



}
```



计算key字符串对应的映射值，redis采用了crc16函数然后与0x3FFF取低16位的方法。crc16以及md5都是比较常用的根据key均匀的分配的函数，就这样，用户传入的一个key我们就映射到一个槽上，然后经过gossip协议，周期性的和集群中的其他节点交换信息，最终整个集群都会知道key在哪一个槽上。