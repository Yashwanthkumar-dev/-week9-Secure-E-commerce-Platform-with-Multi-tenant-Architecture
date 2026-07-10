package com.ecommerce.app.security.Jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${app.security.secret.key}")
    private String SECRET_KEY;

    @Value("${app.security.secret.key.expiration}")
    private Long EXPIRATION;

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ============ generate token =============

    public String generateToken(String email, Long tenantId) {
        return Jwts.builder()
                .subject(email)
                .claim("tenantID", tenantId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSignKey())
                .compact();

    }

    // =============== get all extract claims =============
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build().parseSignedClaims(token).getPayload();
    }

    // ===============  extract tenant =============
    public Long extractTenantId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("tenantId", Long.class);
    }

    // ============= exctract username =============
    public String extractUserName(String token) {
        Claims claim = extractAllClaims(token);
        return claim.getSubject();
    }

    // ============= get Expiration =============

    public Date extractExpiration(String token) {
        Claims claim = extractAllClaims(token);
        return claim.getExpiration();
    }

    // =============== validation token ================

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // =================== istoken validation ==========
    public boolean isTokenvalid(String token, UserDetails user) {
        String username = extractUserName(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }
}
