package ru.ifmo.se.api.usersmodule.components;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    public String generate(Long userId, Duration expires) {
        return com.auth0.jwt.JWT.create()
                .withIssuer("PointCheckerApi")
                .withSubject("Client")
                .withClaim("id", UUID.randomUUID().toString())
                .withClaim("userId", userId)
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

    public Long getUserId(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("userId").asLong();
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Token is invalid");
        }
    }

    public Instant getIssuedTime(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuedAt().toInstant();
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Token is invalid");
        }
    }
}
