package com.pharmassist.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
    private String SECRET_KEY;

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
