package ru.ifmo.se.weblab.dto;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "circle_points")
@PrimaryKeyJoinColumn(name = "id")
public class CirclePointResponse extends PointResponse {
    private String shape;

    public CirclePointResponse() {
    }

    public CirclePointResponse(boolean isPointInArea, int deltaTime, PointRequest pointRequest) {
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
