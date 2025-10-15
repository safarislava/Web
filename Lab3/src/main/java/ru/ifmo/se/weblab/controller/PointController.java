package ru.ifmo.se.weblab.controller;

import ru.ifmo.se.weblab.dto.*;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PointController {
    private final CacheController cacheController = new CacheController();
    private final ValidationController validationController = new ValidationController();
    private final CalculationController calculationController = new CalculationController();
    private final PointRepository pointRepository = new PointHibernateRepository();
    private final Random random = new Random();

    private ShapablePointResponse process(PointRequest pointRequest) {
        long startTime = System.nanoTime();
        Boolean isPointInArea = cacheController.getCache(pointRequest);
        if (isPointInArea == null) {
            isPointInArea = calculationController.checkPointInArea(pointRequest.x, pointRequest.y, pointRequest.r);
            cacheController.setCache(pointRequest, isPointInArea);
        }
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);

        switch (random.nextInt(3)) {
            case 0 -> {
                return new ShapablePointResponse(isPointInArea, deltaTime, pointRequest, "circle");
            }
            case 1 -> {
                return new ShapablePointResponse(isPointInArea, deltaTime, pointRequest, "square");
            }
            case 2 -> {
                return new ShapablePointResponse(isPointInArea, deltaTime, pointRequest, "triangle");
            }
            default -> {
                return new ShapablePointResponse(isPointInArea, deltaTime, pointRequest, "");
            }
        }
    }

    public void addPoints(List<String> xValues, List<String>  yValues, List<String>  rValues) throws IOException {
        ArrayList<ShapablePointResponse> points = new ArrayList<>();
        try {
            for (String x : xValues) {
                for (String y : yValues) {
                    for (String r : rValues) {
                        PointRequest pointRequest = new PointRequest(x, y, r);
                        validationController.validate(pointRequest);
                        points.add(process(pointRequest));
                    }
                }
            }
        } catch (Exception e) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "/error/400.jsp");
        }
        pointRepository.save(points);
    }

    public List<ShapablePointResponse> getPoints() {
        return pointRepository.findAll();
    }
}
