# MySQL分表自增ID解决方案



当我们对MySQL进行分表操作后，将不能依赖MySQL的自动增量来产生唯一ID了，因为数据已经分散到多个表中。

应尽量避免使用自增IP来做为主键，为数据库分表操作带来极大的不便。

在postgreSQL、oracle、db2数据库中有一个特殊的特性---sequence。 任何时候数据库可以根据当前表中的记录数大小和步长来获取到该表下一条记录数。然而，MySQL是没有这种序列对象的。

可以通过下面的方法来实现sequence特性产生唯一ID：
**1. 通过MySQL表生成ID**
在《关于MySQL分表操作的研究》提到了一种方法：
对于插入也就是insert操作，首先就是获取唯一的id了，就需要一个表来专门创建id，插入一条记录，并获取最后插入的ID。代码如下：

```
CREATE TABLE `ttlsa_com`.`create_id` ( 
`id` BIGINT( 20 ) NOT NULL AUTO_INCREMENT PRIMARY KEY
) ENGINE = MYISAM
```



也就是说，当我们需要插入数据的时候，必须由这个表来产生id值，我的php代码的方法如下：

```
<?php 
function get_AI_ID() {  
	$sql = "insert into create_id (id) values('')";  
	$this->db->query($sql); 
	return $this->db->insertID(); 
}
?>
```



这种方法效果很好，但是在高并发情况下，MySQL的AUTO_INCREMENT将导致整个数据库慢。如果存在自增字段，MySQL会维护一个自增锁，innodb会在内存里保存一个计数器来记录auto_increment值，当插入一个新行数据时，就会用一个表锁来锁住这个计数器，直到插入结束。如果是一行一行的插入是没有问题的，但是在高并发情况下，那就悲催了，表锁会引起SQL阻塞，极大的影响性能，还可能会达到max_connections值。
innodb_autoinc_lock_mode：可以设定3个值：0、1、2
0：traditonal （每次都会产生表锁）
1：consecutive （默认,可预判行数时使用新方式，不可时使用表锁，对于simple insert会获得批量的锁，保证连续插入）
2：interleaved （不会锁表，来一个处理一个，并发最高）
对于myisam表引擎是traditional，每次都会进行表锁的。

**2. 通过redis生成ID**

```
function get_next_autoincrement_waitlock($timeout = 60){
	$count = $timeout > 0 ? $timeout : 60; 

	while($r->get("serial:lock")){	
		$count++;	
		sleep(1);	
		if($count >10)
			return false;
	} 
	return true;
} 
function get_next_autoincrement($timeout = 60){	
	// first check if we are locked...	
	if (get_next_autoincrement_waitlock($timeout) == false)		
			return 0; $id = $r->incr("serial"); 
	if ( $id > 1 )	
		return $id; 
	// if ID == 1, we assume we do not have "serial" key... 
	// first we need to get lock.
	if ($r->setnx("serial:lock"), 1){	
		$r->expire("serial:lock", 60  5); 	
		// get max(id) from database.	
		$id = select_db_query("select max(id) from user_posts");	
		// or alternatively:	
		// select id from user_posts order by id desc limit 1 	
		// increase it	
		$id++; 
		// update Redis key	
		$r->set("serial", $id); 
		// release the lock	
		$r->del("serial:lock"); 
		return $id;	
	} 
	// can not get lock.
	return 0;
}
$r = new Redis();
$r->connect("127.0.0.1", "6379"); 

$id = get_next_autoincrement();
if ($id){  
	$sql = "insert into user_posts(id,user,message)values($id,'$user','$message')"  
	$data = exec_db_query($sql);
}
```



**3. 队列方式**
使用队列服务，如redis、memcacheq等等，将一定量的ID预分配在一个队列里，每次插入操作，先从队列中获取一个ID，若插入失败的话，将该ID再次添加到队列中，同时监控队列数量，当小于阀值时，自动向队列中添加元素。

这种方式可以有规划的对ID进行分配，还会带来经济效应，比如ＱＱ号码，各种靓号，明码标价。如网站的userid, 允许uid登陆，推出各种靓号，明码标价，对于普通的ID打乱后再随机分配。

```
<?php 
class common { 

	private $r; 
	
	function construct() { 
		$this->__construct();  
	}  
	public function __construct(){  	
		$this->r=new Redis();
		$this->r->connect('127.0.0.1', 6379); 
	}  

	function set_queue_id($ids){  
		if(is_array($ids) && isset($ids)){  
			foreach ($ids as $id){  		
				$this->r->LPUSH('next_autoincrement',$id);  	
			} 
		} 
	}  
	function get_next_autoincrement(){  
		return $this->r->LPOP('next_autoincrement'); 
	}
}

$createid=array();
while(count($createid)<20){  
	$num=rand(1000,4000); 
	if(!in_array($num,$createid))  
		$createid[]=$num;
} 

$id=new common();
$id->set_queue_id($createid);

var_dump($id->get_next_autoincrement());
```

监控队列数量，并自动补充队列和取到id但并没有使用，相关代码没有贴出来。