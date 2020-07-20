package com.altimetrik.springsecuritywithjwt.filter;

import com.altimetrik.springsecuritywithjwt.JwtUtils;
import com.altimetrik.springsecuritywithjwt.exception.TokenExpiredMessageException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String token=null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }

        if(authorizationHeader != null && SecurityContextHolder.getContext().getAuthentication() == null){
            try {
                Claims claims = jwtUtils.validateToken(token);
                List<String> authorities = (List<String>)claims.get("authorities");
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(claims.getSubject(),
                        null,authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } catch (TokenExpiredMessageException e) {
                e.printStackTrace();
            }
        }
        filterChain.doFilter(request,response);
    }
}
