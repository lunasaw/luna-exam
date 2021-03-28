# Spring Boot缓存实战 Redis 设置有效时间和自动刷新缓存，时间支持在配置文件中配置



# 问题描述

Spring Cache提供的@Cacheable注解不支持配置过期时间，还有缓存的自动刷新。
我们可以通过配置CacheManneg来配置默认的过期时间和针对每个缓存容器（value）单独配置过期时间，但是总是感觉不太灵活。下面是一个示例:

```java
@Bean



public CacheManager cacheManager(RedisTemplate redisTemplate) {



    RedisCacheManager cacheManager= new RedisCacheManager(redisTemplate);



    cacheManager.setDefaultExpiration(60);



    Map<String,Long> expiresMap=new HashMap<>();



    expiresMap.put("Product",5L);



    cacheManager.setExpires(expiresMap);



    return cacheManager;



}
```

我们想在注解上直接配置过期时间和自动刷新时间，就像这样：

```java
@Cacheable(value = "people#120#90", key = "#person.id")



public Person findOne(Person person) {



    Person p = personRepository.findOne(person.getId());



    System.out.println("为id、key为:" + p.getId() + "数据做了缓存");



    return p;



}
```

value属性上用#号隔开，第一个是原始的缓存容器名称，第二个是缓存的有效时间，第三个是缓存的自动刷新时间，单位都是秒。

缓存的有效时间和自动刷新时间支持SpEl表达式，支持在配置文件中配置，如：

```java
@Cacheable(value = "people#${select.cache.timeout:1800}#${select.cache.refresh:600}", key = "#person.id", sync = true)//3



public Person findOne(Person person) {



    Person p = personRepository.findOne(person.getId());



    System.out.println("为id、key为:" + p.getId() + "数据做了缓存");



    return p;



}
```

# 解决思路

查看源码你会发现缓存最顶级的接口就是CacheManager和Cache接口。

## CacheManager说明

CacheManager功能其实很简单就是管理cache，接口只有两个方法，根据容器名称获取一个Cache。还有就是返回所有的缓存名称。

```java
public interface CacheManager {



 



    /**



     * 根据名称获取一个Cache（在实现类里面是如果有这个Cache就返回，没有就新建一个Cache放到Map容器中）



     * @param name the cache identifier (must not be {@code null})



     * @return the associated cache, or {@code null} if none found



     */



    Cache getCache(String name);



 



    /**



     * 返回一个缓存名称的集合



     * @return the names of all caches known by the cache manager



     */



    Collection<String> getCacheNames();



 



}
```

## Cache说明

Cache接口主要是操作缓存的。get根据缓存key从缓存服务器获取缓存中的值，put根据缓存key将数据放到缓存服务器，evict根据key删除缓存中的数据。

```java
public interface Cache {



 



    ValueWrapper get(Object key);



 



    void put(Object key, Object value);



 



    void evict(Object key);



 



    ...



}



 
```

## 请求步骤

1. 请求进来，在方法上面扫描@Cacheable注解，那么会触发org.springframework.cache.interceptor.CacheInterceptor缓存的拦截器。
2. 然后会调用CacheManager的getCache方法，获取Cache，如果没有（第一次访问）就新建一Cache并返回。
3. 根据获取到的Cache去调用get方法获取缓存中的值。RedisCache这里有个bug，源码是先判断key是否存在，再去缓存获取值，在高并发下有bug。

## 代码分析

在最上面我们说了Spring Cache可以通过配置CacheManager来配置过期时间。那么这个过期时间是在哪里用的呢？设置默认的时间setDefaultExpiration，根据特定名称设置有效时间setExpires，获取一个缓存名称（value属性）的有效时间computeExpiration，真正使用有效时间是在createCache方法里面，而这个方法是在父类的getCache方法调用。通过RedisCacheManager源码我们看到：

