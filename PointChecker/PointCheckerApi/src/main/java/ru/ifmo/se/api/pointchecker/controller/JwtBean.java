package ru.ifmo.se.api.pointchecker.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.ejb.Stateless;
import ru.ifmo.se.api.pointchecker.dto.UserDto;

import java.util.Date;
import java.util.UUID;

@Stateless
public class JwtBean {
    private final Algorithm algorithm = Algorithm.HMAC256("baeldung");
    private final JWTVerifier verifier = JWT.require(algorithm).withIssuer("PointCheckerApi").build();

    public String generate(UserDto user) {
        return com.auth0.jwt.JWT.create()
                .withIssuer("PointCheckerApi")
                .withSubject("Baeldung Details")
                .withClaim("username", user.username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000L))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    public boolean verify(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
//            String username = decodedJWT.getClaim("username").asString();
            return true;
        }
        catch (JWTVerificationException e) {
            return false;
        }
    }
}
