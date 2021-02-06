package com.mateuszjanczak.springjwtboilerplate.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Value("${security.jwt.SECRET}")
    private String SECRET;

    @Value("${security.jwt.EXPIRATION-TIME-IN-MINUTES}")
    private long EXPIRATION_TIME;

    public String createToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(new Date().getTime() + EXPIRATION_TIME * 1000 * 60))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }

    public String getUsernameFromToken(String token) {
         return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
