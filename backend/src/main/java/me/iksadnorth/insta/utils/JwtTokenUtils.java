package me.iksadnorth.insta.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {

    private static final String IDENTIFIER = "email";

    public static String getEmail(String token, String secretKey) {
        return JwtTokenUtils.getClaims(token, secretKey).get(IDENTIFIER, String.class);
    }

    public static boolean isExpired(String token, String secretKey) {
        Date expiration = JwtTokenUtils.getClaims(token, secretKey).getExpiration();
        return expiration.after(new Date());
    }

    private static Claims getClaims(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String getAccessToken(String email, String secretKey, Long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put(IDENTIFIER, email);

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiredTimeMs))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }
}
