# kafka与传统的消息中间件对比



**RabbitMQ和kafka从几个角度简单的对比**

业界对于消息的传递有多种方案和产品，本文就比较有代表性的两个MQ(rabbitMQ,kafka)进行阐述和做简单的对比，

在应用场景方面，

RabbitMQ,遵循AMQP协议，由内在高并发的erlanng语言开发，用在实时的对可靠性要求比较高的消息传递上。

kafka是Linkedin于2010年12月份开源的消息发布订阅系统,它主要用于处理活跃的流式数据,大数据量的数据处理上。

1)在架构模型方面，

RabbitMQ遵循AMQP协议，RabbitMQ的broker由Exchange,Binding,queue组成，其中exchange和binding组成了消息的路由键；客户端Producer通过连接channel和server进行通信，Consumer从queue获取消息进行消费（长连接，queue有消息会推送到consumer端，consumer循环从输入流读取数据）。rabbitMQ以broker为中心；有消息的确认机制。

kafka遵从一般的MQ结构，producer，broker，consumer，以consumer为中心，消息的消费信息保存的客户端consumer上，consumer根据消费的点，从broker上批量pull数据；无消息确认机制。

2)在吞吐量，

kafka具有高的吞吐量，内部采用消息的批量处理，zero-copy机制，数据的存储和获取是本地磁盘顺序批量操作，具有O(1)的复杂度，消息处理的效率很高。

rabbitMQ在吞吐量方面稍逊于kafka，他们的出发点不一样，rabbitMQ支持对消息的可靠的传递，支持事务，不支持批量的操作；基于存储的可靠性的要求存储可以采用内存或者硬盘。

3)在可用性方面，

rabbitMQ支持miror的queue，主queue失效，miror queue接管。

kafka的broker支持主备模式。

4)在集群负载均衡方面，

kafka采用zookeeper对集群中的broker、consumer进行管理，可以注册topic到zookeeper上；通过zookeeper的协调机制，producer保存对应topic的broker信息，可以随机或者轮询发送到broker上；并且producer可以基于语义指定分片，消息发送到broker的某分片上。

rabbitMQ的负载均衡需要单独的loadbalancer进行支持。



### Kafka 对比 ActiveMQ

 

Kafka 是LinkedIn 开发的一个高性能、分布式的消息系统，广泛用于日志收集、流式数据处理、在线和离线消息分发等场景。虽然不是作为传统的MQ来设计，在大部分情况，Kafaka 也可以代替原先ActiveMQ 等传统的消息系统。

Kafka 将消息流按Topic 组织，保存消息的服务器称为Broker，消费者可以订阅一个或者多个Topic。为了均衡负载，一个Topic 的消息又可以划分到多个分区(Partition)，分区越多，Kafka并行能力和吞吐量越高。

Kafka 集群需要zookeeper 支持来实现集群，最新的kafka 发行包中已经包含了zookeeper，部署的时候可以在一台服务器上同时启动一个zookeeper Server 和 一个Kafka Server，也可以使用已有的其他zookeeper集群。

和传统的MQ不同，消费者需要自己保留一个offset，从kafka 获取消息时，只拉去当前offset 以后的消息。Kafka 的scala/java 版的client 已经实现了这部分的逻辑，将offset 保存到zookeeper 上。每个消费者可以选择一个id，同样id 的消费者对于同一条消息只会收到一次。一个Topic 的消费者如果都使用相同的id，就是传统的 Queue；如果每个消费者都使用不同的id, 就是传统的pub-sub.

