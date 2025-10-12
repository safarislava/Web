package ru.ifmo.se.weblab.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "square_points")
public class SquarePointResponse extends PointResponse {
    public SquarePointResponse() {
        super();
        setShape("square");
    }

    public SquarePointResponse(boolean isPointInArea, int deltaTime, PointRequest pointRequest) {
        super(isPointInArea, deltaTime, pointRequest);
        setShape("square");
    }
}
