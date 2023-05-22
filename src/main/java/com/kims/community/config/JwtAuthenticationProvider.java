package com.kims.community.config;

import com.kims.community.users.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationProvider {

    @Value("${jwt.secretkey}")
    private String SECRETKEY;

    public String generateToken(Users users) {
        return createToken(users);
    }

    /**
     * jwt 토큰 생성
     * @param users Users
     * @return String (토큰)
     */
    private String createToken(Users users) {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(SECRETKEY), "HmacSHA256");
        Date now = new Date();
        Duration expirationPeriod = Duration.ofMinutes(30);

        return Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expirationPeriod.toMillis()))
            .claim("id", users.getId())
            .setSubject(users.getEmail())
            .signWith(SignatureAlgorithm.HS256, key)
            .compact();
    }

    /**
     * jwt 토큰 유효성 검증 메서드
     * @param token jwt 토큰
     * @return boolean
     */
    public boolean validToken(String token) {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(SECRETKEY), "HmacSHA256");

        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());

        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Request의 Header에 token 값 "Authorization" : token 값
     * @param request HttpServletRequest
     * @return String
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    /**
     * 유저 정보
     * @param token 토큰
     * @return id
     */
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(SECRETKEY), "HmacSHA256");

        return Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token)
            .getBody();
    }
}
