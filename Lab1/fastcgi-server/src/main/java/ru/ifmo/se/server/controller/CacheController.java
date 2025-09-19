package ru.ifmo.se.server.controller;

import redis.clients.jedis.Jedis;
import ru.ifmo.se.server.dto.PointRequest;

public class CacheController {
    private final Jedis jedis = new Jedis("localhost", 6379);

    private final String TRUE_VALUE = "TRUE";
    private final String FALSE_VALUE = "FALSE";

    public Boolean getCache(PointRequest pointRequest) {
        jedis.connect();
        String value = jedis.get(pointRequest.toString());
        jedis.close();

        if (value == null) return null;
        return switch (value) {
            case TRUE_VALUE -> true;
            case FALSE_VALUE -> false;
            default -> null;
        };
    }

    public void setCache(PointRequest pointRequest, Boolean isPointInArea) {
        String key = pointRequest.toString();
        String value = isPointInArea ? TRUE_VALUE : FALSE_VALUE;

        jedis.connect();
        jedis.set(key, value);
        jedis.close();
    }
}
