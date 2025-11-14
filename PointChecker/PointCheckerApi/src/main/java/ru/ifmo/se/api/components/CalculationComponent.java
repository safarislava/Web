package ru.ifmo.se.api.components;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.models.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

@Component
public class CalculationComponent {
    private final MathContext mathContext = MathContext.DECIMAL128;
    private final BufferedImage image;
    private final BigDecimal scale = BigDecimal.valueOf(50);

    public CalculationComponent() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getResource("target.png")));
    }

    @Cacheable(value = "hit",
            keyGenerator = "bigDecimalKeyGenerator",
            unless = "#result == false")
    public boolean checkHit(Point point) {
        BigDecimal width = new BigDecimal(image.getWidth());
        BigDecimal height = new BigDecimal(image.getHeight());

        BigDecimal xDenormalized = point.getX().multiply(scale).add(width.divide(BigDecimal.valueOf(2), mathContext));
        BigDecimal yDenormalized = point.getY().multiply(scale).negate().add(height.divide(BigDecimal.valueOf(2), mathContext));

        if (xDenormalized.compareTo(width) > 0 || yDenormalized.compareTo(height) > 0 ||
            xDenormalized.compareTo(BigDecimal.valueOf(0)) < 0 || yDenormalized.compareTo(BigDecimal.valueOf(0)) < 0) {
            return false;
        }

        Color c = new Color(image.getRGB(xDenormalized.intValue(), yDenormalized.intValue()));
        return !(c.getRed() == 255 && c.getGreen() == 0 && c.getBlue() == 0);
    }
}
