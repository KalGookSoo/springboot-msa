package com.kalgooksoo.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtProvider {

    private static final String AUTHORITIES_KEY = "security";

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final String secret;

    private final long expirationMilliSeconds; // 30 minutes

    public JwtProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration-milli-seconds}") long expirationMilliSeconds) {
        this.secret = secret;
        this.expirationMilliSeconds = expirationMilliSeconds;
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
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SECRET_KEY)
                .setExpiration(expiration)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}