```java
// 设置默认的时间



public void setDefaultExpiration(long defaultExpireTime) {



    this.defaultExpiration = defaultExpireTime;



}



 



// 根据特定名称设置有效时间



public void setExpires(Map<String, Long> expires) {



    this.expires = (expires != null ? new ConcurrentHashMap<String, Long>(expires) : null);



}



// 获取一个key的有效时间



protected long computeExpiration(String name) {



    Long expiration = null;



    if (expires != null) {



        expiration = expires.get(name);



    }



    return (expiration != null ? expiration.longValue() : defaultExpiration);



}



 



@SuppressWarnings("unchecked")



protected RedisCache createCache(String cacheName) {



    // 调用了上面的方法获取缓存名称的有效时间



    long expiration = computeExpiration(cacheName);



    // 创建了Cache对象，并使用了这个有效时间



    return new RedisCache(cacheName, (usePrefix ? cachePrefix.prefix(cacheName) : null), redisOperations, expiration,



            cacheNullValues);



}



 



// 重写父类的getMissingCache。去创建Cache



@Override



protected Cache getMissingCache(String name) {



    return this.dynamic ? createCache(name) : null;



}
```

AbstractCacheManager父类源码：

```java
// 根据名称获取Cache如果没有调用getMissingCache方法，生成新的Cache，并将其放到Map容器中去。



@Override



public Cache getCache(String name) {



    Cache cache = this.cacheMap.get(name);



    if (cache != null) {



        return cache;



    }



    else {



        // Fully synchronize now for missing cache creation...



        synchronized (this.cacheMap) {



            cache = this.cacheMap.get(name);



            if (cache == null) {



                // 如果没找到Cache调用该方法，这个方法默认返回值NULL由子类自己实现。上面的就是子类自己实现的方法



                cache = getMissingCache(name);



                if (cache != null) {



                    cache = decorateCache(cache);



                    this.cacheMap.put(name, cache);



                    updateCacheNames(name);



                }



            }



            return cache;



        }



    }



}
```

由此这个有效时间的设置关键就是在getCache方法上，这里的name参数就是我们注解上的value属性。所以在这里解析这个特定格式的名称我就可以拿到配置的过期时间和刷新时间。getMissingCache方法里面在新建缓存的时候将这个过期时间设置进去，生成的Cache对象操作缓存的时候就会带上我们的配置的过期时间，然后过期就生效了。解析SpEL表达式获取配置文件中的时间也在也一步完成。

CustomizedRedisCacheManager源码：

