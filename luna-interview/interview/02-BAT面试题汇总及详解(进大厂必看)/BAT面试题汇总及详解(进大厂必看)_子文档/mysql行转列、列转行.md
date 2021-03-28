# mysql行转列、列转行

语句不难，不做多余解释了，看语句时，从内往外一句一句剖析

## 行转列

​    有如图所示的表，现在希望查询的结果将行转成列

![20160901211351701](mysql行转列、列转行.assets/20160901211351701.png)

​    建表语句如下：

```sql
CREATE TABLE `TEST_TB_GRADE` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(20) DEFAULT NULL,
  `COURSE` varchar(20) DEFAULT NULL,
  `SCORE` float DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;1234567
insert into TEST_TB_GRADE(USER_NAME, COURSE, SCORE)  values
("张三", "数学", 34),
("张三", "语文", 58),
("张三", "英语", 58),
("李四", "数学", 45),
("李四", "语文", 87),
("李四", "英语", 45),
("王五", "数学", 76),
("王五", "语文", 34),
("王五", "英语", 89);12345678910
```

​    查询语句：

​    此处用之所以用MAX是为了将无数据的点设为0，防止出现NULL

```sql
SELECT user_name ,
    MAX(CASE course WHEN '数学' THEN score ELSE 0 END ) 数学,
    MAX(CASE course WHEN '语文' THEN score ELSE 0 END ) 语文,
    MAX(CASE course WHEN '英语' THEN score ELSE 0 END ) 英语
FROM test_tb_grade
GROUP BY USER_NAME;123456
```

​    结果展示：

![20160901211414524](mysql行转列、列转行.assets/20160901211414524.png)

## 列转行

​    有如图所示的表，现在希望查询的结果将列成行

![20160901211431540](mysql行转列、列转行.assets/20160901211431540.png)

​    建表语句如下：

```sql
CREATE TABLE `TEST_TB_GRADE2` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(20) DEFAULT NULL,
  `CN_SCORE` float DEFAULT NULL,
  `MATH_SCORE` float DEFAULT NULL,
  `EN_SCORE` float DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;12345678
insert into TEST_TB_GRADE2(USER_NAME, CN_SCORE, MATH_SCORE, EN_SCORE) values
("张三", 34, 58, 58),
("李四", 45, 87, 45),
("王五", 76, 34, 89);1234
```

查询语句：

```sql
select user_name, '语文' COURSE , CN_SCORE as SCORE from test_tb_grade2
union select user_name, '数学' COURSE, MATH_SCORE as SCORE from test_tb_grade2
union select user_name, '英语' COURSE, EN_SCORE as SCORE from test_tb_grade2
order by user_name,COURSE;1234
```

​    结果展示：

![20160901211440967](mysql行转列、列转行.assets/20160901211440967.png)