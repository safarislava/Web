package ru.ifmo.se.weblab.entity;

import ru.ifmo.se.weblab.dto.PointRequest;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "circle_points")
@PrimaryKeyJoinColumn(name = "id")
public class CirclePoint extends Point {
    private String shape;

    public CirclePoint() {
    }

    public CirclePoint(boolean isPointInArea, int deltaTime, PointRequest pointRequest) {
        super(isPointInArea, deltaTime, pointRequest);
        this.shape = "circle";
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
}
