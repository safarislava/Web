package ru.ifmo.se.weblab.dto;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "triangle_points")
@PrimaryKeyJoinColumn(name = "id")
public class TrianglePointResponse extends PointResponse {
    private String shape;

    public TrianglePointResponse() {
    }

    public TrianglePointResponse(boolean isPointInArea, int deltaTime, PointRequest pointRequest) {
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
