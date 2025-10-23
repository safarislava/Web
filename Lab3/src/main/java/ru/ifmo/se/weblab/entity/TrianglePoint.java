package ru.ifmo.se.weblab.entity;

import ru.ifmo.se.weblab.dto.PointRequest;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "triangle_points")
@PrimaryKeyJoinColumn(name = "id")
public class TrianglePoint extends Point {
    private String shape;

    public TrianglePoint() {
    }

    public TrianglePoint(boolean isPointInArea, int deltaTime, PointRequest pointRequest) {
        super(isPointInArea, deltaTime, pointRequest);
        this.shape = "triangle";
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
}
