package com.altimetrik.springsecuritywithjwt;

import com.altimetrik.springsecuritywithjwt.exception.TokenExpiredMessageException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
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

        String compact = Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        return  compact;
    }

    public boolean validateToken(String token, UserDetails userDetails) throws TokenExpiredMessageException, ExpiredJwtException {
        try{
        String userName = extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
        }catch (ExpiredJwtException e){
            throw new TokenExpiredMessageException("Token has been expired");
        }
    }

    private Boolean isTokenExpired(String token) throws ExpiredJwtException, TokenExpiredMessageException {
            return extractExpiration(token).before(new Date());

    }

    private Date extractExpiration(String token) throws ExpiredJwtException, TokenExpiredMessageException {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUserName(String token) throws ExpiredJwtException, TokenExpiredMessageException {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) throws ExpiredJwtException, TokenExpiredMessageException {
        Claims claims = extractAllClaim(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaim(String token) throws TokenExpiredMessageException {
        try{
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            throw new TokenExpiredMessageException("Token has been expired");
        }

    }

    public  Claims getClaims(String token) throws ExpiredJwtException{
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return claimsJws.getBody();
    }

}
