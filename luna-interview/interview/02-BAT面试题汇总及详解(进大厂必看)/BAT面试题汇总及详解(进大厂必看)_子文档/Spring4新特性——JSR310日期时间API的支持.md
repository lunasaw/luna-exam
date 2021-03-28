# Spring4新特性——JSR310日期时间API的支持



JSR310 日期与时间规范主要API如下：

**Clock**

时钟，类似于钟表的概念，提供了如系统时钟、固定时钟、特定时区的时钟

```
        //时钟提供给我们用于访问某个特定 时区的 瞬时时间、日期 和 时间的。
        Clock c1 = Clock.systemUTC(); //系统默认UTC时钟（当前瞬时时间 System.currentTimeMillis()）
        System.out.println(c1.millis()); //每次调用将返回当前瞬时时间（UTC）

        Clock c2 = Clock.systemDefaultZone(); //系统默认时区时钟（当前瞬时时间）

        Clock c31 = Clock.system(ZoneId.of("Europe/Paris")); //巴黎时区
        System.out.println(c31.millis()); //每次调用将返回当前瞬时时间（UTC）

        Clock c32 = Clock.system(ZoneId.of("Asia/Shanghai"));//上海时区
        System.out.println(c32.millis());//每次调用将返回当前瞬时时间（UTC）

        Clock c4 = Clock.fixed(Instant.now(), ZoneId.of("Asia/Shanghai"));//固定上海时区时钟
        System.out.println(c4.millis());
        Thread.sleep(1000);
        System.out.println(c4.millis()); //不变 即时钟时钟在那一个点不动

        Clock c5 = Clock.offset(c1, Duration.ofSeconds(2)); //相对于系统默认时钟两秒的时钟
        System.out.println(c1.millis());
        System.out.println(c5.millis());
```

 

 

**Instant**

瞬时时间，等价于以前的System.currentTimeMillis()

```
        //瞬时时间 相当于以前的System.currentTimeMillis()
        Instant instant1 = Instant.now();
        System.out.println(instant1.getEpochSecond());//精确到秒 得到相对于1970-01-01 00:00:00 UTC的一个时间
        System.out.println(instant1.toEpochMilli()); //精确到毫秒

        Clock clock1 = Clock.systemUTC(); //获取系统UTC默认时钟
        Instant instant2 = Instant.now(clock1);//得到时钟的瞬时时间
        System.out.println(instant2.toEpochMilli());

        Clock clock2 = Clock.fixed(instant1, ZoneId.systemDefault()); //固定瞬时时间时钟
        Instant instant3 = Instant.now(clock2);//得到时钟的瞬时时间
        System.out.println(instant3.toEpochMilli());//equals instant1
```

 

**LocalDateTime、LocalDate、LocalTime** 

提供了对java.util.Date的替代，另外还提供了新的DateTimeFormatter用于对格式化/解析的支持

```
        //使用默认时区时钟瞬时时间创建 Clock.systemDefaultZone() -->即相对于 ZoneId.systemDefault()默认时区
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        //自定义时区
        LocalDateTime now2= LocalDateTime.now(ZoneId.of("Europe/Paris"));
        System.out.println(now2);//会以相应的时区显示日期

        //自定义时钟
        Clock clock = Clock.system(ZoneId.of("Asia/Dhaka"));
        LocalDateTime now3= LocalDateTime.now(clock);
        System.out.println(now3);//会以相应的时区显示日期

        //不需要写什么相对时间 如java.util.Date 年是相对于1900 月是从0开始
        //2013-12-31 23:59
        LocalDateTime d1 = LocalDateTime.of(2013, 12, 31, 23, 59);

        //年月日 时分秒 纳秒
        LocalDateTime d2 = LocalDateTime.of(2013, 12, 31, 23, 59,59, 11);

        //使用瞬时时间 + 时区
        Instant instant = Instant.now();
        LocalDateTime d3 = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        System.out.println(d3);

        //解析String--->LocalDateTime
        LocalDateTime d4 = LocalDateTime.parse("2013-12-31T23:59");
        System.out.println(d4);

        LocalDateTime d5 = LocalDateTime.parse("2013-12-31T23:59:59.999");//999毫秒 等价于999000000纳秒
        System.out.println(d5);

        //使用DateTimeFormatter API 解析 和 格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime d6 = LocalDateTime.parse("2013/12/31 23:59:59", formatter);
        System.out.println(formatter.format(d6));


        //时间获取
        System.out.println(d6.getYear());
        System.out.println(d6.getMonth());
        System.out.println(d6.getDayOfYear());
        System.out.println(d6.getDayOfMonth());
        System.out.println(d6.getDayOfWeek());
        System.out.println(d6.getHour());
        System.out.println(d6.getMinute());
        System.out.println(d6.getSecond());
        System.out.println(d6.getNano());
        //时间增减
        LocalDateTime d7 = d6.minusDays(1);
        LocalDateTime d8 = d7.plus(1, IsoFields.QUARTER_YEARS);

        //LocalDate 即年月日 无时分秒
        //LocalTime即时分秒 无年月日
        //API和LocalDateTime类似就不演示了
```

 

