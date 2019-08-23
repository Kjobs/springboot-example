package com.springboot.security.demo.security;

import com.springboot.security.demo.config.JwtConfig;
import com.springboot.security.demo.config.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    public JwtFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestHeader = request.getHeader(JwtConfig.TOKEN_HEADER);
        String username = null;
        String authToken = null;

        if(requestHeader == null) {
            requestHeader = request.getParameter(JwtConfig.TOKEN_HEADER);
        }
        if(requestHeader != null && requestHeader.startsWith(JwtConfig.TOKEN_BEARER)) {
            authToken = jwtUtils.checkToken(requestHeader);
            try {
                username = jwtUtils.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
                throw new RuntimeException("从令牌获取用户名时发生错误");
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
                throw new RuntimeException();
            }
        } else {
            //找不到承载字符串，将忽略标题，默认放行
            //logger.warn("couldn't find bearer string, will ignore the header");
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //log.info(username);
            logger.debug("security context was null, so authorizating user");
            //没有必要从数据库中加载使用细节。您还可以缓存这些信息
            //在使用过程中，并从缓存中读取它。由你决定；
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtUtils.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
