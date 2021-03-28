# MySQL优化(二)：SQL优化



一、SQL优化

1、优化SQL一般步骤

1.1 查看SQL执行频率

   SHOW STATUS LIKE 'Com_%';

   Com_select：执行SELECT操作的次数，一次查询累加1。其他类似

​    以下参数只针对InnoDB存储引擎，累加算法略有不同

​    Innodb_rows_read：SELECT查询操作插入的行数

​    Innodb_rows_inserted/updated/deleted：执行INSERT/UPDATE/DELETE操作的行数

​    通过以上参数，可以了解当前数据库应用是查询为主还是写入数据为主。

​    对于事务型的应用。通过Com_commit和Com_rollback可以了解事务提交和回滚的情况，对于回滚操作非常的频繁的数据库，可能意味着应用编写存在问题。

​    基本情况了解：

​    Connections：试图连接MySQL服务器的次数。

​    Uptime：服务器工作时间 

​    Slow_queries：慢查询的次数

1.2 定位执行效率比较低的SQL语句

​    \- 通过慢查询日志定位慢SQL，用--log-slow-queries[=file_name]选项启动时，mysqld会写一个所有执行时间超过long_query_time秒的SQL语句的日志文件。

​    \- 使用SHOW FULL PROCESSLIST; 查看当前MySQL在进行的线程，同时对一些锁表操作进行优化。   

1.3 通过EXPLAIN分析慢SQL

   语法：EXPLAIN SQL语句

   结果：

![1095387-20180816105800677-1486415872](MySQL优化(二)：SQL优化.assets/1095387-20180816105800677-1486415872.png)

   \- select_type：表示SELECT的类型，常见的取值有SIMPLE(简单表，即不使用表连接或者子查询)、PRIMARY(主查询，即外层的查询)、UNION(UNION中的第二个或者后面的查询语句)、SUBQUERY(子查询中的第一个SELECT)等。

   \- table：输出结果的表名

   \- type：表示MySQL在表中找到所需行的方式，或者叫访问类型

​    常见的有：ALL index range ref eq_ref const,system NULL，从左到右，性能由最差到最好。

​    type=ALL：全表扫描。

​    type=index：索引全扫描，MySQL遍历整个索引来查询。

​    type=range：索引范围扫描，常见于<、<=、>、 >=、 between。

​    type=ref：使用非唯一索引扫描或唯一索引的前缀扫描，返回匹配某个单独值的记录。

​    type=eq_ref：类似ref，区别就在使用的索引是唯一索引，对于每个索引键值，表中只有一条记录匹配，简单来说，就是多表连接中使用primary key或者unique index作为关联条件。

​    type=const/system：单表中最多有一个匹配行，查询起来非常迅速，一般主键primary key或者唯一索引unique index进行的查询，通过唯一索引uk_email访问的时候，类型type为const；而从我们构造的仅有一条记录的a表中检索时，类型type为system。

​    type=NULL：MySQL不用访问表或者索引，就能直接得到结果。

​    类型type还有其他值，如ref_or_null(与ref类似，区别在于条件中包含对NULL的查询)、index_merge(索引合并优化)、unique_subquery(in的后面是一个查询主键字段的子查询)、index_subquery(与unique_subquery类似，区别在于in的后面是查询非唯一索引字段的子查询)

​    \- possible_keys：表示查询时可能使用的索引。

​    \- key：表示实际使用的索引。

​    \- key_len：使用到索引字段的长度。

​    \- rows：扫描行的数量

​    \- Extra：执行情况的说明和描述，包含不适合在其他列中显示但是对执行计划非常重要的额外信息。

​     Using where：表示优化器除了利用索引来加速访问之外，还需要根据索引回表查询数据。

1.4 通过show profile分析SQL

   查看当前MySQL是否支持profile

![1095387-20180816140322675-1626492118](MySQL优化(二)：SQL优化.assets/1095387-20180816140322675-1626492118.png)

   默认profiling是关闭的，可以通过set语句在Session级别开启profiling：set profiling=1;

   使用方法：

   \- 执行统计查询：

