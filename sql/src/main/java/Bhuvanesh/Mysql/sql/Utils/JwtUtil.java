package Bhuvanesh.Mysql.sql.Utils;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

private  final String SECRET="AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
private final long EXPIRATION=1000*60;

    private  final Key secrectKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToke(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(secrectKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public  boolean validateJwtToken(String token){
        try{

            Jwts.parserBuilder()
                    .setSigningKey(secrectKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getSubject();


            return true;
        }catch (JwtException ex){
            return false;
        }
    }

    public  String extractEmail(String token){

       return Jwts.parserBuilder()
                .setSigningKey(secrectKey)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

}
