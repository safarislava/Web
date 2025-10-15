package ru.ifmo.se.weblab.dto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "points")
@Inheritance(strategy = InheritanceType.JOINED)
public class PointResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status = "200";
    private String x;
    private String y;
    private String r;
    private boolean isPointInArea;
    private int deltaTime;
    private Timestamp time;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
