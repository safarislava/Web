package ru.ifmo.se.weblab.dto;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "shapable_points")
@PrimaryKeyJoinColumn(name = "id")
public class ShapablePointResponse extends PointResponse {
    private String shape;

    public ShapablePointResponse() {
    }

    public ShapablePointResponse(boolean isPointInArea, int deltaTime, PointRequest pointRequest, String shape) {
        super(isPointInArea, deltaTime, pointRequest);
        this.shape = shape;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
}
