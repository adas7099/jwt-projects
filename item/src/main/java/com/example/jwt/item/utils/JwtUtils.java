package com.example.jwt.item.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    private final String SECRET="878hjgyt5t76bjhbytr56y768786798908098676ijbvct656";
    public String generateToken(UserDetails userDetails){
        List<String> roles= userDetails.getAuthorities()
                .stream().map(a->a.getAuthority())
                .toList();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles",roles)
                .issuedAt(new Date())
                .expiration(new Date(
                        System.currentTimeMillis()+5*60*1000
                ))
                .signWith(getKey())
                .compact();
    }
    public String extractUserName(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }
    public List<String> extractRoles(String token){
        Claims claims=Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("roles",List.class);
    }
    public boolean isValidToken(String token ,UserDetails userDetails){
        return extractUserName(token).equalsIgnoreCase(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiDate=Jwts.parser().verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiDate.before(new Date());
    }
    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
}
