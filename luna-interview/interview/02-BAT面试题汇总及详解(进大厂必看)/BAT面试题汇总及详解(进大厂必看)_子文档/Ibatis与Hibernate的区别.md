# Ibatis与Hibernate的区别



 一、 hibernate与ibatis之间的比较：

   hibernate 是当前最流行的o/r mapping框架，它出身于sf.NET，现在已经成为jboss的一部分了。
   ibatis 是另外一种优秀的o/r mapping框架，目前属于apache的一个子项目了。 
相对hibernate“o/r”而言，ibatis是一种“sql mapping”的orm实现。 
hibernate对数据库结构提供了较为完整的封装，hibernate的o/r mapping实现了pojo 和数据库表之间的映射，以及sql 的自动生成和执行。程序员往往只需定义好了pojo 到数据库表的映射关系，即可通过hibernate 提供的方法完成持久层操作。程序员甚至不需要对sql 的熟练掌握， hibernate/ojb 会根据制定的存储逻辑，自动生成对应的sql 并调用jdbc 接口加以执行。 
而ibatis 的着力点，则在于pojo 与sql之间的映射关系。也就是说，ibatis并不会为程序员在运行期自动生成sql 执行。具体的sql 需要程序员编写，然后通过映射配置文件，将sql所需的参数，以及返回的结果字段映射到指定pojo。 
使用ibatis 提供的orm机制，对业务逻辑实现人员而言，面对的是纯粹的Java对象。
这一层与通过hibernate 实现orm 而言基本一致，而对于具体的数据操作，hibernate会自动生成sql 语句，而ibatis 则要求开发者编写具体的sql 语句。相对hibernate而言，ibatis 以sql开发的工作量和数据库移植性上的让步，为系统设计提供了更大的自由空间。 
二、hibernate与ibatis两者的对比：

   1．ibatis非常简单易学，hibernate相对较复杂，门槛较高。 
   2．二者都是比较优秀的开源产品 
   3．当系统属于二次开发,无法对数据库结构做到控制和修改,那ibatis的灵活性将比hibernate更适合 
   4．系统数据处理量巨大，性能要求极为苛刻，这往往意味着我们必须通过经过高度优化的sql语句（或存储过程）才能达到系统性能设计指标。在这种情况下ibatis会有更好的可控性和表现。 
   5．ibatis需要手写sql语句，也可以生成一部分，hibernate则基本上可以自动生成，偶尔会写一些hql。同样的需求,ibatis的工作量比hibernate要大很多。类似的，如果涉及到数据库字段的修改，hibernate修改的地方很少，而ibatis要把那些sql mapping的地方一一修改。 
   6．以数据库字段一一对应映射得到的po和hibernte这种对象化映射得到的po是截然不同的，本质区别在于这种po是扁平化的，不像hibernate映射的po是可以表达立体的对象继承，聚合等等关系的，这将会直接影响到你的整个软件系统的设计思路。 
  7．hibernate现在已经是主流o/r mapping框架，从文档的丰富性，产品的完善性，版本的开发速度都要强于ibatis。



### 三、iBatis与Hibernate区别？



 1． iBatis 需要手写sql语句，也可以生成一部分，Hibernate则基本上可以自动生成，偶尔会写一些Hql。同样的需求,iBATIS的工作量比 Hibernate要大很多。类似的，如果涉及到数据库字段的修改，Hibernate修改的地方很少，而iBATIS要把那些sql mapping的地方一一修改。
 **2. iBatis 可以进行细粒度的优化**
比如说我有一个表，这个表有几个或者几十个字段，我需要更新其中的一个字段，iBatis 很简单，执行一个sql UPDATE TABLE_A SET column_1=#column_1# WHERE id=#id# 但是用 Hibernate 的话就比较麻烦了,缺省的情况下 hibernate 会更新所有字段。 当然我记得 hibernate 有一个选项可以控制只保存修改过的字段，但是我不太确定这个功能的负面效果。
例如：我需要列出一个表的部分内容，用 iBatis 的时候，这里面的好处是可以少从数据库读很多数据，节省流量SELECT ID, NAME FROM TABLE_WITH_A_LOT_OF_COLUMN WHERE …一般情况下Hibernate 会把所有的字段都选出来。比如说有一个上面表有8个字段，其中有一两个比较大的字段，varchar(255)/text。上面的场景中我为什么要把他们 也选出来呢？用hibernate 的话，你又不能把这两个不需要的字段设置为lazy load，因为还有很多地方需要一次把整个 domain object 加载出来。这个时候就能显现出ibatis 的好处了。如果我需要更新一条记录（一个对象），如果使用 hibernate，需要现把对象 select 出来，然后再做 update。这对数据库来说就是两条sql。而iBatis只需要一条update的sql就可以了。减少一次与数据库的交互，对于性能的提升是非常重 要。
 **3. 开发方面：**
开发效率上，我觉得两者应该差不多。可维护性方面，我觉得 iBatis 更好一些。因为 iBatis 的 sql 都保存到单独的文件中。而 Hibernate 在有些情况下可能会在 java 代码中保sql/hql。相对Hibernate“O/R”而言，iBATIS 是一种“Sql Mapping”的ORM实现。 而iBATIS 的着力点，则在于POJO 与SQL之间的映射关系。也就是说，iBATIS并不会为程序员在运行期自动生成SQL 执行。具体的SQL 需要程序员编写，然后通过映射配置文件，将SQL所需的参数，以及返回的结果字段映射到指定POJO。使用iBATIS 提供的ORM机制，对业务逻辑实现人员而言，面对的是纯粹的Java对象，这一层与通过Hibernate 实现ORM 而言基本一致，而对于具体的数据操作，Hibernate会自动生成SQL 语句，而iBATIS 则要求开发者编写具体的SQL 语句。相对Hibernate而言，iBATIS 以SQL开发的工作量和数据库移植性上的让步，为系统设计提供了更大的自由空间。
 **4. 运行效率：**在不考虑 cache 的情况下，iBatis 应该会比hibernate 快一些或者很多。

***\*四、选择Hibernate还是iBATIS都有它的道理：\****
（1）、Hibernate功能强大，数据库无关性好，O/R映射能力强，如果你对Hibernate相当精通，而且对Hibernate进行了适当的封装，那么你的项目整个持久层代码会相当简单，需要写的代码很少，开发速度很快，非常爽。
Hibernate的缺点就是学习门槛不低，要精通门槛更高，而且怎么设计O/R映射，在性能和对象模型之间如何权衡取得平衡，以及怎样用好Hibernate方面需要你的经验和能力都很强才行。
（2）、iBATiS入门简单，即学即用，提供了数据库查询的自动对象绑定功能，而且延续了很好的SQL使用经验，对于没有那么高的对象模型要求的项目来说，相当完美。
iBATIS的缺点就是框架还是比较简陋，功能尚有缺失，虽然简化了数据绑定代码，但是整个底层数据库查询实际还是要自己写的，工作量也比较大，而且不太容易适应快速数据库修改。
建议：如果你的团队没有Hibernate高手，那么请用iBATIS。要把Hibernate用好，并不容易；否则你应该选择Hibernate，这样的话你的开发速度和代码简洁性都非常棒。