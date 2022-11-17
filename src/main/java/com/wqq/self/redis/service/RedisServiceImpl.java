package com.wqq.self.redis.service;

import com.wqq.self.common.exception.BusinessException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate redisTemplate;


    @Override
    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Override
    public void setBoolean(String key, Boolean value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Override
    public void setBoolean(String key, Boolean value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

//    @Override
//    public void set(byte[] key, byte[] value){
//        redisTemplate.opsForValue().set(key,value);
//    }
//
//    @Override
//    public void set(byte[] key, byte[] value, Duration timeout){
//        redisTemplate.opsForValue().set(key,value,timeout);
//    }
//
//    @Override
//    public byte[] get(byte[] key){
//        return (byte[]) redisTemplate.opsForValue().get(key);
//    }
//
//    @Override
//    public boolean del(byte[] key) {
//        return redisTemplate.delete(key);
//    }

    @Override
    public byte[] get(byte[] key) {
        return (byte[]) redisTemplate.opsForValue().get(key);
    }

    @Override
    public byte[] getByte(String key) {
        return (byte[]) redisTemplate.opsForValue().get(key);
    }

    @Override
    public synchronized void switchDatabase(int DbIndex) {
        LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        //切换到指定的数据上
        connectionFactory.setDatabase(DbIndex);
        redisTemplate.setConnectionFactory(connectionFactory);
        //刷新配置
        connectionFactory.afterPropertiesSet();
        //重置连接
        connectionFactory.resetConnection();
    }

    @Override
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean getBoolean(String key) {
        return (Boolean) redisTemplate.opsForValue().get(key);
    }

    @Override
    public Integer getInteger(String key) {
        return (Integer) redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Long del(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public Boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Long decr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    @Override
    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public Integer hGetInteger(String key, String hashKey) {
        return (Integer) redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public Boolean hSetInteger(String key, String hashKey, Integer value, long time) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return expire(key, time);
    }

    @Override
    public void hSetInteger(String key, String hashKey, Integer value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public Boolean hSet(String key, String hashKey, Object value, long time) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return expire(key, time);
    }

    @Override
    public void hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Map<String, Integer> hGetAllInteger(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Boolean hSetAll(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        return expire(key, time);
    }

    @Override
    public void hSetAll(String key, Map<String, ?> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public <T> void hSetAll(String key, T t) {
        try {
            redisTemplate.opsForHash().putAll(key, BeanUtils.describe(t));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void hDel(String key, Object... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    @Override
    public Boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    @Override
    public Long hIncr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    @Override
    public Long hDecr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    @Override
    public <T> Set<T> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public Long sAdd(String key, long time, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        expire(key, time);
        return count;
    }

    @Override
    public <T> Long sAdd(String key, Long time, Collection<T> values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        expire(key, time);
        return count;
    }

    @Override
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    @Override
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    @Override
    public <T> List<T> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    @Override
    public Long lPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public Object lPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public Object blPop(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForList().leftPop(key, timeout, timeUnit);
    }

    @Override
    public Object rPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public Object brPop(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForList().rightPop(key, timeout, timeUnit);
    }

    @Override
    public Long lPush(String key, Object value, long time) {
        Long index = redisTemplate.opsForList().rightPush(key, value);
        expire(key, time);
        return index;
    }

    @Override
    public Long lPushAll(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public <T> Long lPushAll(String key, Collection<T> values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public <T> Long lPushAll(String key, Long time, Collection<T> values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        expire(key, time);
        return count;
    }

    @Override
    public Long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    @Override
    public void setKeyRedisSerializer(RedisSerializer redisSerializer) {
        redisTemplate.setKeySerializer(redisSerializer);
    }

    @Override
    public void setValueRedisSerializer(RedisSerializer redisSerializer) {
        redisTemplate.setValueSerializer(redisSerializer);
    }

    @Override
    public void setDefaultRedisSerializer(RedisSerializer redisSerializer) {
        redisTemplate.setDefaultSerializer(redisSerializer);
    }

    @Override
    public void setEnableDefaultSerializer(boolean enable) {
        redisTemplate.setEnableDefaultSerializer(enable);

    }

    @Override
    public void afterPropertiesSet(){
        redisTemplate.afterPropertiesSet();
    }

    @Override
    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void remove(String singleKey) {
        redisTemplate.delete(singleKey);
    }

    @Override
    public void setHashKeySerializer(RedisSerializer redisSerializer){
        redisTemplate.setHashKeySerializer(redisSerializer);
    }

    @Override
    public void setHashValueSerializer(RedisSerializer redisSerializer){
        redisTemplate.setHashValueSerializer(redisSerializer);
    }

    public void setStringSerializer(RedisSerializer redisSerializer){
        redisTemplate.setStringSerializer(redisSerializer);
    }

}