![1095387-20180816140939027-761930886](MySQL优化(二)：SQL优化.assets/1095387-20180816140939027-761930886.png)

   \- 查找上述SQL的query ID：

![1095387-20180816141115091-1411161465](MySQL优化(二)：SQL优化.assets/1095387-20180816141115091-1411161465.png)

   \- 查找上述SQL执行过程中每个线程的状态和消耗时间：

![1095387-20180816141553600-1519244449](MySQL优化(二)：SQL优化.assets/1095387-20180816141553600-1519244449.png)

   Sending data状态表示MySQL线程开始访问数据并行把结果返回给客户端，而不仅仅是返回结果给客户端。由于在Sending data状态下，MySQL线程往往需要做大量的磁盘读取操作，所以经常是整个查询中耗时最长的状态。

​    \- 查看详细信息并排序：

```
SELECT
    STATE,
    SUM(DURATION) AS TR,
    ROUND(
        100 * SUM(DURATION) / (
            SELECT
                SUM(DURATION)
            FROM
                information_schema.PROFILING
            WHERE
                QUERY_ID = 3
        ),
        2
    ) AS PR,
    COUNT(*) AS Calls,
    SUM(DURATION) / COUNT(*) AS "R/Call"
FROM
    information_schema.PROFILING
WHERE
    QUERY_ID = 3
GROUP BY
    STATE
ORDER BY
    TR DESC;
```

![1095387-20180816155903676-174906157](MySQL优化(二)：SQL优化.assets/1095387-20180816155903676-174906157.png)

   进一步获取all、cpu、block io、context switch、page faults等明细类型来查看MySQL在使用什么资源上耗费了过高的时间，例如，选择查看CPU的耗费时间。

   此时可获取到sending data时间主要消耗在CPU上

![1095387-20180816161232430-327486737](MySQL优化(二)：SQL优化.assets/1095387-20180816161232430-327486737.png)

   提示：InnoDB引擎count(*)没有MyISAM执行速度快，就是因为InnoDB引擎经历了Sending data状态，存在访问数据的过程，而MyISAM引擎的表在executing之后直接就结束查询，完全不需要访问数据。

2、索引问题

   索引是数据库优化中最常用也是最重要的手段之一，通过索引通常可以帮助用户解决大多数的SQL性能问题。

2.1 存储引擎的分类

   \- B-Tree索引：最常见的索引类型，大部分引擎都支持B树索引。

   \- HASH索引：只有Memory引擎支持。

   \- R-Tree索引：空间索引是MyISAM的一个特殊索引类型，主要用于地理空间数据类型。

   \- Full-text：全文索引是MyISAM的一个特殊索引类型，主要用于全文索引，InnoDB从MySQL5.6版本开始对其支持。

   MySQL目前不支持函数索引，但是能对列的前面某一部分进行索引，例如标题title字段，可以只取title的前10个字符进行索引，但是在排序Order By和分组Group By操作的时候无法使用。前缀索引创建例子：create index idx_title on film(title(10))。

   常用的索引是B-Tree和Hash。Hash只有Memory/Heap引擎支持。适用于Key-Value查询，通过Hash比B-Tree更迅速。Hash索引不使用范围查询。Memory/Heap引擎只有在=条件下才会使用索引。

2.2 MySQL如何使用索引

   创建一个复合索引：ALTER TABLE rental ADD INDEX idx_rental_date (rental_date, inventory_id, customer_id);

2.2.1 MySQL中能够使用索引的典型场景

   \- 匹配全值，对索引中所有列都指定具体值，即是对索引中的所有列都有等值匹配条件。

​    比如上述创建的idx_rental_date，包含rental_date, inventory_id, customer_id，此时如果where子句中包含三者，即为全值匹配。

![1095387-20180817162943669-1879690118](MySQL优化(二)：SQL优化.assets/1095387-20180817162943669-1879690118.png)

