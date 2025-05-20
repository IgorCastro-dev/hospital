package com.igor.hospital.application.service;

import com.igor.hospital.presentation.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public Token allocateToken(String email) {
        Date today = new Date();
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")
                .setSubject(email).setIssuer("Token do app").setIssuedAt(today)
                .setExpiration(new Date(today.getTime() + 1000 * 60 * 15)).compact();
        return new TokenDto(jwt,today.getTime(),email);
    }

    @Override
    public Token verifyToken(String key) {
        Claims claims = Jwts.parser()
                .setSigningKey("$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")
                .parseClaimsJws(key)
                .getBody();
        return new TokenDto(key,claims.getIssuedAt().getTime(),claims.getSubject());
    }
}