```java
package com.xiaolyuh.redis.cache;



 



import com.xiaolyuh.redis.cache.helper.SpringContextHolder;



import com.xiaolyuh.redis.utils.ReflectionUtils;



import org.apache.commons.lang3.StringUtils;



import org.slf4j.Logger;



import org.slf4j.LoggerFactory;



import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.beans.factory.support.DefaultListableBeanFactory;



import org.springframework.cache.Cache;



import org.springframework.data.redis.cache.RedisCacheManager;



import org.springframework.data.redis.core.RedisOperations;



 



import java.util.Collection;



import java.util.concurrent.ConcurrentHashMap;



 



/**



 * 自定义的redis缓存管理器



 * 支持方法上配置过期时间



 * 支持热加载缓存：缓存即将过期时主动刷新缓存



 *



 * @author yuhao.wang



 */



public class CustomizedRedisCacheManager extends RedisCacheManager {



 



    private static final Logger logger = LoggerFactory.getLogger(CustomizedRedisCacheManager.class);



 



    /**



     * 父类cacheMap字段



     */



    private static final String SUPER_FIELD_CACHEMAP = "cacheMap";



 



    /**



     * 父类dynamic字段



     */



    private static final String SUPER_FIELD_DYNAMIC = "dynamic";



 



    /**



     * 父类cacheNullValues字段



     */



    private static final String SUPER_FIELD_CACHENULLVALUES = "cacheNullValues";



 



    /**



     * 父类updateCacheNames方法



     */



    private static final String SUPER_METHOD_UPDATECACHENAMES = "updateCacheNames";



 



    /**



     * 缓存参数的分隔符



     * 数组元素0=缓存的名称



     * 数组元素1=缓存过期时间TTL



     * 数组元素2=缓存在多少秒开始主动失效来强制刷新



     */



    private static final String SEPARATOR = "#";



 



    /**



     * SpEL标示符



     */



    private static final String MARK = "$";



 



    RedisCacheManager redisCacheManager = null;



 



    @Autowired



    DefaultListableBeanFactory beanFactory;



 



    public CustomizedRedisCacheManager(RedisOperations redisOperations) {



        super(redisOperations);



    }



 



    public CustomizedRedisCacheManager(RedisOperations redisOperations, Collection<String> cacheNames) {



        super(redisOperations, cacheNames);



    }



 



    public RedisCacheManager getInstance() {



        if (redisCacheManager == null) {



            redisCacheManager = SpringContextHolder.getBean(RedisCacheManager.class);



        }



        return redisCacheManager;



    }



 



    @Override



    public Cache getCache(String name) {



        String[] cacheParams = name.split(SEPARATOR);



        String cacheName = cacheParams[0];



 



        if (StringUtils.isBlank(cacheName)) {



            return null;



        }



 



        // 有效时间，初始化获取默认的有效时间



        Long expirationSecondTime = getExpirationSecondTime(cacheName, cacheParams);



        // 自动刷新时间，默认是0



        Long preloadSecondTime = getExpirationSecondTime(cacheParams);



 



        // 通过反射获取父类存放缓存的容器对象



        Object object = ReflectionUtils.getFieldValue(getInstance(), SUPER_FIELD_CACHEMAP);



        if (object != null && object instanceof ConcurrentHashMap) {



            ConcurrentHashMap<String, Cache> cacheMap = (ConcurrentHashMap<String, Cache>) object;



            // 生成Cache对象，并将其保存到父类的Cache容器中



            return getCache(cacheName, expirationSecondTime, preloadSecondTime, cacheMap);



        } else {



            return super.getCache(cacheName);



        }



 



    }



 



    /**



     * 获取过期时间



     *



     * @return



     */



    private long getExpirationSecondTime(String cacheName, String[] cacheParams) {



        // 有效时间，初始化获取默认的有效时间



        Long expirationSecondTime = this.computeExpiration(cacheName);



 



        // 设置key有效时间



        if (cacheParams.length > 1) {



            String expirationStr = cacheParams[1];



            if (!StringUtils.isEmpty(expirationStr)) {



                // 支持配置过期时间使用EL表达式读取配置文件时间



                if (expirationStr.contains(MARK)) {



                    expirationStr = beanFactory.resolveEmbeddedValue(expirationStr);



                }



                expirationSecondTime = Long.parseLong(expirationStr);



            }



        }



 



        return expirationSecondTime;



    }



 



    /**



     * 获取自动刷新时间



     *



     * @return



     */



    private long getExpirationSecondTime(String[] cacheParams) {



        // 自动刷新时间，默认是0



        Long preloadSecondTime = 0L;



        // 设置自动刷新时间



        if (cacheParams.length > 2) {



            String preloadStr = cacheParams[2];



            if (!StringUtils.isEmpty(preloadStr)) {



                // 支持配置刷新时间使用EL表达式读取配置文件时间



                if (preloadStr.contains(MARK)) {



                    preloadStr = beanFactory.resolveEmbeddedValue(preloadStr);



                }



                preloadSecondTime = Long.parseLong(preloadStr);



            }



        }



        return preloadSecondTime;



    }



 



    /**



     * 重写父类的getCache方法，真假了三个参数



     *



     * @param cacheName            缓存名称



     * @param expirationSecondTime 过期时间



     * @param preloadSecondTime    自动刷新时间



     * @param cacheMap             通过反射获取的父类的cacheMap对象



     * @return Cache



     */



    public Cache getCache(String cacheName, long expirationSecondTime, long preloadSecondTime, ConcurrentHashMap<String, Cache> cacheMap) {



        Cache cache = cacheMap.get(cacheName);



        if (cache != null) {



            return cache;



        } else {



            // Fully synchronize now for missing cache creation...



            synchronized (cacheMap) {



                cache = cacheMap.get(cacheName);



                if (cache == null) {



                    // 调用我们自己的getMissingCache方法创建自己的cache



                    cache = getMissingCache(cacheName, expirationSecondTime, preloadSecondTime);



                    if (cache != null) {



                        cache = decorateCache(cache);



                        cacheMap.put(cacheName, cache);



 



                        // 反射去执行父类的updateCacheNames(cacheName)方法



                        Class<?>[] parameterTypes = {String.class};



                        Object[] parameters = {cacheName};



                        ReflectionUtils.invokeMethod(getInstance(), SUPER_METHOD_UPDATECACHENAMES, parameterTypes, parameters);



                    }



                }



                return cache;



            }



        }



    }



 



    /**



     * 创建缓存



     *



     * @param cacheName            缓存名称



     * @param expirationSecondTime 过期时间



     * @param preloadSecondTime    制动刷新时间



     * @return



     */



    public CustomizedRedisCache getMissingCache(String cacheName, long expirationSecondTime, long preloadSecondTime) {



 



        logger.info("缓存 cacheName：{}，过期时间:{}, 自动刷新时间:{}", cacheName, expirationSecondTime, preloadSecondTime);



        Boolean dynamic = (Boolean) ReflectionUtils.getFieldValue(getInstance(), SUPER_FIELD_DYNAMIC);



        Boolean cacheNullValues = (Boolean) ReflectionUtils.getFieldValue(getInstance(), SUPER_FIELD_CACHENULLVALUES);



        return dynamic ? new CustomizedRedisCache(cacheName, (this.isUsePrefix() ? this.getCachePrefix().prefix(cacheName) : null),



                this.getRedisOperations(), expirationSecondTime, preloadSecondTime, cacheNullValues) : null;



    }



}
```

