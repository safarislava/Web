package ru.ifmo.se.api.services;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import ru.ifmo.se.api.entities.AbstractPoint;

import java.util.Optional;

@Service
public class CacheService {
    private final Jedis jedis = new Jedis("localhost", 6379);

    private final String TRUE_VALUE = "TRUE";
    private final String FALSE_VALUE = "FALSE";

    public Optional<Boolean> getCache(AbstractPoint abstractPoint) {
        try {
            jedis.connect();
            String value = jedis.get(abstractPoint.toString());
            jedis.close();

            return switch (value) {
                case TRUE_VALUE -> Optional.of(Boolean.TRUE);
                case FALSE_VALUE -> Optional.of(Boolean.FALSE);
                default -> Optional.empty();
            };
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    public void setCache(AbstractPoint abstractPoint, Boolean hit) {
        String key = abstractPoint.toString();
        String value = hit ? TRUE_VALUE : FALSE_VALUE;

        try {
            jedis.connect();
            jedis.set(key, value);
            jedis.close();
        }
        catch (Exception ignored) {}
    }
}
