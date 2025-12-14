package com.example.jwt.item.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    private final String SECRET="878hjgyt5t76bjhbytr56yijbvct656";
    public String generateToken(UserDetails userDetails){
        List<String> roles= userDetails.getAuthorities()
                .stream().map(a->a.getAuthority())
                .toList();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                        System.currentTimeMillis()+5*60*1000
                ))
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact();
    }
    public String extractUserName(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }
    public List<String> extractRoles(String token){
        Claims claims=Jwts.parser()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("roles",List.class);
    }
    public boolean isValidToken(String token ,UserDetails userDetails){
        return extractUserName(token).equalsIgnoreCase(userDetails.getUsername())
                &&!isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiDate=Jwts.parser().setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        expiDate.before(new Date());
    }
}
