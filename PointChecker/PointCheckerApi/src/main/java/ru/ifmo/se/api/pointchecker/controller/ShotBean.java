package ru.ifmo.se.api.pointchecker.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ru.ifmo.se.api.pointchecker.database.ShotRepository;
import ru.ifmo.se.api.pointchecker.dto.*;
import ru.ifmo.se.api.pointchecker.entity.*;
import ru.ifmo.se.api.pointchecker.utils.BigDecimalMath;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Stateless
public class ShotBean {
    @EJB
    private ShotRepository shotRepository;
    @EJB
    private CacheBean cacheBean;
    @EJB
    private ValidatorBean validatorBean;
    @EJB
    private CalculationBean calculationBean;

    public List<ShotResponse> getShotResponses() {
        List<Shot> shots = shotRepository.findAll();
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
                validatorBean.validate(shotRequest);

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
        shotRepository.save(shots);
    }

    private Bullet processPoint(ShotRequest shotRequest) {
        AbstractPoint abstractPoint = addSpread(shotRequest.x, shotRequest.y, shotRequest.r);
        Boolean isPointInArea = cacheBean.getCache(abstractPoint);
        if (isPointInArea == null) {
            isPointInArea = calculationBean.checkPointInArea(abstractPoint.x, abstractPoint.y, shotRequest.r);
            cacheBean.setCache(abstractPoint, isPointInArea);
        }
        return new Bullet(abstractPoint.x, abstractPoint.y, shotRequest.r, isPointInArea);
    }

    private RevolverShot processRevolverShot(ShotRequest shotRequest) {
        long startTime = System.nanoTime();
        Bullet bullet = processPoint(shotRequest);
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new RevolverShot(bullet, deltaTime, shotRequest);
    }

    private ShotgunShot processShotgunShot(ShotRequest shotRequest) {
        long startTime = System.nanoTime();
        List<Bullet> bullets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bullets.add(processPoint(shotRequest));
        }
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new ShotgunShot(bullets, deltaTime, shotRequest);
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
