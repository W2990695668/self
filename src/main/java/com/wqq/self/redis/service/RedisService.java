package com.wqq.self.redis.service;

import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {

    /**
     * 保存属性
     */
    void set(String key, Object value, long time);

    void setBoolean(String key, Boolean value, long time);

    void setBoolean(String key, Boolean value);

    /**
     * 保存属性
     */
    void set(String key, Object value);


//    void set(byte[] key, byte[] value);
//
//    void set(byte[] key, byte[] value, Duration timeout);
//
//    byte[] get(byte[] key);
//
//    boolean del(byte[] key);

    byte[] get(byte[] key);

    byte[] getByte(String key);

    void switchDatabase(int DbIndex);

    /**
     * 获取属性
     */
    Object get(String key);

    Boolean getBoolean(String key);

    Integer getInteger(String key);

    /**
     * 删除属性
     */
    Boolean del(String key);

    /**
     * 批量删除属性
     */
    Long del(List<String> keys);

    /**
     * 设置过期时间
     */
    Boolean expire(String key, long time);

    /**
     * 获取过期时间
     */
    Long getExpire(String key);

    /**
     * 判断是否有该属性
     */
    Boolean hasKey(String key);

    /**
     * 按delta递增
     */
    Long incr(String key, long delta);

    /**
     * 按delta递减
     */
    Long decr(String key, long delta);

    /**
     * 获取Hash结构中的属性
     */
    Object hGet(String key, String hashKey);

    Integer hGetInteger(String key, String hashKey);

    Boolean hSetInteger(String key, String hashKey, Integer value, long time);

    void hSetInteger(String key, String hashKey, Integer value);

    /**
     * 向Hash结构中放入一个属性
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     */
    Map<Object, Object> hGetAll(String key);

    Map<String, Integer> hGetAllInteger(String key);

    /**
     * 直接设置整个Hash结构
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * 直接设置整个Hash结构
     */
    void hSetAll(String key, Map<String, ?> map);

    /**
     * 直接设置整个Hash结构
     */
    <T> void hSetAll(String key, T t);

    /**
     * 删除Hash结构中的属性
     */
    void hDel(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     */
    <T> Set<T> sMembers(String key);

    /**
     * 向Set结构中添加属性
     */
    Long sAdd(String key, Object... values);

    /**
     * 向Set结构中添加属性
     */
    Long sAdd(String key, long time, Object... values);

    <T> Long sAdd(String key, Long time, Collection<T> values);

    /**
     * 是否为Set中的属性
     */
    Boolean sIsMember(String key, Object value);

    /**
     * 获取Set结构的长度
     */
    Long sSize(String key);

    /**
     * 删除Set结构中的属性
     */
    Long sRemove(String key, Object... values);

    /**
     * 获取List结构中的属性
     */
    <T> List<T> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     */
    Object lIndex(String key, long index);

    Long lPush(String key, Object value);

    Object lPop(String key);

    Object blPop(String key, long timeout, TimeUnit timeUnit);

    Object rPop(String key);

    Object brPop(String key, long timeout, TimeUnit timeUnit);

    /**
     * 向List结构中添加属性
     */
    Long lPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     */
    Long lPushAll(String key, Object... values);


    public <T> Long lPushAll(String key, Collection<T> values);

    /**
     * 向List结构中批量添加属性
     */
    <T> Long lPushAll(String key, Long time, Collection<T> values);

    /**
     * 从List结构中移除属性
     */
    Long lRemove(String key, long count, Object value);

    void setKeyRedisSerializer(RedisSerializer redisSerializer);

    void setValueRedisSerializer(RedisSerializer redisSerializer);

    void setDefaultRedisSerializer(RedisSerializer redisSerializer);

    void setHashKeySerializer(RedisSerializer redisSerializer);

    void setHashValueSerializer(RedisSerializer redisSerializer);

    void setStringSerializer(RedisSerializer redisSerializer);

    void setEnableDefaultSerializer(boolean enable);

    void afterPropertiesSet();

    boolean existsKey(String key);

    void remove(String singleKey);
}