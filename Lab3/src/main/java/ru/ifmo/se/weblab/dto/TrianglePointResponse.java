package ru.ifmo.se.weblab.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "triangle_points")
public class TrianglePointResponse extends PointResponse {
    public TrianglePointResponse() {
        super();
        setShape("triangle");
    }

    public TrianglePointResponse(boolean isPointInArea, int deltaTime, PointRequest pointRequest) {
        super(isPointInArea, deltaTime, pointRequest);
        setShape("triangle");
    }
}
