package ru.ifmo.se.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.models.Bullet;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "bullet")
public class BulletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @Column(precision = 25, scale = 20)
    private BigDecimal x;
    @Column(precision = 25, scale = 20)
    private BigDecimal y;
    private Boolean hit;

    public BulletEntity() {}

    public BulletEntity(Bullet bullet) {
        id = bullet.getId();
        x = bullet.getX();
        y = bullet.getY();
        hit = bullet.getHit();
    }
}
