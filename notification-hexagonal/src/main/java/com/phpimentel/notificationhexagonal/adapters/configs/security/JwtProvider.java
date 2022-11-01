package com.phpimentel.notificationhexagonal.adapters.configs.security;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class JwtProvider {

    @Value("${ead.auth.jwtSecret}")
    private String jwtSecret;

    public String getSubjectJwt(String token) {
        return Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
    public String getClaimNameJwt(String token, String claimName) {
        return Jwts.parser().setSigningKey(this.jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .get(claimName).toString();
    }

    public boolean validateJwt(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException signatureException) {
            log.error("Invalid JWT signature: {}", signatureException.getMessage());
        } catch (MalformedJwtException malformedJwtException) {
            log.error("Invalid JWT token: {}", malformedJwtException.getMessage());
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("JWT token is expired: {}", expiredJwtException.getMessage());
        } catch (UnsupportedJwtException unsupportedJwtException) {
            log.error("JWT token is unsupported: {}", unsupportedJwtException.getMessage());
        }  catch (IllegalArgumentException illegalArgumentException) {
            log.error("JWT claims string is empty: {}", illegalArgumentException.getMessage());
        }
        return false;
    }
}
