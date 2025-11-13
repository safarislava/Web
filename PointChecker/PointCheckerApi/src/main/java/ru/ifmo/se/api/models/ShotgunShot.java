package ru.ifmo.se.api.models;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.dto.responses.BulletDto;
import ru.ifmo.se.api.dto.responses.ShotDetails;
import ru.ifmo.se.api.dto.responses.ShotgunDetails;
import ru.ifmo.se.api.mappers.BulletMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class ShotgunShot extends Shot {
    private List<Bullet> bullets;

    public ShotgunShot(Long id, Long version, BigDecimal x, BigDecimal y, BigDecimal r, User user,
                       Integer accuracy, Integer deltaTime, Timestamp time, List<Bullet> bullets) {
        super(id, version, x, y, r, user, accuracy, deltaTime, time);
        this.bullets = bullets;
    }

    public ShotgunShot(BigDecimal x, BigDecimal y, BigDecimal r, Integer deltaTime, List<Bullet> bullets) {
        super(x, y, r, (int) bullets.stream().filter(Bullet::getHit).count() * 100 / bullets.size(), deltaTime);
        this.bullets = bullets;
    }

    @Override
    public ShotDetails getDetailsDto() {
        List<BulletDto> bullets = this.bullets.stream().map(BulletMapper::toDto).toList();
        return new ShotgunDetails(bullets);
    }
}
