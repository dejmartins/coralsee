package com.coralpay.coralsee.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.coralpay.coralsee.security.config.JwtConfig;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.time.ZoneOffset.UTC;

@Service
@RequiredArgsConstructor
public class JwtServiceImplementation implements JwtService {
    private final JwtConfig jwtConfig;
    private final UserDetailsService userDetailsService;
    @Override
    public String generateTokenFor(String username) {
        return JWT.create()
                .withSubject("access_token")
                .withClaim("username", username)
                .withExpiresAt(LocalDateTime.now().plusDays(jwtConfig.getJwtDuration())
                        .toInstant(UTC))
                .sign(HMAC512(jwtConfig.getJwtSecret().getBytes()));
    }

    @Override
    public boolean validate(String token) {
        Algorithm algorithm = HMAC512(jwtConfig.getJwtSecret().getBytes());
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build().verify(token);
        return isValidToken(decodedJWT);
    }

    private boolean isValidToken(DecodedJWT decodedJWT) {
        return isTokenNotExpired(decodedJWT);
    }

    private boolean isTokenNotExpired(DecodedJWT decodedJWT) {
        return Instant.now().isBefore(decodedJWT.getExpiresAtAsInstant());
    }

    @Override
    public UserDetails extractUserDetailsFrom(String token) {
        Algorithm algorithm = HMAC512(jwtConfig.getJwtSecret().getBytes());
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build().verify(token);
        String username =  decodedJWT.getClaim("username").as(String.class);
        return userDetailsService.loadUserByUsername(username);
    }
}
