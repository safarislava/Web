package ru.ifmo.se.webapp.controller;

import redis.clients.jedis.Jedis;
import ru.ifmo.se.webapp.dto.PointRequest;

public class CacheController {
    private final Jedis jedis = new Jedis("localhost", 6379);

    private final String TRUE_VALUE = "TRUE";
    private final String FALSE_VALUE = "FALSE";

    public Boolean getCache(PointRequest pointRequest) {
        String value;
        try {
            jedis.connect();
            value = jedis.get(pointRequest.toString());
        }
        finally {
            jedis.close();
        }

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

        try {
            jedis.connect();
            jedis.set(key, value);
        }
        finally {
            jedis.close();
        }
    }
}
