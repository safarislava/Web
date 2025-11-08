package ru.ifmo.se.api.pointchecker.services;


import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import ru.ifmo.se.api.pointchecker.entities.AbstractPoint;

@Service
public class CacheService {
    private final Jedis jedis = new Jedis("localhost", 6379);

    private final String TRUE_VALUE = "TRUE";
    private final String FALSE_VALUE = "FALSE";

    public Boolean getCache(AbstractPoint abstractPoint) {
        String value;
        try {
            jedis.connect();
            value = jedis.get(abstractPoint.toString());
            jedis.close();
        }
        catch (Exception e) {
            return null;
        }

        if (value == null) return null;
        return switch (value) {
            case TRUE_VALUE -> true;
            case FALSE_VALUE -> false;
            default -> null;
        };
    }

    public void setCache(AbstractPoint abstractPoint, Boolean isPointInArea) {
        String key = abstractPoint.toString();
        String value = isPointInArea ? TRUE_VALUE : FALSE_VALUE;

        try {
            jedis.connect();
            jedis.set(key, value);
            jedis.close();
        } catch (Exception ignored) {}
    }
}
