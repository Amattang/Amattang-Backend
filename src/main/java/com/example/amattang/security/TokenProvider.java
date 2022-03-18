package com.example.amattang.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

    @Value("${app.token.issuer}")
    private String issuer;

    @Value("${app.token.secret}")
    private String tokenSecret;

    @Value("${app.token.accessTokenExpirationSec}")
    private long accessTokenExpirationSec;

    @Value("${app.token.refreshTokenExpirationSec}")
    private long refreshTokenExpirationSec;

    public enum TokenType {
        ACCESS,
        REFRESH
    }

    private JwtBuilder jwtBuilder(Date date) {
        return Jwts.builder()
                .setIssuer(issuer)
                .setIssuedAt(date)
                .signWith(SignatureAlgorithm.HS512, tokenSecret);
    }

    public String createJwtToken(String userId, Date date, TokenType type) {
        return jwtBuilder(date)
                .setSubject(userId)
                .setExpiration(new Date(date.getTime() + ((type.equals(TokenType.ACCESS)?accessTokenExpirationSec:refreshTokenExpirationSec))))
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

    public String getJwtAccessFromHeader(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        return getJwtAccessFromFullToken(accessToken);
    }

    public String getJwtAccessFromFullToken(String accessToken) {
        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer")) {
            return accessToken.substring(7, accessToken.length());
        }
        return null;
    }


}
