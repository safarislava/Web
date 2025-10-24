package ru.ifmo.se.api.pointchecker.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ru.ifmo.se.api.pointchecker.database.ShotRepository;
import ru.ifmo.se.api.pointchecker.dto.*;
import ru.ifmo.se.api.pointchecker.entity.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Stateless
public class ShotController {
    @EJB
    private ShotRepository repositoryController;
    @EJB
    private CacheController cacheController;
    @EJB
    private ValidationController validationController;
    @EJB
    private CalculationController calculationController;

    public List<ShotResponse> getShotResponses() {
        List<Shot> shots = repositoryController.findAll();
        List<ShotResponse> shotResponses = new ArrayList<>();
        for (Shot shot : shots) {
            shotResponses.add(new ShotResponse(shot));
        }
        return shotResponses;
    }

    public void addShots(List<ShotRequest> shotRequests) {
        ArrayList<Shot> shots = new ArrayList<>();
        try {
            for (ShotRequest shotRequest : shotRequests) {
                validationController.validate(shotRequest);

                Shot shot = null;
                switch (shotRequest.weapon) {
                    case REVOLVER -> {
                        shot = processRevolverShot(shotRequest);
                    }
                    case SHOTGUN -> {
                        shot = processShotgunShot(shotRequest);
                    }
                }
                if (shot != null) shots.add(shot);
            }
        } catch (IllegalArgumentException e) {
            // externalContext.redirect(externalContext.getRequestContextPath() + "/error/400.jsp");
        }
        repositoryController.save(shots);
    }

    private Point processPoint(ShotRequest shotRequest) {
        AbstractPoint abstractPoint = addSpread(shotRequest.x, shotRequest.y, shotRequest.r);
        Boolean isPointInArea = cacheController.getCache(abstractPoint);
        if (isPointInArea == null) {
            isPointInArea = calculationController.checkPointInArea(abstractPoint.x, abstractPoint.y, shotRequest.r);
            cacheController.setCache(abstractPoint, isPointInArea);
        }
        return new Point(abstractPoint.x, abstractPoint.y, shotRequest.r, isPointInArea);
    }

    private RevolverShot processRevolverShot(ShotRequest shotRequest) {
        long startTime = System.nanoTime();
        Point point = processPoint(shotRequest);
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new RevolverShot(point, deltaTime, shotRequest);
    }

    private ShotgunShot processShotgunShot(ShotRequest shotRequest) {
        long startTime = System.nanoTime();
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            points.add(processPoint(shotRequest));
        }
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new ShotgunShot(points, deltaTime, shotRequest);
    }

    private AbstractPoint addSpread(BigDecimal x, BigDecimal y, BigDecimal r) {
        Random random = new Random();
        BigDecimal mod = BigDecimal.valueOf(random.nextDouble());
        BigDecimal arg = BigDecimal.valueOf(random.nextDouble());

        BigDecimal pointX = x.add(BigDecimalMath.cos(arg).multiply(mod));
        BigDecimal pointY = y.add(BigDecimalMath.sin(arg).multiply(mod));
        return new AbstractPoint(pointX, pointY);
    }
}
