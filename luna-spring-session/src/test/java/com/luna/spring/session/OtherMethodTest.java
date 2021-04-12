package com.luna.spring.session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OtherMethodTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void boundHashOperationTest() {
        // 给指定的建设置map值，不能为已有的bound参数绑定其他类型
        BoundHashOperations<String, String, Object> boundHashOperations = redisTemplate.boundHashOps("li");

        boundHashOperations.put("ww", "i");
        boundHashOperations.put("w1", "i1");
        boundHashOperations.put("w2", "i2");

        // 获取设置的绑定key值
        System.out.println("获取设置的绑定key值:" + boundHashOperations.getKey());

        // 获取map中的value值
        boundHashOperations.values().forEach(v -> System.out.println("获取map中的value值" + v));

        // 获取map键值对
        boundHashOperations.entries().forEach((m, n) -> System.out.println("获取map键值对:" + m + "-" + n));

        // 获取map键的值
        System.out.println("获取map建的值:" + boundHashOperations.get("w1"));

        // 获取map的键
        boundHashOperations.keys().forEach(v -> System.out.println("获取map的键:" + v));

        // 根据map键批量获取map值
        List list = new ArrayList<>(Arrays.asList("ww", "w1"));
        boundHashOperations.multiGet(list).forEach(v -> System.out.println("根据map键批量获取map值:" + v));

        // 批量添加键值对
        Map map = new HashMap<>();
        map.put("m1", "n1");
        map.put("m2", "n2");
        boundHashOperations.putAll(map);
        boundHashOperations.entries().forEach((m, n) -> System.out.println("批量添加键值对:" + m + "-" + n));

        // 自增长map键的值
        boundHashOperations.increment("c", 1);
        System.out.println("自增长map键的值:" + boundHashOperations.get("c"));

        // 如果map键不存在，则新增，存在，则不变
        boundHashOperations.putIfAbsent("m2", "n2-1");
        boundHashOperations.putIfAbsent("m3", "n3");
        boundHashOperations.entries().forEach((m, n) -> System.out.println("新增不存在的键值对:" + m + "-" + n));

        // 查看绑定建的map大小
        System.out.println("查看绑定建的map大小:" + boundHashOperations.size());

        // 遍历绑定键获取所有值
        Cursor<Map.Entry<String, Object>> cursor = boundHashOperations.scan(ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<String, Object> entry = cursor.next();
            System.out.println("遍历绑定键获取所有值:" + entry.getKey() + "---" + entry.getValue());
        }

        long delSize = boundHashOperations.delete("m3", "m2");
        System.out.println("删除的键的个数:" + delSize);
        boundHashOperations.entries().forEach((m, n) -> System.out.println("删除后剩余map键值对:" + m + "-" + n));
    }

    @Test
    public void boundListOperationTest() {
        // 定义绑定的键
        BoundListOperations boundListOperations = redisTemplate.boundListOps("lk");

        // 在绑定的键中添加值
        boundListOperations.leftPush("h");
        boundListOperations.leftPush("i");
        boundListOperations.rightPush("a");
        boundListOperations.rightPush("b");

        // 获取绑定的键中的值
        boundListOperations.range(0, -1).forEach(v -> System.out.println("获取绑定的键中的值" + v));

        // 获取特定位置的值
        System.out.println("获取特定位置的值：" + boundListOperations.index(0));

        // 弹出左边的值
        System.out.println("弹出左边的值:" + boundListOperations.leftPop());

        // 弹出右边的值
        System.out.println("弹出右边的值:" + boundListOperations.rightPop());

        // 在指定的出现第一个值左边添加值
        boundListOperations.leftPush("i", "w");
        boundListOperations.range(0, -1).forEach(v -> System.out.println("在指定的出现第一个值左边添加值：" + v));

        // 在指定的出现第一个值右边添加值
        boundListOperations.rightPush("i", "x");
        boundListOperations.range(0, -1).forEach(v -> System.out.println("在指定的出现第一个值左边添加值：" + v));

        // 在指定的时间过后弹出左边的值
        boundListOperations.leftPop(1, TimeUnit.SECONDS);

        // 在指定的时间过后弹出右边的值
        boundListOperations.rightPop(1, TimeUnit.SECONDS);

        // 在左边批量添加值
        boundListOperations.leftPushAll("y", "g");
        boundListOperations.range(0, -1).forEach(v -> System.out.println("在左边批量添加值：" + v));

        // 在右边批量添加值
        boundListOperations.rightPushAll("b", "r");
        boundListOperations.range(0, -1).forEach(v -> System.out.println("在右边批量添加值：" + v));

        // 向左边添加不存在的值
        boundListOperations.leftPushIfPresent("k");
        boundListOperations.leftPushIfPresent(";");
        boundListOperations.range(0, -1).forEach(v -> System.out.println("向左边添加不存在的值：" + v));

        // 向右边添加不存在的值
        boundListOperations.rightPushIfPresent("k");
        boundListOperations.rightPushIfPresent(";");
        System.out.println("向右边添加不存在的值：" + boundListOperations.range(0, -1).toString());

        // 移除指定值的个数
        long removeCount = boundListOperations.remove(2, "i");
        System.out.println("移除指定值的个数:" + removeCount);
        System.out.println("移除指定值的个数后剩余的值：" + boundListOperations.range(0, -1).toString());

        // 在指定位置设置值
        boundListOperations.set(0, "j");
        System.out.println("在指定位置设置值：" + boundListOperations.range(0, -1).toString());

        // 截取指定区间位置的值
        boundListOperations.trim(1, 3);
        System.out.println("截取指定区间位置的值：" + boundListOperations.range(0, -1).toString());
    }

    @Test
    public void boundSetOperationTest() {
        BoundSetOperations boundSetOperations = redisTemplate.boundSetOps("bso");

        // 添加新值后查看所有的值
        boundSetOperations.add("a", "b", "c");
        boundSetOperations.members().forEach(v -> System.out.println("添加新值后查看所有的值:" + v));

        // 遍历所有值
        Cursor<String> cursor = boundSetOperations.scan(ScanOptions.NONE);
        while (cursor.hasNext()) {
            System.out.println("遍历所有值:" + cursor.next());
        }

        System.out.println("随机获取一个值:" + boundSetOperations.randomMember());

        System.out.println("随机获取指定数量的值:" + boundSetOperations.randomMembers(2));

        System.out.println("获取唯一的随机数量值：" + boundSetOperations.distinctRandomMembers(2));

        // 这里比较的也应该是特定的集合名称，且名称不能和已经在比较的set集合中存在，否则报错
        Set list = new HashSet<>();
        list.add("bso1");
        boundSetOperations.diff(list).forEach(v -> System.out.println("比较给定集合中的set的不同元素:" + v));

        boundSetOperations.diff("bso2").forEach(v -> System.out.println("比较给定set的不同元素:" + v));

        // 比较不同集合并存储
        boundSetOperations.diffAndStore("bso2", "bso3");
        redisTemplate.opsForSet().members("bso3").forEach(v -> System.out.println("比较不同set并存储:" + v));
        boundSetOperations.diff("bso3").forEach(v -> System.out.println("比较给定set的不同元素:" + v));

        // 比较给定集合中的相同值
        boundSetOperations.intersect("bso2").forEach(v -> System.out.println("比较给定集合中的相同值:" + v));
        boundSetOperations.intersect(list).forEach(v -> System.out.println("比较给定集合中的相同值:" + v));

        // 比较给定集合中的相同值并存储
        boundSetOperations.intersectAndStore("bso3", "bso4");
        redisTemplate.opsForSet().members("bso4").forEach(v -> System.out.println("比较给定set的相同元素:" + v));

        // 将给定集合中的所有值合并
        boundSetOperations.union("bso2").forEach(v -> System.out.println("将给定集合中的所有值合并:" + v));
        boundSetOperations.union(list).forEach(v -> System.out.println("将给定集合中的所有值合并:" + v));

        boundSetOperations.unionAndStore("bso3", "bso5");
        redisTemplate.opsForSet().members("bso5").forEach(v -> System.out.println("将给定集合中的所有值合并:" + v));

        // 将集合中的value值移动到另外一个集合中
        boolean moveSuc = boundSetOperations.move("bso6", "a");
        System.out.println("将集合中的值移动到另外一个集合中是否成功:" + moveSuc);
        redisTemplate.opsForSet().members("bso6").forEach(v -> System.out.println("将集合中的值移动到另外一个集合中:" + v));
        boundSetOperations.members().forEach(v -> System.out.println("将集合中的值移动到另外一个集合中原集合剩余的值:" + v));

        // 弹出集合中的值
        Object p = boundSetOperations.pop();
        System.out.println("弹出集合中的值:" + p);
        boundSetOperations.members().forEach(v -> System.out.println("弹出集合中的值原集合剩余的值:" + v));

        // 移除特定元素
        long removeCount = boundSetOperations.remove("c");
        System.out.println("移除特定元素个数:" + removeCount);
        boundSetOperations.members().forEach(v -> System.out.println("移除特定元素个数后原集合剩余的值:" + v));
    }

    @Test
    public void boundValueOperationTest() {

        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps("bvo");
        boundValueOperations.append("a");
        boundValueOperations.append("b");

        // 获取从指定位置开始，到指定位置为止的值
        System.out.println("获取从指定位置开始，到指定位置为止的值:" + boundValueOperations.get(0, 2));

        // 获取所有值
        System.out.println("获取所有值:" + boundValueOperations.get());

        // 重新设置值
        boundValueOperations.set("f");
        System.out.println("重新设置值:" + boundValueOperations.get());

        // 在指定时间后重新设置
        boundValueOperations.set("wwww", 5, TimeUnit.SECONDS);
        System.out.println("在指定时间后重新设置:" + boundValueOperations.get());

        // 截取原有值的指定长度后添加新值在后面
        boundValueOperations.set("nnnnnn", 3);
        System.out.println("截取原有值的指定长度后添加新值在后面:" + boundValueOperations.get());

        // 没有值存在则添加
        boundValueOperations.setIfAbsent("mmm");
        System.out.println("没有值存在则添加:" + boundValueOperations.get());

        // 获取原来的值，并覆盖为新值
        Object object = boundValueOperations.getAndSet("yyy");
        System.out.print("获取原来的值" + object);
        System.out.println("，覆盖为新值:" + boundValueOperations.get());

        // 自增长只能在为数字类型的时候才可以
        // boundValueOperations.increment(1);
        // System.out.println("自增长只能在为数字类型的时候才可以:" + boundValueOperations.get());

        System.out.println("value值的长度:" + boundValueOperations.size());

    }

    @Test
    public void boundZSetOperationTest() {
        BoundZSetOperations boundZSetOperations = redisTemplate.boundZSetOps("bzso");

        boundZSetOperations.add("a", 1);
        boundZSetOperations.add("b", 2);

        // 集合中的值
        boundZSetOperations.range(0, -1).forEach(v -> System.out.println("集合中的值:" + v));

        // 获取从指定位置开始(下标起始坐标为1)到结束位置为止的值个数
        System.out.println("获取从指定位置开始(下标起始坐标为1)到结束位置为止的值个数:" + boundZSetOperations.count(1, 2));

        // 通过TypedTuple方式新增数据
        ZSetOperations.TypedTuple<Object> typedTuple1 = new DefaultTypedTuple<Object>("E", 6.0);
        ZSetOperations.TypedTuple<Object> typedTuple2 = new DefaultTypedTuple<Object>("F", 7.0);
        ZSetOperations.TypedTuple<Object> typedTuple3 = new DefaultTypedTuple<Object>("G", 5.0);
        Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = new HashSet<ZSetOperations.TypedTuple<Object>>();
        typedTupleSet.add(typedTuple1);
        typedTupleSet.add(typedTuple2);
        typedTupleSet.add(typedTuple3);
        boundZSetOperations.add(typedTupleSet);
        boundZSetOperations.range(0, -1).forEach(v -> System.out.println("通过TypedTuple方式新增数据:" + v));

        // 自增长后的数据
        boundZSetOperations.incrementScore("a", 1);
        boundZSetOperations.range(0, -1).forEach(v -> System.out.println("自增长后的数据:" + v));

        // 获取相同值，并存储
        boundZSetOperations.intersectAndStore("bzso1", "bzso2");
        redisTemplate.opsForZSet().range("bzso2", 0, -1).forEach(v -> System.out.println("获取相同值，并存储" + v));

        Cursor<ZSetOperations.TypedTuple> cursor = boundZSetOperations.scan(ScanOptions.NONE);
        while (cursor.hasNext()) {
            ZSetOperations.TypedTuple typedTuple = cursor.next();
            System.out.println("扫描绑定数据:" + typedTuple.getValue() + "--->" + typedTuple.getScore());
        }

        // 按照值来排序的取值,这个排序只有在有相同分数的情况下才能使用，如果有不同的分数则返回值不确定
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.lte("b");
        // range.gte("F");
        boundZSetOperations.rangeByLex(range).forEach(v -> System.out.println("按照值来排序的取值:" + v));

        // 获取从设置下标开始的设置长度的元素值
        RedisZSetCommands.Limit limit = new RedisZSetCommands.Limit();
        limit.count(2);
        // 起始下标为0
        limit.offset(1);
        boundZSetOperations.rangeByLex(range, limit).forEach(v -> System.out.println("按照值来排序的限定取值:" + v));

        // 按照分数排序
        boundZSetOperations.rangeByScore(3, 7).forEach(v -> System.out.println("按照分数排序:" + v));

        // 按照位置排序取值和分数
        Set<ZSetOperations.TypedTuple> tupleSet = boundZSetOperations.rangeWithScores(3, 5);
        tupleSet.forEach(v -> System.out.printf("按照位置排序取值和分数:%s-->%s\n", v.getValue(), v.getScore()));

        // 按照分数位置排序取值和分数
        Set<ZSetOperations.TypedTuple> scoreSet = boundZSetOperations.rangeByScoreWithScores(1, 5);
        scoreSet.forEach(v -> System.out.printf("按照分数位置排序取值和分数:%s-->%s\n", v.getValue(), v.getScore()));

        // 按照值来倒序取值
        boundZSetOperations.reverseRange(0, 3).forEach(v -> System.out.println("按照值来倒序取值:" + v));

        // 按照分数来倒序取值
        boundZSetOperations.reverseRangeByScore(2, 5).forEach(v -> System.out.println("按照分数来倒序取值:" + v));

        // 按照位置倒序取值和分数
        tupleSet = boundZSetOperations.reverseRangeWithScores(2, 5);
        tupleSet.forEach(v -> System.out.printf("按照位置倒序取值和分数:%s-->%s\n", v.getValue(), v.getScore()));

        // 按照分数位置倒序取值和分数
        scoreSet = boundZSetOperations.reverseRangeByScoreWithScores(2, 5);
        scoreSet.forEach(v -> System.out.printf("按照分数位置倒序取值和分数:%s-->%s\n", v.getValue(), v.getScore()));

        // 统计分数在某个区间的个数
        System.out.println("统计分数在某个区间的个数:" + boundZSetOperations.count(2, 5));

        // 获取变量中元素的索引,下标开始位置为0
        System.out.println("获取变量中元素的索引:" + boundZSetOperations.rank("b"));

        System.out.println("获取变量中元素的分数:" + boundZSetOperations.score("b"));

        // 获取变量中元素的个数
        System.out.println("获取变量中元素的个数:" + boundZSetOperations.zCard());

        // intersectAndStore后的数据
        boundZSetOperations.intersectAndStore("abc", "bac");
        redisTemplate.opsForSet().members("bac").forEach(v -> System.out.println("intersectAndStore后的数据:" + v));

        // intersectAndStore集合后的数据
        List list = new ArrayList<>(Arrays.asList("abc", "acb"));
        boundZSetOperations.intersectAndStore(list, "bac");
        redisTemplate.opsForSet().members("bac").forEach(v -> System.out.println("intersectAndStore集合后的数据:" + v));

        // unionAndStore后的数据
        boundZSetOperations.unionAndStore("abc", "bca");
        redisTemplate.opsForZSet().range("bca", 0, -1).forEach(v -> System.out.println("unionAndStore后的数据:" + v));

        // unionAndStore集合后的数据
        boundZSetOperations.unionAndStore(list, "bca");
        redisTemplate.opsForZSet().range("bca", 0, -1).forEach(v -> System.out.println("unionAndStore集合后的数据:" + v));

        // 移除给定值中的变量
        long removeCount = boundZSetOperations.remove("a", "b");
        System.out.println("移除给定值中的变量个数:" + removeCount);
        boundZSetOperations.range(0, -1).forEach(v -> System.out.println("移除给定值中的变量后剩余的值:" + v));

        // 移除区间值的变量
        boundZSetOperations.removeRange(2, 3);
        boundZSetOperations.range(0, -1).forEach(v -> System.out.println("移除区间值的变量后剩余的值:" + v));

        // 移除分数区间值的变量
        boundZSetOperations.removeRangeByScore(3, 5);
        boundZSetOperations.range(0, -1).forEach(v -> System.out.println("移除分数区间值的变量后剩余的值:" + v));

    }

}