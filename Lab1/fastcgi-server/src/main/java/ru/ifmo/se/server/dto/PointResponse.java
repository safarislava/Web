package ru.ifmo.se.server.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

public class PointResponse {
    public String title = "Point response";
    public String status = "200";
    public BigDecimal x;
    public BigDecimal y;
    public BigDecimal r;
    public boolean isPointInArea;
    public int deltaTime;
    public Timestamp time;

    public PointResponse(boolean isPointInArea, int deltaTime, PointRequest pointRequest) {
        this.x = pointRequest.x;
        this.y = pointRequest.y;
        this.r = pointRequest.r;
        this.isPointInArea = isPointInArea;
        this.deltaTime = deltaTime;
        this.time = Timestamp.from(Instant.now());
    }

    public PointRequest getPointRequest() {
        return new PointRequest(x, y, r);
    }
}
