package ru.ifmo.se.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "shot")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
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
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
    private Integer accuracy;
    private Integer deltaTime;
    private Timestamp time;

    public ShotEntity() {}

    public <R> R accept(ShotEntityVisitor<R> visitor){
        return visitor.visit(this);
    }
}
