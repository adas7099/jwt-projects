package com.example.jwt.book.Utils;

import com.example.jwt.book.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    private String SECRET="878hjgyt5t76bjhbytr56y768786798908098676ijbvct656";

    public String generateToken(String username, List<String> roles){
        return Jwts.builder()
                .setSubject(username)
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                        System.currentTimeMillis()+90000000
                ))
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact();
    }
    public String extractUsername(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public Date extractExpiration(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
    public List<String> getRoles(String token){
        Claims claims= Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("roles",List.class);
    }
    public boolean isValidToken(String token, User user){
        return user.getUsername().equals(extractUsername(token))
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
