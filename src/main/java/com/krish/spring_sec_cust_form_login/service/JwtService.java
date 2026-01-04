package com.krish.spring_sec_cust_form_login.service;

import com.krish.spring_sec_cust_form_login.utils.CommonConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Configuration public class JwtService {
    private final static byte[] KEY_BYTES = Decoders.BASE64.decode(CommonConstants.SECRET);
    private final Key SIGN_KEY = Keys.hmacShaKeyFor(KEY_BYTES);

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role",
                        user.getAuthorities().iterator().next().getAuthority())
                .setIssuedAt(new Date())
                .setExpiration(
                        Date.from(Instant.now().plus(1, ChronoUnit.HOURS))
                )
                .signWith(SIGN_KEY)
                .compact();
    }
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        return extractUsername(token).equals(user.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date(System.currentTimeMillis()));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SIGN_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
