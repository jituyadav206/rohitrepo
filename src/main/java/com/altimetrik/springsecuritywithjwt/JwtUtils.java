package com.altimetrik.springsecuritywithjwt;

import com.altimetrik.springsecuritywithjwt.exception.TokenExpiredMessageException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.token.expiration.time}")
    private long expTime;

    private String secret = "altimetrik";

    public String generateToken(String userName) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        return Jwts.builder().setSubject(userName)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Claims validateToken(String token) throws TokenExpiredMessageException {
        if(isTokenExpired(token)){
            throw new TokenExpiredMessageException("Token is expired for timeout");
        }
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) throws TokenExpiredMessageException {
        try {
            Claims claim = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            Date date = extractClaim(claim, Claims::getExpiration);
            return date.before(new Date());
        }catch (ExpiredJwtException e){
            throw new TokenExpiredMessageException("Token is expired for timeout");
        }
    }

    private <T> T extractClaim(Claims claims, Function<Claims, T> claimResolver) {
        return claimResolver.apply(claims);
    }

}
