package ru.ifmo.se.api.service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "shotgun_shot")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor
public class ShotgunShotEntity extends ShotEntity {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BulletEntity> bullets;
}
