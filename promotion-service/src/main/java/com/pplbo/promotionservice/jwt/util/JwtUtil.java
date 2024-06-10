package com.pplbo.promotionservice.jwt.util;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pplbo.promotionservice.jwt.model.JwtUserData;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    public JwtUserData getUserData(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET)))
                .requireIssuer("pplbo-ecommerce")
                .build()
                .parseSignedClaims(token).getPayload();

        return new JwtUserData(
                claims.get("id", String.class),
                claims.get("email", String.class),
                JwtUserData.Role.valueOf(claims.get("role", String.class)),
                claims.get("name", String.class));
    }
}
