package ru.ifmo.se.api.pointchecker.dto;

import ru.ifmo.se.api.pointchecker.entity.Shot;

import java.sql.Timestamp;

public class ShotResponse {
    private Long id;
    private String x;
    private String y;
    private String r;
    private Integer accuracy;
    private Integer deltaTime;
    private Timestamp time;

    public ShotResponse(Shot shot) {
        this.id = shot.getId();
        this.x = shot.getX().toPlainString();
        this.y = shot.getY().toPlainString();
        this.r = shot.getR().toPlainString();
        this.accuracy = shot.getAccuracy();
        this.deltaTime = shot.getDeltaTime();
        this.time = shot.getTime();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public Integer getAccuracy() {
        return accuracy;
    }
    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getDeltaTime() {
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
