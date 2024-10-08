package com.privilledge.pma.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String secretKey = "privilledge_mashegede_project_management_app_2024_secret_key.privilledge_mashegede_project_management_app_2024_secret_key.privilledge_mashegede_project_management_app_2024_secret_key.";
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
    //
//    private final Key key=Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T>T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public String generateToken(String username,Long user_id){
        Map<String,Object> claims= new HashMap<>();
        claims.put("user_id",user_id);
       String token=createToken(claims,username);
        System.out.println("Generated Token"+token);
       return token;
    }
    private String createToken(Map<String,Object> claims,String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10)).signWith(key).compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
