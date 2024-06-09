package com.pplbo.orderservice.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.pplbo.orderservice.jwt.model.JwtUserData;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    public JwtUserData getUserData(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        Claims body = claimsJws.getBody();

        String id = body.get("id", String.class);
        String email = body.get("email", String.class);
        String roleName = body.get("role", String.class);
        JwtUserData.Role role = JwtUserData.Role.valueOf(roleName);
        String name = body.get("name", String.class);

        return new JwtUserData(id, email, role, name);
    }
}
