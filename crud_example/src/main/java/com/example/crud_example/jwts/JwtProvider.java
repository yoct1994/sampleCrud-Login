package com.example.crud_example.jwts;

import com.example.crud_example.error.exceptions.InvalidTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.accessToken}")
    private Integer expireAccessToken;

    @Value("${jwt.refreshToken}")
    private Integer expireRefreshToken;

    public String generateAccessToken(Integer userId) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + expireAccessToken * 1000))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setSubject(userId.toString())
                .claim("type", "access_token")
                .compact();
    }

    public String generateRefreshToken(Integer userId) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireRefreshToken * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setSubject(userId.toString())
                .claim("type", "refresh_token")
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody().getSubject();
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().get("type").equals("refresh_token");
    }

    public String getUserId(String token) {
        System.out.println(token);
        if(!validateToken(token))
            throw new InvalidTokenException();

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
