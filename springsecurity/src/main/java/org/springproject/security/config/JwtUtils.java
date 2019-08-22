package org.springproject.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils implements Serializable {

    private Clock clock = DefaultClock.INSTANCE;

    /**
     * 根据数据声明生成令牌
     */
    public String generateToken(Map<String, Object> claims, String subject) {
        final Date createDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createDate);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, JwtConfig.TOKEN_SECRET)
                .compact();
    }

    /**
     * 生成令牌
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims,userDetails.getUsername());
    }

    /**
     * 从令牌中获得数据声明
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JwtConfig.TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * 检查token
     */
    public String checkToken(String token) {
        return token.replace(JwtConfig.TOKEN_BEARER, "");
    }


    /**
     * 判断令牌是否过期
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(clock.now());
    }

    /**
     * 刷新令牌
     */
    public String refreshToken(String token) {
        final Date createDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createDate);
        Claims claims = getClaimsFromToken(token);
        claims.setIssuedAt(createDate);
        claims.setExpiration(expirationDate);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, JwtConfig.TOKEN_SECRET)
                .compact();
    }

    /**
     * 验证令牌
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 计算过期时间
     */
    private Date calculateExpirationDate(Date createDate) {
        return new Date(createDate.getTime() + JwtConfig.TOKEN_EXPIRATION);
    }

}
