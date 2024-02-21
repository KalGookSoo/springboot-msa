package com.kalgooksoo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private final String secret;

    private final SecretKey secretKey;

    private final long expirationMilliSeconds; // 30 minutes

    public JwtProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration-milli-seconds}") long expirationMilliSeconds) {
        this.secret = secret;
        this.expirationMilliSeconds = expirationMilliSeconds;
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        String authorities = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date expiration = new Date(now + expirationMilliSeconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(secret, authorities)
                .signWith(secretKey)
                .setExpiration(expiration)
                .compact();
    }

    public String getUsername(String token) {
        return getBody(token).getSubject();
    }

    public Claims getClaims(String token) {
        return getBody(token);
    }

    private Claims getBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}