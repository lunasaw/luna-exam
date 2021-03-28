# 基于redis的zset实现排行榜功能



临近中秋，公司需要开发一款微信小游戏，里面有一个排行榜的功能

主要需求包括：

\1. 用户可以上传每次游戏的分数，系统返回该用户的最高分和最高分排名（分数相同时，时间优先）；

\2. 用户可以查询排行榜，返回top50，和自己所在的排名

最开始是想使用数据库来实现，保存每个用户最高分的记录，主要字段【name, score, createTime】

针对需求1，用户有新的高分产生的话，就更新用户的最高分，否则返回当前的最高分，获取排名时，需要查询两次数据库（1：查询分数大于自己的记录

数；2：查询分数相同，时间小于自己的记录数）

针对需求2，按照分数倒序，时间正序查询top50，判断自己如果不是前50，则查询自己的记录，放到列表末尾

这样对于数据库的查询压力会比较大，而且只是一个临时活动，也没必要专门创建一张表来实现

然后和同事讨论可不可以参考HashMap的原理，使用数组+链表的形式的来实现，将分数作为数组下标，每个数组元素上是链表，按照达到该分数的先后

顺序保存用户

![img](https://img-blog.csdn.net/20170927165630464?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbHVud3VjaXl1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
 

针对需求1: 用户上传新的分数时，在对应索引位置末尾增加用户，将原先的用户节点删除（需要另外维护用户原先的最大分数），从末尾循环每个节点的

链表，获取排名和最高分

针对需求2：类似于需求1，从末尾循环每个节点的链表，获取top50

这样每次都需要循环列表，如果用户分数很低的话，循环会比较消耗性能，而且系统重启也会丢失数据。

最后都懂得，搜索引擎，看到redis 的zset原来是解决排行榜的标配，天生就是来做排行榜的，参考了一些网上的文章
 

redis的zset可以给每个object标记一个分数，然后可以针对这个分数，为object排名，基于hashtable和skiplist执行insert和remove操作，可以通过range方法获取top50，通过rank方法获取排名，完美解决排行榜问题，直接上代码

获取top50的逻辑



```java
public List<MidAutumnView> getRangeTop(Long userId) {



        // top



        Set<String> midAutumnStrs = stringRedisTemplate.opsForZSet().range(MID_AUTUMN, 0, TOP_NUM);



        List<MidAutumnView> midAutumnViews = Lists.newArrayList();



        Iterator<String> iterator = midAutumnStrs.iterator();



        int i = 1;



        while (iterator.hasNext()) {



            midAutumnViews.add(convStr2MidAutumnView(iterator.next(), i));



            i++;



        }



        // 判断是否在末尾追加自己



        String midAutumnStr = stringRedisTemplate.opsForValue().get(MID_AUTUMN_USER + userId);



        MidAutumnView midAutumnView = JSONObject.parseObject(midAutumnStr, MidAutumnView.class);



        Long rank = stringRedisTemplate.opsForZSet().rank(MID_AUTUMN,



            convView2ItemStr(midAutumnView));



        if (rank != null && rank > TOP_NUM) {



            midAutumnViews.add(midAutumnView);



        }



        return midAutumnViews;



    }
```

提交新分数的逻辑

```java
public MidAutumnView putScore(RedisRankItem redisRankItem) {



        String midAutumnStr = stringRedisTemplate.opsForValue()



            .get(MID_AUTUMN_USER + redisRankItem.getUserId());



        // 首次提交分数



        if (StringUtils.isEmpty(midAutumnStr)) {



            stringRedisTemplate.opsForZSet().add(MID_AUTUMN, JSONObject.toJSONString(redisRankItem),



                redisRankItem.buildScore(redisRankItem.getScore()));



            Long rank = stringRedisTemplate.opsForZSet().rank(MID_AUTUMN,



                JSONObject.toJSONString(redisRankItem));



            MidAutumnView midAutumnView = MidAutumnView.builder().userId(redisRankItem.getUserId())



                .name(redisRankItem.getName()).portraitUrl(redisRankItem.getPortraitUrl())



                .score(redisRankItem.getScore()).createTime(redisRankItem.getCreateTime())



                .rank(rank.intValue()).maxScore(redisRankItem.getScore()).build();



            stringRedisTemplate.opsForValue().set(MID_AUTUMN_USER + redisRankItem.getUserId(),



                JSONObject.toJSONString(midAutumnView));



            return midAutumnView;



        } else {



            // 二次提交分数



            MidAutumnView midAutumnView = JSONObject.parseObject(midAutumnStr, MidAutumnView.class);



            midAutumnView.setScore(redisRankItem.getScore());



            midAutumnView.setCreateTime(redisRankItem.getCreateTime());



            // 更新最高分



            if (redisRankItem.getScore() > midAutumnView.getScore()) {



                stringRedisTemplate.opsForZSet().remove(MID_AUTUMN,



                    convView2ItemStr(midAutumnView));



                stringRedisTemplate.opsForZSet().add(MID_AUTUMN,



                    JSONObject.toJSONString(redisRankItem),



                    redisRankItem.buildScore(redisRankItem.getScore()));



                Long rank = stringRedisTemplate.opsForZSet().rank(MID_AUTUMN,



                    JSONObject.toJSONString(redisRankItem));



                midAutumnView.setMaxScore(redisRankItem.getScore());



                midAutumnView.setRank(rank.intValue());



                stringRedisTemplate.opsForValue().set(MID_AUTUMN_USER + redisRankItem.getUserId(),



                    JSONObject.toJSONString(midAutumnView));



            }



            return midAutumnView;



        }



    }
```

为了排错，最后用户的每次提交都会记录到mongo