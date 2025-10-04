package ru.ifmo.se.weblab.dto;

import java.sql.Timestamp;
import java.time.Instant;

public class PointResponse {
    public String title = "Point response";
    public String status = "200";
    public String x;
    public String y;
    public String r;
    public boolean isPointInArea;
    public int deltaTime;
    public Timestamp time;

    public PointResponse() {
    }

    public PointResponse(boolean isPointInArea, int deltaTime, PointRequest pointRequest) {
        this.x = pointRequest.x.toPlainString();
        this.y = pointRequest.y.toPlainString();
        this.r = pointRequest.r.toPlainString();
        this.isPointInArea = isPointInArea;
        this.deltaTime = deltaTime;
        this.time = Timestamp.from(Instant.now());
    }
}
