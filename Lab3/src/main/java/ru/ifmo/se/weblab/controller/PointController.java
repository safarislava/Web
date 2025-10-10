package ru.ifmo.se.weblab.controller;

import ru.ifmo.se.weblab.dto.PointRequest;
import ru.ifmo.se.weblab.dto.PointResponse;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PointController {
    private final CacheController cacheController = new CacheController();
    private final ValidationController validationController = new ValidationController();
    private final CalculationController calculationController = new CalculationController();
    private final PointRepository pointRepository = new PointJooqRepository();

    private PointResponse process(PointRequest pointRequest) {
        long startTime = System.nanoTime();
        Boolean isPointInArea = cacheController.getCache(pointRequest);
        if (isPointInArea == null) {
            isPointInArea = calculationController.checkPointInArea(pointRequest.x, pointRequest.y, pointRequest.r);
            cacheController.setCache(pointRequest, isPointInArea);
        }
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new PointResponse(isPointInArea, deltaTime, pointRequest);
    }

    public void updatePoints(List<String> xValues, List<String>  yValues, List<String>  rValues) throws IOException {
        ArrayList<PointResponse> points = new ArrayList<>();
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
            return;
        }
        pointRepository.save(points);
        pointRepository.close();
    }
}
