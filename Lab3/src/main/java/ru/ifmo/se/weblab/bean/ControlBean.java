package ru.ifmo.se.weblab.bean;


import com.google.gson.Gson;
import ru.ifmo.se.weblab.controller.PointController;
import ru.ifmo.se.weblab.controller.PointHibernateRepository;
import ru.ifmo.se.weblab.controller.PointRepository;
import ru.ifmo.se.weblab.dto.PointResponse;

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
    private final PointRepository repository = new PointHibernateRepository();

    public String getX() { return x; }
    public void setX(String x) { this.x = x; }

    public String getY() { return y; }
    public void setY(String y) { this.y = y; }

    public String getR() { return r; }
    public void setR(String r) { this.r = r; }


    private void loadPoints() {
        points = repository.findAll();
    }

    public List<PointResponse> getPoints() {
        if (points == null) loadPoints();
        return points;
    }

    public String getPointsJson() {
        if (points == null) loadPoints();
        Gson gson = new Gson();
        return gson.toJson(points);
    }

    public void addPoints() throws IOException {
        if (x != null && y != null && r != null) {
            PointController pointController = new PointController();
            if (pointController.updatePoints(List.of(x), List.of(y), List.of(r))) {
                loadPoints();
            }
        }
    }
}

