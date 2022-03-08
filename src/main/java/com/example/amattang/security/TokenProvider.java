package com.example.amattang.security;

import com.example.amattang.domain.user.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.example.amattang.security.TokenProvider.TokenType.AUTHORIZATION;

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
    public String createJwtToken(User user, TokenType type) {

        Date now = new Date();

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(tokenSecret)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, tokenSecret);

        if (type.equals(AUTHORIZATION)) {
            return jwtBuilder
                    .setSubject(user.getId())
                    .setExpiration(new Date(now.getTime() + accessTokenExpirationSec))
                    .compact();
        } else {
            return jwtBuilder
                    .setExpiration(new Date(now.getTime() + refreshTokenExpirationSec))
                    .compact();
        }
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

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        log.debug(claims.toString());

        return Long.parseLong(claims.getSubject());
    }

}