那自动刷新时间呢？

在RedisCache的属性里面没有刷新时间，所以我们继承该类重写我们自己的Cache的时候要多加一个属性preloadSecondTime来存储这个刷新时间。并在getMissingCache方法创建Cache对象的时候指定该值。

CustomizedRedisCache部分源码：

```java
/**



 * 缓存主动在失效前强制刷新缓存的时间



 * 单位：秒



 */



private long preloadSecondTime = 0;



 



// 重写后的构造方法



public CustomizedRedisCache(String name, byte[] prefix, RedisOperations<? extends Object, ? extends Object> redisOperations, long expiration, long preloadSecondTime) {



    super(name, prefix, redisOperations, expiration);



    this.redisOperations = redisOperations;



    // 指定自动刷新时间



    this.preloadSecondTime = preloadSecondTime;



    this.prefix = prefix;



}



 



// 重写后的构造方法



public CustomizedRedisCache(String name, byte[] prefix, RedisOperations<? extends Object, ? extends Object> redisOperations, long expiration, long preloadSecondTime, boolean allowNullValues) {



    super(name, prefix, redisOperations, expiration, allowNullValues);



    this.redisOperations = redisOperations;



    // 指定自动刷新时间



    this.preloadSecondTime = preloadSecondTime;



    this.prefix = prefix;



}
```

那么这个自动刷新时间有了，怎么来让他自动刷新呢？

在调用Cache的get方法的时候我们都会去缓存服务查询缓存，这个时候我们在多查一个缓存的有效时间，和我们配置的自动刷新时间对比，如果缓存的有效时间小于这个自动刷新时间我们就去刷新缓存（这里注意一点在高并发下我们最好只放一个请求去刷新数据，尽量减少数据的压力，所以在这个位置加一个分布式锁）。所以我们重写这个get方法。

CustomizedRedisCache部分源码：