**ZonedDateTime**

带有时区的date-time 存储纳秒、时区和时差（避免与本地date-time歧义）；API和LocalDateTime类似，只是多了时差(如2013-12-20T10:35:50.711+08:00[Asia/Shanghai])  

```
        //即带有时区的date-time 存储纳秒、时区和时差（避免与本地date-time歧义）。
        //API和LocalDateTime类似，只是多了时差(如2013-12-20T10:35:50.711+08:00[Asia/Shanghai])
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);

        ZonedDateTime now2= ZonedDateTime.now(ZoneId.of("Europe/Paris"));
        System.out.println(now2);

        //其他的用法也是类似的 就不介绍了

        ZonedDateTime z1 = ZonedDateTime.parse("2013-12-31T23:59:59Z[Europe/Paris]");
        System.out.println(z1);
```

 

 

**Duration**

表示两个瞬时时间的时间段 

 

```
        //表示两个瞬时时间的时间段
        Duration d1 = Duration.between(Instant.ofEpochMilli(System.currentTimeMillis() - 12323123), Instant.now());
        //得到相应的时差
        System.out.println(d1.toDays());
        System.out.println(d1.toHours());
        System.out.println(d1.toMinutes());
        System.out.println(d1.toMillis());
        System.out.println(d1.toNanos());

        //1天时差 类似的还有如ofHours()
        Duration d2 = Duration.ofDays(1);
        System.out.println(d2.toDays());
```

 

 

Chronology

用于对年历系统的支持，是java.util.Calendar的替代者

```
        //提供对java.util.Calendar的替换，提供对年历系统的支持
        Chronology c  = HijrahChronology.INSTANCE;
        ChronoLocalDateTime d = c.localDateTime(LocalDateTime.now());
        System.out.println(d);
```

 

**其他**

如果提供了年、年月、月日、周期的API支持

```
        Year year = Year.now();
        YearMonth yearMonth = YearMonth.now();
        MonthDay monthDay = MonthDay.now();

        System.out.println(year);//年
        System.out.println(yearMonth); //年-月
        System.out.println(monthDay); // 月-日

        //周期，如表示10天前  3年5个月钱
        Period period1 = Period.ofDays(10);
        System.out.println(period1);
        Period period2 = Period.of(3, 5, 0);
        System.out.println(period2);
```



我们只需要在实体/Bean上使用DateTimeFormat注解：

```
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;
```

比如我们在springmvc中：

```
    @RequestMapping("/test")
    public String test(@ModelAttribute("entity") Entity entity) {
        return "test";
    }
```

当前端页面请求：

localhost:9080/spring4/test?dateTime=2013-11-11 11:11:11&date=2013-11-11&time=12:12:12

会自动进行类型转换。

 

另外spring4也提供了对TimeZone的支持；比如在springmvc中注册了LocaleContextResolver相应实现的话（如CookieLocaleResolver），我们就可以使用如下两种方式得到相应的TimeZone：

RequestContextUtils.getTimeZone(request)

LocaleContextHolder.getTimeZone()

 

不过目前的缺点是不能像Local那样自动的根据当前请求得到相应的TimeZone，如果需要这种功能需要覆盖相应的如CookieLocaleResolver中的如下方法来得到：

```
	protected TimeZone determineDefaultTimeZone(HttpServletRequest request) {
		return getDefaultTimeZone();
	}
```

 

另外还提供了DateTimeContextHolder，其用于线程绑定DateTimeContext；而DateTimeContext提供了如：Chronology、ZoneId、DateTimeFormatter等上下文数据，如果需要这种上下文信息的话，可以使用这个API进行绑定。比如在进行日期格式化时，就会去查找相应的DateTimeFormatter，因此如果想自定义相应的格式化格式，那么使用DateTimeContextHolder绑定即可。 

spring4只是简单的对jsr310提供了相应的支持，没有太多的增强。

 