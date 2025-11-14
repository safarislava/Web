package ru.ifmo.se.api.components;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.exceptions.BadRequestException;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtComponent {
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JwtComponent(@Value("${JWT_SECRET}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).withIssuer("PointCheckerApi").build();
    }

    public String generate(String username, Duration expires) {
        return com.auth0.jwt.JWT.create()
                .withIssuer("PointCheckerApi")
                .withSubject("Client")
                .withClaim("id", UUID.randomUUID().toString())
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expires.toMillis()))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    public boolean verify(String token) {
        try {
            verifier.verify(token);
            return true;
        }
        catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("username").asString();
        } catch (JWTVerificationException e) {
            throw new BadRequestException("Token is invalid");
        }
    }

    public Instant getIssuedTime(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuedAt().toInstant();
        } catch (JWTVerificationException e) {
            throw new BadRequestException("Token is invalid");
        }
    }
}
