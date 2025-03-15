package com.pharmassist.security;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Service
public class JwtService {
    private static final String SECRET_KEY = "2f6838150d2282aa6a5352e6dcaba6f1ead84ebe854ac63fdc630a8b5aca584a"; // Change this!

    private static final long EXPIRATION_TIME = 120 * 60 * 1000; // 1 hour

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String extractUsername(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null; // Return null if the token is invalid
        }
    }

    public boolean isTokenValid(String token, String username) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            // Check if token is expired
            if (decodedJWT.getExpiresAt().before(new Date())) {
                return false; // Token is expired
            }

            return decodedJWT.getSubject().equals(username);
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