​    字段key为idx_rental_date，表示优化器使用的是索引idx_rental_date进行扫描。

   \- 匹配值的范围查询，对索引的值能够进行范围查找。

![1095387-20180817170926021-1430224937](MySQL优化(二)：SQL优化.assets/1095387-20180817170926021-1430224937.png)

​    类型type为range说明优化器选择范围查询，索引key为idx_fk_customer_id说明优化器选择索引idx_fk_customer_id来加速访问。

   \- 匹配最左前缀，意思是在复合索引中，索引是从左边第一个开始查找，不会跨过第一个从第二个查找，比如一个联合索引包含(c1, c2, c3)三个字段，可是不能被c2或者c2+c3等值查询利用到。

​    添加索引：ALTER TABLE payment ADD INDEX idx_payment_date(payment_date, amount, last_update);此时第一个字段是payment_date

​    如果查询条件包含索引的第一列支付日期，能够使用复合索引idx_payment_date进行过滤。

​    比如：

![1095387-20180817174217694-122391617](MySQL优化(二)：SQL优化.assets/1095387-20180817174217694-122391617.png)

​    如果使用的是第二个支付金额不包含第一个，则不会使用索引。

​    比如：(此时key为空)

![1095387-20180817174411343-1844690364](MySQL优化(二)：SQL优化.assets/1095387-20180817174411343-1844690364.png)

   \- 仅仅对索引进行查询，意思是查询的数据都在索引字段中时，查询的效率更高。

​    比如此时查询last_update且last_update字段被包含在索引字段中如图：

![1095387-20180820112334175-618747015](MySQL优化(二)：SQL优化.assets/1095387-20180820112334175-618747015.png)

​    那么直接访问索引就可以获取所需的数据，不需要通过索引回表，此时的Extra也变成了Using index，Using index指的是覆盖索引扫描。

​    查询结果：

![1095387-20180820112833930-1267160093](MySQL优化(二)：SQL优化.assets/1095387-20180820112833930-1267160093.png)

   \- 匹配列前缀，仅仅使用索引中的第一列，并且只包含索引第一列的开头一部分进行查找。

​    例如查找标题title是以AFRICAN开头的电影信息。

​    首先创建索引：CREATE INDEX idx_title_desc_part ON film_text (title(10), description(20));

​    查询可以看到idx_title_desc_part 被使用，Using where表示优化器需要通过索引回表查询数据：

​    ![1095387-20180820113830861-1065875314](MySQL优化(二)：SQL优化.assets/1095387-20180820113830861-1065875314.png)

   \- 匹配部分精确，其他部分范围匹配。

​    指定日期，不同客户编号

![1095387-20180820114242955-1520792792](MySQL优化(二)：SQL优化.assets/1095387-20180820114242955-1520792792.png)

​    类型type为range说明优化器选择范围查询，索引key为idx_rental_date说明优化器选择索引idx_rental_date帮助加速查询，同时所查询的字段在索引中，索引Extra能看见Using index。

   \- 列名 is null，此种情况下会使用索引。

​    例如：

​    ![1095387-20180820145640866-59856749](MySQL优化(二)：SQL优化.assets/1095387-20180820145640866-59856749.png)

 2.2.2 存在索引但不能使用的典型场景

   \- 以%开头的LIKE查询不能利用B-Tree索引。

​    如下：

![1095387-20180820152331948-1113401102](MySQL优化(二)：SQL优化.assets/1095387-20180820152331948-1113401102.png)

​    B-Tree索引结构，以%开头的查询无法利用索引，一般可使用全文索引(Fulltext)来解决类似问题。或者使用InnoDB表上的二级索引，首先获取满足条件的列表的id，之后再根据主键回表去检索记录。