```java
/**



 * 重写get方法，获取到缓存后再次取缓存剩余的时间，如果时间小余我们配置的刷新时间就手动刷新缓存。



 * 为了不影响get的性能，启用后台线程去完成缓存的刷。



 * 并且只放一个线程去刷新数据。



 *



 * @param key



 * @return



 */



@Override



public ValueWrapper get(final Object key) {



    RedisCacheKey cacheKey = getRedisCacheKey(key);



    String cacheKeyStr = new String(cacheKey.getKeyBytes());



    // 调用重写后的get方法



    ValueWrapper valueWrapper = this.get(cacheKey);



 



    if (null != valueWrapper) {



        // 刷新缓存数据



        refreshCache(key, cacheKeyStr);



    }



    return valueWrapper;



}



 



/**



 * 重写父类的get函数。



 * 父类的get方法，是先使用exists判断key是否存在，不存在返回null，存在再到redis缓存中去取值。这样会导致并发问题，



 * 假如有一个请求调用了exists函数判断key存在，但是在下一时刻这个缓存过期了，或者被删掉了。



 * 这时候再去缓存中获取值的时候返回的就是null了。



 * 可以先获取缓存的值，再去判断key是否存在。



 *



 * @param cacheKey



 * @return



 */



@Override



public RedisCacheElement get(final RedisCacheKey cacheKey) {



 



    Assert.notNull(cacheKey, "CacheKey must not be null!");



 



    // 根据key获取缓存值



    RedisCacheElement redisCacheElement = new RedisCacheElement(cacheKey, fromStoreValue(lookup(cacheKey)));



    // 判断key是否存在



    Boolean exists = (Boolean) redisOperations.execute(new RedisCallback<Boolean>() {



 



        @Override



        public Boolean doInRedis(RedisConnection connection) throws DataAccessException {



            return connection.exists(cacheKey.getKeyBytes());



        }



    });



 



    if (!exists.booleanValue()) {



        return null;



    }



 



    return redisCacheElement;



}



 



/**



 * 刷新缓存数据



 */



private void refreshCache(Object key, String cacheKeyStr) {



    Long ttl = this.redisOperations.getExpire(cacheKeyStr);



    if (null != ttl && ttl <= CustomizedRedisCache.this.preloadSecondTime) {



        // 尽量少的去开启线程，因为线程池是有限的



        ThreadTaskHelper.run(new Runnable() {



            @Override



            public void run() {



                // 加一个分布式锁，只放一个请求去刷新缓存



                RedisLock redisLock = new RedisLock((RedisTemplate) redisOperations, cacheKeyStr + "_lock");



                try {



                    if (redisLock.lock()) {



                        // 获取锁之后再判断一下过期时间，看是否需要加载数据



                        Long ttl = CustomizedRedisCache.this.redisOperations.getExpire(cacheKeyStr);



                        if (null != ttl && ttl <= CustomizedRedisCache.this.preloadSecondTime) {



                            // 通过获取代理方法信息重新加载缓存数据



                            CustomizedRedisCache.this.getCacheSupport().refreshCacheByKey(CustomizedRedisCache.super.getName(), key.toString());



                        }



                    }



                } catch (Exception e) {



                    logger.info(e.getMessage(), e);



                } finally {



                    redisLock.unlock();



                }



            }



        });



    }



}
```

那么自动刷新肯定要掉用方法访问数据库，获取值后去刷新缓存。这时我们又怎么能去调用方法呢？

我们利用java的反射机制。所以我们要用一个容器来存放缓存方法的方法信息，包括对象，方法名称，参数等等。我们创建了CachedInvocation类来存放这些信息，再将这个类的对象维护到容器中。

CachedInvocation源码：

