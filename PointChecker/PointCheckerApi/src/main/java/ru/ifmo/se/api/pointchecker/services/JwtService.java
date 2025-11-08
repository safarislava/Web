package ru.ifmo.se.api.pointchecker.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.pointchecker.dto.requests.UserDto;

import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JwtService(Environment environment) {
        String secret = environment.getProperty("JWT_SECRET", "VeRy_SeCrEt");
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).withIssuer("PointCheckerApi").build();
    }

    public String generate(UserDto user) {
        return com.auth0.jwt.JWT.create()
                .withIssuer("PointCheckerApi")
                .withSubject("Client")
                .withClaim("username", user.username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000L))
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
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("username").asString();
    }
}
