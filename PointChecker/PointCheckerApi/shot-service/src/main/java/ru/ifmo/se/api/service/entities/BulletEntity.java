package ru.ifmo.se.api.service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@Table(name = "bullet")
@AllArgsConstructor
@NoArgsConstructor
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
}
