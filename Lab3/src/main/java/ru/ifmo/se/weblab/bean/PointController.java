package ru.ifmo.se.weblab.bean;

import ru.ifmo.se.weblab.controller.CacheController;
import ru.ifmo.se.weblab.controller.CalculationController;
import ru.ifmo.se.weblab.controller.ValidationController;
import ru.ifmo.se.weblab.dto.*;
import ru.ifmo.se.weblab.entity.CirclePoint;
import ru.ifmo.se.weblab.entity.Point;
import ru.ifmo.se.weblab.entity.SquarePoint;
import ru.ifmo.se.weblab.entity.TrianglePoint;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ManagedBean(name="pointController", eager = true)
@SessionScoped
public class PointController {
    @ManagedProperty(value = "#{repositoryBean}")
    private RepositoryBean repositoryBean;
    private final CacheController cacheController = new CacheController();
    private final ValidationController validationController = new ValidationController();
    private final CalculationController calculationController = new CalculationController();
    private final Random random = new Random();

    private Point process(PointRequest pointRequest) {
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
                return new CirclePoint(isPointInArea, deltaTime, pointRequest);
            }
            case 1 -> {
                return new SquarePoint(isPointInArea, deltaTime, pointRequest);
            }
            case 2 -> {
                return new TrianglePoint(isPointInArea, deltaTime, pointRequest);
            }
            default -> {
                return new Point(isPointInArea, deltaTime, pointRequest);
            }
        }
    }

    public void addPoints(List<String> xValues, List<String>  yValues, List<String>  rValues) throws IOException {
        ArrayList<Point> points = new ArrayList<>();
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
        repositoryBean.save(points);
    }

    public List<PointResponse> getPoints() {
        List<Point> points = repositoryBean.getAllPoints();
        List<PointResponse> pointResponses = new ArrayList<>();
        for (Point point : points) {
            pointResponses.add(new PointResponse(point));
        }
        return pointResponses;
    }

    public RepositoryBean getRepositoryBean() {
        return repositoryBean;
    }

    public void setRepositoryBean(RepositoryBean repositoryBean) {
        this.repositoryBean = repositoryBean;
    }
}
