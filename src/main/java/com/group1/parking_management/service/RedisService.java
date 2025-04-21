package com.group1.parking_management.service;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public void addToBlacklist(String token, long expirationTime) {
        redisTemplate.opsForValue().set(token, "Blacklisted", expirationTime, TimeUnit.SECONDS);
    }

    public void addResetToken(String token, String value) {
        redisTemplate.opsForValue().set("reset_token:" + token, value, 15, TimeUnit.MINUTES);
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    public boolean isTokenBacklisted(String token) {
        if (token == null) return false;
        Boolean result = redisTemplate.hasKey(token);
        return Boolean.TRUE.equals(result);
    }
}
