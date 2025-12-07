package ru.ifmo.se.api.service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "shot")
@BatchSize(size = 20)
@Inheritance(strategy = InheritanceType.JOINED)
public class ShotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @Column(precision = 25, scale = 20)
    private BigDecimal x;
    @Column(precision = 25, scale = 20)
    private BigDecimal y;
    @Column(precision = 25, scale = 20)
    private BigDecimal r;
    private Long userId;
    private Integer accuracy;
    private Integer deltaTime;
    private Timestamp time;

    public ShotEntity() {}

    public <R> R accept(ShotEntityVisitor<R> visitor){
        return visitor.visit(this);
    }
}
