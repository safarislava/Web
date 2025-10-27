package ru.ifmo.se.api.pointchecker.entity;

import jakarta.persistence.*;
import ru.ifmo.se.api.pointchecker.dto.ShotRequest;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "shot")
@Inheritance(strategy = InheritanceType.JOINED)
public class Shot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private Integer accuracy;
    private Integer deltaTime;
    private Timestamp time;

    public Shot() {}

    public Shot(ShotRequest shotRequest){
        x = shotRequest.x;
        y = shotRequest.y;
        r = shotRequest.r;
    }

    public Shot(User user, Integer accuracy, Integer deltaTime, ShotRequest shotRequest) {
        this(shotRequest);
        this.user = user;
        this.accuracy = accuracy;
        this.deltaTime = deltaTime;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getR() {
        return r;
    }

    public void setR(BigDecimal r) {
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
