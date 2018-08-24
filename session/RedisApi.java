package com.session.redis.api;



import java.util.List;

import redis.clients.jedis.Jedis;

public class RedisApi {
    private static Jedis jedis = new Jedis("192.168.38.129", 6379);
    /** 
     * jedis操作List 
     */
    
    public static void lpush(String key,String value) {
        jedis.lpush(key,value);
    }
    
    public static List<String> lrange(String key){
        List<String> lrange = jedis.lrange(key, 0, -1);
        return lrange;
    }
    



}
