package ru.ifmo.se.weblab.dto;

import ru.ifmo.se.weblab.entity.CirclePoint;
import ru.ifmo.se.weblab.entity.Point;
import ru.ifmo.se.weblab.entity.SquarePoint;
import ru.ifmo.se.weblab.entity.TrianglePoint;

import java.sql.Timestamp;

public class PointResponse {
    private Long id;
    private String x;
    private String y;
    private String r;
    private boolean isPointInArea;
    private int deltaTime;
    private Timestamp time;
    private String shape;

    public PointResponse(Point point) {
        this.id = point.getId();
        this.x = point.getX();
        this.y = point.getY();
        this.r = point.getR();
        this.isPointInArea = point.getIsPointInArea();
        this.deltaTime = point.getDeltaTime();
        this.time = point.getTime();

        if (point instanceof CirclePoint) {
            this.shape = ((CirclePoint) point).getShape();
        }
        else if (point instanceof SquarePoint) {
            this.shape = ((SquarePoint) point).getShape();
        }
        else if (point instanceof TrianglePoint) {
            this.shape = ((TrianglePoint) point).getShape();
        }
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

    public boolean getIsPointInArea() {
        return isPointInArea;
    }

    public void setIsPointInArea(boolean isPointInArea) {
        this.isPointInArea = isPointInArea;
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

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
}