```java
public final class CachedInvocation {



 



    private Object key;



    private final Object targetBean;



    private final Method targetMethod;



    private Object[] arguments;



 



    public CachedInvocation(Object key, Object targetBean, Method targetMethod, Object[] arguments) {



        this.key = key;



        this.targetBean = targetBean;



        this.targetMethod = targetMethod;



        if (arguments != null && arguments.length != 0) {



            this.arguments = Arrays.copyOf(arguments, arguments.length);



        }



    }



 



    public Object[] getArguments() {



        return arguments;



    }



 



    public Object getTargetBean() {



        return targetBean;



    }



 



    public Method getTargetMethod() {



        return targetMethod;



    }



 



    public Object getKey() {



        return key;



    }



 



    /**



     * 必须重写equals和hashCode方法，否则放到set集合里没法去重



     * @param o



     * @return



     */



    @Override



    public boolean equals(Object o) {



        if (this == o) {



            return true;



        }



        if (o == null || getClass() != o.getClass()) {



            return false;



        }



 



        CachedInvocation that = (CachedInvocation) o;



 



        return key.equals(that.key);



    }



 



    @Override



    public int hashCode() {



        return key.hashCode();



    }



}
```

维护缓存方法信息的容器和刷新缓存的类CacheSupportImpl 关键代码：

```java
private final String SEPARATOR = "#";



 



/**



 * 记录缓存执行方法信息的容器。



 * 如果有很多无用的缓存数据的话，有可能会照成内存溢出。



 */



private Map<String, Set<CachedInvocation>> cacheToInvocationsMap = new ConcurrentHashMap<>();



 



@Autowired



private CacheManager cacheManager;



 



// 刷新缓存



private void refreshCache(CachedInvocation invocation, String cacheName) {



 



    boolean invocationSuccess;



    Object computed = null;



    try {



        // 通过代理调用方法，并记录返回值



        computed = invoke(invocation);



        invocationSuccess = true;



    } catch (Exception ex) {



        invocationSuccess = false;



    }



    if (invocationSuccess) {



        if (!CollectionUtils.isEmpty(cacheToInvocationsMap.get(cacheName))) {



            // 通过cacheManager获取操作缓存的cache对象



            Cache cache = cacheManager.getCache(cacheName);



            // 通过Cache对象更新缓存



            cache.put(invocation.getKey(), computed);



        }



    }



}



 



private Object invoke(CachedInvocation invocation)



        throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {



 



    final MethodInvoker invoker = new MethodInvoker();



    invoker.setTargetObject(invocation.getTargetBean());



    invoker.setArguments(invocation.getArguments());



    invoker.setTargetMethod(invocation.getTargetMethod().getName());



    invoker.prepare();



 



    return invoker.invoke();



}



 



// 注册缓存方法的执行类信息



@Override



public void registerInvocation(Object targetBean, Method targetMethod, Object[] arguments,



        Set<String> annotatedCacheNames, String cacheKey) {



 



    // 获取注解上真实的value值



    Collection<String> cacheNames = generateValue(annotatedCacheNames);



 



    // 获取注解上的key属性值



    Class<?> targetClass = getTargetClass(targetBean);



    Collection<? extends Cache> caches = getCache(cacheNames);



    Object key = generateKey(caches, cacheKey, targetMethod, arguments, targetBean, targetClass,



            CacheOperationExpressionEvaluator.NO_RESULT);



 



    // 新建一个代理对象（记录了缓存注解的方法类信息）



    final CachedInvocation invocation = new CachedInvocation(key, targetBean, targetMethod, arguments);



    for (final String cacheName : cacheNames) {



        if (!cacheToInvocationsMap.containsKey(cacheName)) {



            cacheToInvocationsMap.put(cacheName, new CopyOnWriteArraySet<>());



        }



        cacheToInvocationsMap.get(cacheName).add(invocation);



    }



}



 



@Override



public void refreshCache(String cacheName) {



    this.refreshCacheByKey(cacheName, null);



}



 



 



// 刷新特定key缓存



@Override



public void refreshCacheByKey(String cacheName, String cacheKey) {



    // 如果根据缓存名称没有找到代理信息类的set集合就不执行刷新操作。



    // 只有等缓存有效时间过了，再走到切面哪里然后把代理方法信息注册到这里来。



    if (!CollectionUtils.isEmpty(cacheToInvocationsMap.get(cacheName))) {



        for (final CachedInvocation invocation : cacheToInvocationsMap.get(cacheName)) {



            if (!StringUtils.isBlank(cacheKey) && invocation.getKey().toString().equals(cacheKey)) {



                logger.info("缓存：{}-{}，重新加载数据", cacheName, cacheKey.getBytes());



                refreshCache(invocation, cacheName);



            }



        }



    }



}



 
```