![1095387-20180820153245036-168858525](MySQL优化(二)：SQL优化.assets/1095387-20180820153245036-168858525.png)

   \- 数据类型出现隐式转换不会使用索引，有些列类型是字符串，在写where条件时，需要将常量值用引号括起来。

   \- 复合索引，查询条件需要包括最左边部分，否则不会使用复合索引。即leftmost

   \- MySQL执行语句时会有优化器选择的过程，当全表扫描的代价小于索引的代价时，会使用全表扫描，所以此时需要更换一个筛选性更高的条件。

   \- 用or分开的条件，如果or前的列有索引，后面的没有索引，则不会使用索引。

2.3 查看索引的使用情况

![1095387-20180820160156677-1519550033](MySQL优化(二)：SQL优化.assets/1095387-20180820160156677-1519550033.png)

   如果索引正在工作，Handler_read_key的值将很高，这个值代表了一个行被索引值读的次数，如果很低，说明增加索引得到的性能改善不高，因为索引并没有被经常使用。

   Handler_read_rnd_next的值高则意味着查询运行低效，并且应该建立索引补救。这个值的含义是在数据文件中读下一行的请求数。如果值比较大，说明正在进行大量的表扫描，则通常说明表索引不正确或写入的查询没有利用索引。

 

3、常用SQL优化

3.1 大批量插入数据(load)

   \- MyISAM

​    \- 打开或者关闭MyISAM表非唯一索引的更新，可以提高导入效率(导入数据到非空MyISAM表)。

​     步骤：ALTER TABLE tab_name DISABLE KEYS; 导入数据; ALTER TABLE tab_name ENABLE KEYS;

​     导入数据到一个空的MyISAM表，默认是先导入数据然后才创建索引的，所以不用设置。

   \- InnoDB

​    \- 因为InnoDB类型的表是按照主键顺序保存的，所以降导入的数据按主键的顺序排列，可以有效的提高导入数据的效率。

​    \- 关闭唯一性校验，SET UNIQUE_CHECKS = 0,导入结束后开启。

​    \- 如果使用的是自动提交的方式，在导入前使用SET AUTOCOMMIT = 0，导入结束后在恢复。 

3.2 优化INSERT语句

​    \- 同一客户端插入很多行，应尽量使用多个值的INSERT语句。比如：INSERT INTO tab_name values(),(),()...

​    \- 不同客户端插入很多行，可以使用INSERT DELAYED，DELAYED含义是让INSERT语句放置到内存的队列中，并没有写入磁盘。LOW_PRIORITY是在所有其他用户对表的读写完成后才进行插入。

​    \- 将索引文件和数据文件分别放置在不同的磁盘上。

​    \- MyISAM如果进行批量插入，增加bulk_insert_buffer_size的值。

​    \- 从文件装载一个表时，使用LOAD DATA INFILE，比INSERT语句快20倍。

3.3 优化ORDER BY 语句

3.3.1 MySQL排序方式

   \- 通过有序索引顺序扫描直接返回有序数据。

​    在表customer上有索引idx_fk_store_id，指向字段store_id

![1095387-20180821095756675-504865635](MySQL优化(二)：SQL优化.assets/1095387-20180821095756675-504865635.png)

​    此时order by使用store_id排序时，Extra为Using index，不需要额外的排序，操作效率较高。

![1095387-20180821100114373-1353800322](MySQL优化(二)：SQL优化.assets/1095387-20180821100114373-1353800322.png)

   \- 通过Filesort排序，所有不是通过索引直接返回排序结果的排序都叫Filesort排序。MySQL服务器对排序参数的设置和需要排序数据的大小决定排序操作是否使用磁盘文件或临时表。

​    Filesort是通过算法，将取得的数据在sort_buffer_size系统变量设置的内存排序区中进行排序，如果内存装不下，就会将磁盘上的数据进行分块，再对各个数据块进行排序，然后合并。sort_buffer_size的排序区为线程独占，可能同时存在多个。

​    比如通过store_id排序所有客户记录时，此时为全表扫描，并且使用filesort。

![1095387-20180821101131126-2057095990](MySQL优化(二)：SQL优化.assets/1095387-20180821101131126-2057095990.png)

