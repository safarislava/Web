package ru.ifmo.se.api.pointchecker.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ru.ifmo.se.api.pointchecker.database.ShotRepository;
import ru.ifmo.se.api.pointchecker.database.UserRepository;
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
    private UserRepository userRepository;
    @EJB
    private CacheBean cacheBean;
    @EJB
    private ValidatorBean validatorBean;
    @EJB
    private CalculationBean calculationBean;

    public List<ShotResponse> getShotResponses(String username) {
        List<Shot> shots = shotRepository.findAll(userRepository.getUser(username));
        List<ShotResponse> shotResponses = new ArrayList<>();
        for (Shot shot : shots) {
            shotResponses.add(new ShotResponse(shot));
        }
        return shotResponses;
    }

    public boolean addShots(List<ShotRequest> shotRequests) {
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
            shotRepository.save(shots);

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private Bullet processShot(ShotRequest shotRequest) {
        AbstractPoint abstractPoint = addSpread(shotRequest.x, shotRequest.y, shotRequest.r);
        Boolean isPointInArea = cacheBean.getCache(abstractPoint);
        if (isPointInArea == null) {
            isPointInArea = calculationBean.checkPointInArea(abstractPoint.x, abstractPoint.y);
            cacheBean.setCache(abstractPoint, isPointInArea);
        }
        return new Bullet(abstractPoint.x, abstractPoint.y, isPointInArea);
    }

    private RevolverShot processRevolverShot(ShotRequest shotRequest) {
        long startTime = System.nanoTime();
        User user = userRepository.getUser(shotRequest.username);
        Bullet bullet = processShot(shotRequest);
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new RevolverShot(user, bullet, deltaTime, shotRequest);
    }

    private ShotgunShot processShotgunShot(ShotRequest shotRequest) {
        long startTime = System.nanoTime();
        User user = userRepository.getUser(shotRequest.username);
        List<Bullet> bullets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bullets.add(processShot(shotRequest));
        }
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new ShotgunShot(user, bullets, deltaTime, shotRequest);
    }

    private AbstractPoint addSpread(BigDecimal x, BigDecimal y, BigDecimal r) {
        Random random = new Random();
        BigDecimal mod = BigDecimal.valueOf(random.nextDouble());
        BigDecimal arg = BigDecimal.valueOf(random.nextDouble(2 * Math.PI));

        BigDecimal pointX = x.add(BigDecimalMath.cos(arg).multiply(mod).multiply(r));
        BigDecimal pointY = y.add(BigDecimalMath.sin(arg).multiply(mod).multiply(r));
        return new AbstractPoint(pointX, pointY);
    }

    public boolean clearShots(String username) {
        try {
            shotRepository.clear(userRepository.getUser(username));
            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }
}
