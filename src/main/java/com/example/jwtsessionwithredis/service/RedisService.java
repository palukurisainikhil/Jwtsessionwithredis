package com.example.jwtsessionwithredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void insertData(String key, Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    public Object getData(String key){
        return redisTemplate.opsForValue().get(key);
    }
}
