package com.money.util;

import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final ValueOperations<String, String> valueOperations;

    public Boolean setDataIfAbsent(String key, String value, Duration expiredTime) {
        return valueOperations.setIfAbsent(key, value, expiredTime);
    }

    public Optional<String> getData(String key) {
        String value = valueOperations.get(key);

        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(value);
    }
}
