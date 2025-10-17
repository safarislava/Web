package ru.ifmo.se.weblab.dto;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "square_points")
@PrimaryKeyJoinColumn(name = "id")
public class SquarePointResponse extends PointResponse {
    private String shape;

    public SquarePointResponse() {
    }

    public SquarePointResponse(boolean isPointInArea, int deltaTime, PointRequest pointRequest) {
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
