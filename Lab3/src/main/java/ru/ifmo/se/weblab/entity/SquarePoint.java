package ru.ifmo.se.weblab.entity;

import ru.ifmo.se.weblab.dto.PointRequest;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "square_points")
@PrimaryKeyJoinColumn(name = "id")
public class SquarePoint extends Point {
    private String shape;

    public SquarePoint() {
    }

    public SquarePoint(boolean isPointInArea, int deltaTime, PointRequest pointRequest) {
        super(isPointInArea, deltaTime, pointRequest);
        this.shape = "square";
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
}