现在刷新缓存和注册缓存执行方法的信息都有了，我们怎么来把这个执行方法信息注册到容器里面呢？这里还少了触发点。

所以我们还需要一个切面，当执行@Cacheable注解获取缓存信息的时候我们还需要注册执行方法的信息，所以我们写了一个切面：

```java
/**



 * 缓存拦截，用于注册方法信息



 * @author yuhao.wang



 */



@Aspect



@Component



public class CachingAnnotationsAspect {



 



    private static final Logger logger = LoggerFactory.getLogger(CachingAnnotationsAspect.class);



 



    @Autowired



    private InvocationRegistry cacheRefreshSupport;



 



    private <T extends Annotation> List<T> getMethodAnnotations(AnnotatedElement ae, Class<T> annotationType) {



        List<T> anns = new ArrayList<T>(2);



        // look for raw annotation



        T ann = ae.getAnnotation(annotationType);



        if (ann != null) {



            anns.add(ann);



        }



        // look for meta-annotations



        for (Annotation metaAnn : ae.getAnnotations()) {



            ann = metaAnn.annotationType().getAnnotation(annotationType);



            if (ann != null) {



                anns.add(ann);



            }



        }



        return (anns.isEmpty() ? null : anns);



    }



 



    private Method getSpecificmethod(ProceedingJoinPoint pjp) {



        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();



        Method method = methodSignature.getMethod();



        // The method may be on an interface, but we need attributes from the



        // target class. If the target class is null, the method will be



        // unchanged.



        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(pjp.getTarget());



        if (targetClass == null && pjp.getTarget() != null) {



            targetClass = pjp.getTarget().getClass();



        }



        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);



        // If we are dealing with method with generic parameters, find the



        // original method.



        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);



        return specificMethod;



    }



 



    @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)")



    public void pointcut() {



    }



 



    @Around("pointcut()")



    public Object registerInvocation(ProceedingJoinPoint joinPoint) throws Throwable {



 



        Method method = this.getSpecificmethod(joinPoint);



 



        List<Cacheable> annotations = this.getMethodAnnotations(method, Cacheable.class);



 



        Set<String> cacheSet = new HashSet<String>();



        String cacheKey = null;



        for (Cacheable cacheables : annotations) {



            cacheSet.addAll(Arrays.asList(cacheables.value()));



            cacheKey = cacheables.key();



        }



        cacheRefreshSupport.registerInvocation(joinPoint.getTarget(), method, joinPoint.getArgs(), cacheSet, cacheKey);



        return joinPoint.proceed();



 



    }



}
```

 

**注意：一个缓存名称（@Cacheable的value属性），也只能配置一个过期时间，如果配置多个以第一次配置的为准。**

至此我们就把完整的设置过期时间和刷新缓存都实现了，当然还可能存在一定问题，希望大家多多指教。

使用这种方式有个不好的地方，我们破坏了Spring Cache的结构，导致我们切换Cache的方式的时候要改代码，有很大的依赖性。

下一篇我将对 redisCacheManager.setExpires()方法进行扩展来实现过期时间和自动刷新，进而不会去破坏Spring Cache的原有结构，切换缓存就不会有问题了。

 

代码结构图：

 

 

 ![aHR0cDovL3VwbG9hZC1pbWFnZXMuamlhbnNodS5pby91cGxvYWRfaW1hZ2VzLzY0NjQwODYtMmQzZGM4ODllNTIzZjA3MA](Spring Boot缓存实战 Redis 设置有效时间和自动刷新缓存，时间支持在配置文件中配置.assets/aHR0cDovL3VwbG9hZC1pbWFnZXMuamlhbnNodS5pby91cGxvYWRfaW1hZ2VzLzY0NjQwODYtMmQzZGM4ODllNTIzZjA3MA.png)

