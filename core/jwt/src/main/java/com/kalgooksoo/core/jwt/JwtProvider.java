package com.kalgooksoo.core.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    private final String secret;// FIXME 암호화 알고리즘 변경할 것

    private final long expirationMilliSeconds; // 30 minutes

    private static final String AUTHORITIES_KEY = "authorities";

    private SecretKey secretKey;

    public JwtProvider(String secret, long expirationMilliSeconds) {
        this.secret = secret;
        this.expirationMilliSeconds = expirationMilliSeconds;
    }

    @PostConstruct
    protected void init() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
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
                .signWith(secretKey)
                .setExpiration(expiration)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends SimpleGrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
    }

    public boolean validateToken(String token) {

        if (!hasText(token)) {
            return false;
        }

        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            LOGGER.error("유효하지 않은 JWT 토큰");
        } catch (ExpiredJwtException ex) {
            LOGGER.error("만료된 JWT 토큰");
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("지원하지 않는 JWT 토큰");
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT 클레임 문자열이 비어 있습니다.");
        } catch (@SuppressWarnings("deprecation") SignatureException ex) {
            LOGGER.error("JWT 서명이 로컬에서 계산된 서명과 일치하지 않습니다.");
        }
        return false;
    }

    private boolean hasText(String str) {
        return str != null && !str.isEmpty();
    }

}