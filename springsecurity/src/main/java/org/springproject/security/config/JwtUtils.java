package org.springproject.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springproject.security.entity.SysUser;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils implements Serializable {

    private Clock clock = DefaultClock.INSTANCE;

    /**
     * 获取token所有的声明信息
     *
     * @param token token
     * @return 声明信息
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JwtConfig.TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 判断 token 是否过期
     *
     * @param token token
     * @return 是否过期
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    /**
     * 判断是否重置过密码
     *
     * @param created           创建时间
     * @param lastPasswordReset 最后一次重置时间
     * @return
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * 忽略过期的token
     *
     * @param token token
     * @return
     */
    private Boolean ignoreTokenExpiration(String token) {
        // 这里指定令牌，因为过期将被忽略
        return false;
    }

    /**
     * 创建token并返回
     *
     * @param claims  声明集合
     * @param subject 主体、拥有者
     * @return 返回token
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, JwtConfig.TOKEN_SECRET)
                .compact();
    }

    /**
     * 检查token
     *
     * @param token
     * @return
     */
    public String checkToken(String token) {
        return token.replace(JwtConfig.TOKEN_BEARER, "");
    }

    /**
     * 根据 token 获取用户名并反回
     *
     * @param token token
     * @return 用户名
     */
    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 获取 token 的签发时间
     *
     * @param token token
     * @return 签发时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    /**
     * 获取 token 的过期时间
     *
     * @param token token
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 根据 token 后去所有声明
     *
     * @param token          token
     * @param claimsResolver 回调
     * @param <T>            返回类型
     * @return
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 根据 UserDetails 创建token
     *
     * @param userDetails 用户信息
     * @return token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * 根据 UserDetails 创建token
     *
     * @param userDetails 用户信息
     * @return token
     */
    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * 判断是否可以刷新token
     *
     * @param token             token
     * @param lastPasswordReset 最后一次重置密码
     * @return true 可以刷新 否则不可以
     */
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    /**
     * 刷新token并返回
     *
     * @param token token
     * @return 返回刷新后的token
     */
    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, JwtConfig.TOKEN_SECRET)
                .compact();
    }

    /**
     * 验证Token是否合法
     *
     * @param token       token
     * @param userDetails 用户信息
     * @return 是否通过验证
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        SysUser jwtUserDetails = (SysUser) userDetails;
        final String username = getUserNameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
        return (
                username.equals(jwtUserDetails.getUsername())
                        && !isTokenExpired(token)
        );
    }

    /**
     * 计算过期时间，在传入的时间增加配置的时间后返回
     *
     * @param createdDate 创建时间
     * @return 过期时间
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + JwtConfig.TOKEN_EXPIRATION * 1000);
    }
}
