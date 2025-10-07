package ru.ifmo.se.weblab.dto;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "points")
public class PointResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public boolean getIsPointInArea() {
        return isPointInArea;
    }

    public void setPointInArea(boolean pointInArea) {
        isPointInArea = pointInArea;
    }

    public int getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(int deltaTime) {
        this.deltaTime = deltaTime;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
