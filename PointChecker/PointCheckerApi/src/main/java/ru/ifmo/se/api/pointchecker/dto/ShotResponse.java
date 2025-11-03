package ru.ifmo.se.api.pointchecker.dto;

import com.google.gson.Gson;
import ru.ifmo.se.api.pointchecker.entity.RevolverShot;
import ru.ifmo.se.api.pointchecker.entity.Shot;
import ru.ifmo.se.api.pointchecker.entity.ShotgunShot;

import java.util.List;

public class ShotResponse {
    private Long id;
    private String x;
    private String y;
    private String r;
    private Integer accuracy;
    private Integer deltaTime;
    private String time;
    private String details = "";

    public ShotResponse(Shot shot) {
        this.id = shot.getId();
        this.x = shot.getX().stripTrailingZeros().toPlainString();
        this.y = shot.getY().stripTrailingZeros().toPlainString();
        this.r = shot.getR().stripTrailingZeros().toPlainString();
        this.accuracy = shot.getAccuracy();
        this.deltaTime = shot.getDeltaTime();
        this.time = shot.getTime().toString();

        Gson gson = new Gson();
        if (shot instanceof RevolverShot) {
            BulletDto bullet = new BulletDto(((RevolverShot)  shot).getBullet());
            RevolverDetails details = new RevolverDetails(bullet);
            this.details = gson.toJson(details);
        }
        else if (shot instanceof ShotgunShot) {
            List<BulletDto> bullets = ((ShotgunShot) shot).getBullets().stream().map(BulletDto::new).toList();
            ShotgunDetails details = new ShotgunDetails(bullets);
            this.details = gson.toJson(details);
        }
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }
    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }
    public void setY(String y) {
        this.y = y;
    }

    public String getR() {
        return r;
    }
    public void setR(String r) {
        this.r = r;
    }

    public Integer getAccuracy() {
        return accuracy;
    }
    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getDeltaTime() {
        return deltaTime;
    }
    public void setDeltaTime(int deltaTime) {
        this.deltaTime = deltaTime;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
}
