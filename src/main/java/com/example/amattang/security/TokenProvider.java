package com.example.amattang.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

    @Value("${app.token.secret}")
    private String tokenSecret;

    @Value("${app.token.accessTokenExpirationSec}")
    private long accessTokenExpirationSec;

    @Value("${app.token.refreshTokenExpirationSec}")
    private long refreshTokenExpirationSec;

    public enum TokenType {
        AUTHORIZATION,
        REFRESH
    }

    private JwtBuilder jwtBuilder(Date date) {
        return Jwts.builder()
                .setIssuer(tokenSecret)
                .setIssuedAt(date)
                .signWith(SignatureAlgorithm.HS512, tokenSecret);
    }

    public String createJwtAccessToken(String userId, Date date) {
        return jwtBuilder(date)
                .setSubject(userId)
                .setExpiration(new Date(date.getTime() + accessTokenExpirationSec))
                .compact();
    }

    public String createJwtRefreshToken(Date date) {
        return jwtBuilder(date)
                .setExpiration(new Date(date.getTime() + refreshTokenExpirationSec))
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