​    一般优化方式：减少额外的排序，通过索引直接返回有序数据。尽量使WHERE条件和ORDER BY使用相同的索引，并且ORDER BY的顺序和索引数据相同，并且ORDER BY的字段都是升序或者都是降序，否则肯定会出现Filesort。

​    \- 不会使用索引情况：

​     \- order by的字段混合ASC和DESC：SELECT * FROM TAB_NAME ORDER BY KEY_PART1 DESC, KEY_PART2 ASC;

​     \- 用于查询的关键字与ORDER BY 中所使用的不相同：SELECT * FROM TAB_NAME WHERE KEY2=CONSTANT ORDER BY KEY1;

​     \- 对不同的关键字使用ORDER BY：SELECT * FROM TAB_NAME ORDER BY KEY1, KEY2;

3.3.2 优化Filesort

   Filesort有两种排序算法：

   \- 两次扫描算法：首先根据条件取出排序字段和行指针信息，之后在排序区sort buffer中排序。如果排序区sort buffer不够，则在临时表Temporary Table中存储排序结果，完成排序后根据行指针回表读取记录。需要两次访问数据，第一次获取排序字段和行指针信息，第二次根据行指针获取记录，第二次读取操作可能导致大量随机I/O操作，优点是排序的时候内存开销较少。

   \- 一次扫描算法：一次性取出满足条件的行的所有字段，然后在排序区sort buffer中排序后直接输出结果集，排序的时候内存开销比较大，但是排序效率比两次扫描要高。

   MySQL通过比较系统变量max_length_for_sort_data的大小和Query语句取出的字段总大小来判断使用哪种算法。max_length_for_sort_data大使用第二种算法，否则第一种。

   适当加大系统变量max_length_for_sort_data的值，能够让MySQL选择更优化的Filesort排序算法。但是过大会引起CPU利用率过低和磁盘I/O过高。

   适当加大sort_buffer_size排序区，尽量让排序在内存中完成，而不是通过创建临时表放在文件中进行；该大小需要考虑数据库活动连接数和服务器内存的大小来适当设置排序区。因为这个参数是每个线程独占的，如果设置过大，会导致服务器SWAP严重。尽量只使用必要的字段，而不是SELECT *。

 3.3.3 优化GROUP BY

   默认情况下，MySQL对所有的GROUP BY字段进行排序，如果查询包括GROUP BY但是用户想要避免排序结果的消耗，则可以指定ORDER BY NULL禁止排序。

   SELECT XXX FROM XXX GROUP BY XXX ORDER BY NULL

3.3.4 优化嵌套查询

   使用子查询可以一次性的完成很多逻辑上需要多个步骤才能完成的SQL操作，同时也可以避免事务或者表锁死。子查询可以被更有效的连接JOIN替代。

3.3.5 优化OR条件

   对于含有OR的查询子句，如果要利用索引，则OR之间的每个条件列都必须要用到索引。

3.3.6 优化分页查询

​    一般分页查询时，通过创建覆盖索引能够比较好地提高性能，但是当分页为1000 20时，此时会排序前1020条记录后返回1001到1020条记录，前1000条记录都会被抛弃，查询和排序的代价非常高。

​    \- 第一种优化思路：从索引完成排序分页的操作，最后根据主键关联回原表查询所需的其他列内容。

​     例如：对电影表film根据标题title排序后取某一页数据

​     \- 直接查询

![1095387-20180821161046052-1685002475](MySQL优化(二)：SQL优化.assets/1095387-20180821161046052-1685002475.png)

​      按照索引分页后回表方式改写SQL

![1095387-20180821161418223-457551069](MySQL优化(二)：SQL优化.assets/1095387-20180821161418223-457551069.png)

   \- 第二种优化思路：把limit查询转换成某个位置的查询。

​    假设需要查询第100页，则可以记录99页最后一行的id(倒序或者正序)，然后再次查询时使用where取99也最后一行的id进行大于或者小于，然后在直接使用limit n即可。n为每页显示的行数。

​    比如以每页十行查询第100页的数据，可以使用一下步骤：

​    首先查询到第99行最后一行的id：

