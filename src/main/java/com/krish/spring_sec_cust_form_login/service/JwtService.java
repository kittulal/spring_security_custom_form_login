package com.krish.spring_sec_cust_form_login.service;

import com.krish.spring_sec_cust_form_login.dto.AuthRequest;
import com.krish.spring_sec_cust_form_login.utils.CommonConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration public class JwtService {

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role",
                        user.getAuthorities().iterator().next().getAuthority())
                .setIssuedAt(new Date())
                .setExpiration(
                        Date.from(Instant.now().plus(1, ChronoUnit.HOURS))
                )
                .signWith(getMySignatureKey())
                .compact();
    }

    private Key getMySignatureKey(){
        byte[] keyBytes = Decoders.BASE64.decode(CommonConstants.SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
