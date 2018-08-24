package com.redis.cache;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
         解决高并发下缓存穿透模板                                    
 * @describe
 * @author yxd
 * @data 2018-08-24 15:02:55
 *
 */
@Component
public class CacheServiceTemplate {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    
    public <T> T findCache(String key, long expire, TimeUnit unit,TypeReference<T> clazz, CacheLoadble<T> cacheLoadable) {
        ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
        //查询缓存
        String json = String.valueOf(ops.get(key));
        //判断是否存在数据，如果有数据，则返回
        if(StringUtils.isNotEmpty(json)&&!"null".equalsIgnoreCase(json)) {
            System.out.println("查询缓存");
            return JSON.parseObject(json,clazz);
        }
        
        //双重检测锁  单例模式
        synchronized (this) {
            json = String.valueOf(ops.get(key));
            //判断是否存在数据，如果有数据，则返回
            if(StringUtils.isNotEmpty(json)&&!"null".equalsIgnoreCase(json)) {
                System.out.println("查询缓存");
                return JSON.parseObject(json, clazz);
            }
            //如果缓存中没有数据，则从DB中获取，并且同步到缓存中
            System.out.println("查询数据库");
            //load()方法是业务逻辑的具体实现
            T result = cacheLoadable.load();
            //序列化key
            RedisSerializer<String> redisSerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(redisSerializer);
            ops.set(key, JSON.toJSON(result));
            return result;
        }
        
    }
}
