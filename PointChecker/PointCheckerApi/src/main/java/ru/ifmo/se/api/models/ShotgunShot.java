package ru.ifmo.se.api.models;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.dto.responses.BulletDto;
import ru.ifmo.se.api.dto.responses.ShotDetailsDto;
import ru.ifmo.se.api.dto.responses.ShotgunDetailsDto;
import ru.ifmo.se.api.entities.ShotEntity;
import ru.ifmo.se.api.entities.ShotgunShotEntity;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ShotgunShot extends Shot {
    private List<Bullet> bullets;

    public ShotgunShot(ShotgunShotEntity entity) {
        super(entity);
        bullets = entity.getBullets().stream().map(Bullet::new).toList();
    }

    public ShotgunShot(BigDecimal x, BigDecimal y, BigDecimal r, User user, Integer deltaTime, List<Bullet> bullets) {
        super(x, y, r, user, (int) bullets.stream().filter(Bullet::getHit).count() * 100 / bullets.size(), deltaTime);
        this.bullets = bullets;
    }

    @Override
    public ShotEntity toEntity() {
        return new ShotgunShotEntity(this);
    }

    @Override
    public ShotDetailsDto getDetailsDto() {
        List<BulletDto> bullets = this.bullets.stream().map(BulletDto::new).toList();
        return new ShotgunDetailsDto(bullets);
    }
}
