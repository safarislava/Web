package ru.ifmo.se.api.pointchecker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.pointchecker.database.ShotRepository;
import ru.ifmo.se.api.pointchecker.database.UserRepository;
import ru.ifmo.se.api.pointchecker.dto.*;
import ru.ifmo.se.api.pointchecker.entity.*;
import ru.ifmo.se.api.pointchecker.utils.BigDecimalMath;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ShotBean {
    private final ShotRepository shotRepository;
    private final UserRepository userRepository;
    private final CacheBean cacheBean;
    private final ValidatorBean validatorBean;
    private final CalculationBean calculationBean;

    public List<ShotResponse> getShotResponses(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new IllegalArgumentException("User not found");

        List<Shot> shots = shotRepository.findAllByUser(user.get());
        List<ShotResponse> shotResponses = new ArrayList<>();
        shots.forEach(shot -> shotResponses.add(new ShotResponse(shot)));
        return shotResponses;
    }

    public void addShot(ShotRequest shotRequest, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new IllegalArgumentException("User not found");

        validatorBean.validate(shotRequest);
        Shot shot = new Shot();
        switch (shotRequest.weapon) {
            case REVOLVER -> {
                shot = processRevolverShot(shotRequest, user.get());
            }
            case SHOTGUN -> {
                shot = processShotgunShot(shotRequest, user.get());
            }
        }
        shotRepository.save(shot);
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

    private RevolverShot processRevolverShot(ShotRequest shotRequest, User user) {
        long startTime = System.nanoTime();
        Bullet bullet = processShot(shotRequest);
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new RevolverShot(user, bullet, deltaTime, shotRequest);
    }

    private ShotgunShot processShotgunShot(ShotRequest shotRequest, User user) {
        long startTime = System.nanoTime();
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

    public void clearShots(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new IllegalArgumentException("User not found");

        shotRepository.deleteAllByUser(user.get());
    }
}
