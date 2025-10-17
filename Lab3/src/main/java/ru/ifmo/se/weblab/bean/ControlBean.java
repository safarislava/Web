package ru.ifmo.se.weblab.bean;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.ifmo.se.weblab.controller.PointController;
import ru.ifmo.se.weblab.dto.PointResponse;
import ru.ifmo.se.weblab.utils.PointResponseSerializer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="controlBean", eager = true)
@SessionScoped
public class ControlBean implements Serializable {
    private String x;
    private String y;
    private String r;
    private List<PointResponse> points;
    private final PointController pointController = new PointController();

    public String getX() { return x; }
    public void setX(String x) { this.x = x; }

    public String getY() { return y; }
    public void setY(String y) { this.y = y; }

    public String getR() { return r; }
    public void setR(String r) { this.r = r; }


    private void loadPoints() {
        points = pointController.getPoints();
    }

    public List<PointResponse> getPoints() {
        if (points == null) loadPoints();
        return points;
    }

    public String getPointsJson() {
        if (points == null) loadPoints();
        Gson gson = new GsonBuilder().registerTypeAdapter(PointResponse.class, new PointResponseSerializer()).create();
        return gson.toJson(points);
    }

    public void addPoints() throws IOException {
        if (x == null || y == null || r == null) return;
        pointController.addPoints(List.of(x), List.of(y), List.of(r));
        loadPoints();
    }
}

