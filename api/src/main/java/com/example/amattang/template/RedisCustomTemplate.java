package com.example.amattang.template;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisCustomTemplate {

    private final StringRedisTemplate redisTemplate;

    public void timeoutTemplate(String key, String value, long timeout, TimeUnit unit) {
        ValueOperations<String, String> template = redisTemplate.opsForValue();
        template.set(key, value, timeout, unit);
    }

    public String getRedisStringValue(String key) {
        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        return stringValueOperations.get(key);
    }

    public void deleteRedisStringValue(String key) {
        redisTemplate.delete(key);
    }
}