![1095387-20180821175324674-297516276](MySQL优化(二)：SQL优化.assets/1095387-20180821175324674-297516276.png)

​    在通过获取到的id取小于它的值，取10行，即为第100页：

​    ![1095387-20180821175356117-42322935](MySQL优化(二)：SQL优化.assets/1095387-20180821175356117-42322935.png)

​    与直接查询结果相比较：

​    ![1095387-20180821175423565-1921886951](MySQL优化(二)：SQL优化.assets/1095387-20180821175423565-1921886951.png)

​    explain比较：

​    ![1095387-20180821175517953-301849620](MySQL优化(二)：SQL优化.assets/1095387-20180821175517953-301849620.png)

![1095387-20180821175547994-806782603](MySQL优化(二)：SQL优化.assets/1095387-20180821175547994-806782603.png)

3.3.7 使用SQL提示

​    \- USE INDEX

​     提示MySQL参考使用的索引，可以让MySQL不再考虑其他可用的索引。

​     比如：select count(1) from tab_name use index(index_name) where xxx; 此时查询会用index_name所以，而忽略其他。

​    \- IGNORE INDEX

​     提示MySQL忽略一个或者索引。

​     比如：select count(1) from tab_name ignore index (index_name); 此时查询会忽略index_name索引。

​    \- FORCE INDEX

​      强制MySQL使用某个索引，使用情况：当where子句取id>1的值，因为数据库中大部分库表都是大于1的，所以会全盘扫描，此时使用use index不可用，所以使用force index。

​      比如：select * from tab_name force index(index_name) where id > 1;

4、常用SQL技巧

4.1 正则表达式的使用

   ![1095387-20180821195130862-17252537](MySQL优化(二)：SQL优化.assets/1095387-20180821195130862-17252537.png)

   \-  ^ 在字符串的开始处进行匹配

​     匹配是否已a开头

![1095387-20180821195408561-1356471726](MySQL优化(二)：SQL优化.assets/1095387-20180821195408561-1356471726.png)

   \-  $ 在字符串的末尾处进行匹配。

   \-  . 匹配任意单个字符，包括换行符。

![v1095387-20180821195713103-1542820628](MySQL优化(二)：SQL优化.assets/v1095387-20180821195713103-1542820628.png)

   \-  [...] 匹配出括号内的任意字符。

![1095387-20180821195837065-356490816](MySQL优化(二)：SQL优化.assets/1095387-20180821195837065-356490816.png)

   \-  [^...] 不匹配[]内的任意字符

   真实例子：

![1095387-20180821201003861-1946929549](MySQL优化(二)：SQL优化.assets/1095387-20180821201003861-1946929549.png)

  使用like格式如下：SELECT first_name, email FROM customer WHERE email LIKE "%@163.com" OR email LIKE "%@163,com";

 4.2 利用RAND()提取随机行

   随机抽取n条数据：SELECT * FROM tab_name ORDER BY RAND() LIMIT n;

4.3 GROUP BY的WITH ROLLUP

   WITH ROLLUP可以检索出更多的分组聚合信息。

   比如查询经手员工每日的支付金额的统计。不使用WITH ROLLUP如下：

![1095387-20180822105325785-842575664](MySQL优化(二)：SQL优化.assets/1095387-20180822105325785-842575664.png)

​    加入 WITH ROLLUP如下：

![1095387-20180822105428622-564606270](MySQL优化(二)：SQL优化.assets/1095387-20180822105428622-564606270.png)

​    WITH ROLLUP反映的是一种OLAP思想，可以满足用户想要得到的任何一个分组以及分组组合的聚合信息值。上个例子中，WITH ROLLUP帮用户统计了每日的总金额和所有的总金额。注意：ROLLUP不能和ORDER BY使用，且limit在ROLLUP后面。

4.4 数据库名、表名大小写问题

   由于Windows、Mac OS、Unix对库表名已经查询使用的大小写敏感不一致，所以最好将库表进行规范保存，且查询语句也规范使用。