　　ActiveMQ和Kafka，前者完全实现了JMS的规范，后者看上去有一些“野路子”，并没有纠结于JMS规范，剑走偏锋的设计了另一套吞吐非常高的分布式发布-订阅消息系统，目前非常流行。接下来我们结合三个点（消息安全性，服务器的稳定性容错性以及吞吐量）来分别谈谈这两个消息中间件。今天我们谈Kafka，[ActiveMQ的文章在此。](http://www.liubey.org/mq-activemq/)

　　**01 性能怪兽Kafka**
　　Kafka是LinkedIn开源的分布式发布-订阅消息系统，目前归属于Apache定级项目。”Apache Kafka is publish-subscribe messaging rethought as a distributed commit log.”，官网首页的一句话高度概括其职责。Kafka并没有遵守JMS规范，他只用文件系统来管理消息的生命周期。Kafka的设计目标是：
（1）以时间复杂度为O(1)的方式提供消息持久化能力，即使对TB级以上数据也能保证常数时间复杂度的访问性能。
（2）高吞吐率。即使在非常廉价的商用机器上也能做到单机支持每秒100K条以上消息的传输。
（3）支持Kafka Server间的消息分区，及分布式消费，同时保证每个Partition内的消息顺序传输。
（4）同时支持离线数据处理和实时数据处理。
（5）Scale out：支持在线水平扩展。
　　所以，不像AMQ，Kafka从设计开始极为高可用为目的，天然HA。broker支持集群，消息亦支持负载均衡，还有副本机制。同样，Kafka也是使用Zookeeper管理集群节点信息，包括consumer的消费信息也是保存在zk中，下面我们分话题来谈：
**1）消息的安全性**
Kafka集群中的Leader负责某一topic的某一partition的消息的读写，理论上consumer和producer只与该Leader 节点打交道，一个集群里的某一broker即是Leader的同时也可以担当某一partition的follower，即Replica。Kafka分配Replica的算法如下：
（1）将所有Broker（假设共n个Broker）和待分配的Partition排序
（2）将第i个Partition分配到第（i mod n）个Broker上
（3）将第i个Partition的第j个Replica分配到第（(i + j) mode n）个Broker上
同时，Kafka与Replica既非同步也不是严格意义上的异步。一个典型的Kafka发送-消费消息的过程如下：首先首先Producer消息发送给某Topic的某Partition的Leader，Leader先是将消息写入本地Log，同时follower（如果落后过多将会被踢出出 Replica列表）从Leader上pull消息，并且在未写入log的同时即向Leader发送ACK的反馈，所以对于某一条已经算作commit的消息来讲，在某一时刻，其存在于Leader的log中，以及Replica的内存中。这可以算作一个危险的情况（听起来吓人），因为如果此时集群挂了这条消息就算丢失了，但结合producer的属性（request.required.acks=2 当所有follower都收到消息后返回ack）可以保证在绝大多数情况下消息的安全性。当消息算作commit的时候才会暴露给consumer，并保证at-least-once的投递原则。
**2）服务的稳定容错性**
前面提到过，Kafka天然支持HA，整个leader/follower机制通过zookeeper调度，它在所有broker中选出一个 controller，所有Partition的Leader选举都由controller决定，同时controller也负责增删Topic以及 Replica的重新分配。如果Leader挂了，集群将在ISR（in-sync replicas）中选出新的Leader，选举基本原则是：新的Leader必须拥有原来的Leader commit过的所有消息。假如所有的follower都挂了，Kafka会选择第一个“活”过来的Replica（不一定是ISR中的）作为 Leader，因为如果此时等待ISR中的Replica是有风险的，假如所有的ISR都无法“活”，那此partition将会变成不可用。
**3） 吞吐量**
Leader节点负责某一topic（可以分成多个partition）的某一partition的消息的读写，任何发布到此partition的消息都会被直接追加到log文件的尾部，因为每条消息都被append到该partition中，是顺序写磁盘，因此效率非常高（经验证，顺序写磁盘效率比随机写内存还要高，这是Kafka高吞吐率的一个很重要的保证），同时通过合理的partition，消息可以均匀的分布在不同的partition里面。 Kafka基于时间或者partition的大小来删除消息，同时broker是无状态的，consumer的消费状态(offset)是由 consumer自己控制的（每一个consumer实例只会消费某一个或多个特定partition的数据，而某个partition的数据只会被某一个特定的consumer实例所消费），也不需要broker通过锁机制去控制消息的消费，所以吞吐量惊人，这也是Kafka吸引人的地方。
最后说下由于zookeeper引起的脑裂（Split Brain）问题：每个consumer分别单独通过Zookeeper判断哪些partition down了，那么不同consumer从Zookeeper“看”到的view就可能不一样，这就会造成错误的reblance尝试。而且有可能所有的 consumer都认为rebalance已经完成了，但实际上可能并非如此。

 

如果在MQ的场景下，将Kafka 和 ActiveMQ 相比:

### Kafka 的优点

分布式可高可扩展。Kafka 集群可以透明的扩展，增加新的服务器进集群。

高性能。Kafka 的性能大大超过传统的ActiveMQ、RabbitMQ等MQ 实现，尤其是Kafka 还支持batch 操作。下图是linkedin 的消费者性能压测结果:



容错。Kafka每个Partition的数据都会复制到几台服务器上。当某个Broker故障失效时，ZooKeeper服务将通知生产者和消费者，生产者和消费者转而使用其它Broker。

**Kafka 的不利**

重复消息。Kafka 只保证每个消息至少会送达一次，虽然几率很小，但一条消息有可能会被送达多次。 
消息乱序。虽然一个Partition 内部的消息是保证有序的，但是如果一个Topic 有多个Partition，Partition 之间的消息送达不保证有序。 
复杂性。Kafka需要zookeeper 集群的支持，Topic通常需要人工来创建，部署和维护较一般消息队列成本更高