package ru.ifmo.se.weblab.bean;


import ru.ifmo.se.weblab.controller.PointController;
import ru.ifmo.se.weblab.controller.PointRepository;
import ru.ifmo.se.weblab.dto.PointResponse;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ControlBean implements Serializable {
    private List<String> x;
    private String y;
    private List<String> r;

    public List<String> getX() { return x; }
    public void setX(List<String> x) { this.x = x; }

    public String getY() { return y; }
    public void setY(String y) { this.y = y; }

    public List<String> getR() { return r; }
    public void setR(List<String> r) { this.r = r; }

    public List<PointResponse> getPoints() {
        PointRepository repository = new PointRepository();
        return repository.findAll();
    }

    public void addPoints() throws IOException {
        ArrayList<String> yVals = new ArrayList<>();
        yVals.add(y);

        if (x != null && y != null && r != null) {
            PointController pointController = new PointController();
            pointController.updatePoints(x, yVals, r);
        }
    }
}

