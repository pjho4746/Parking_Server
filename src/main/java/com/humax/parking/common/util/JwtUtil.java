package com.humax.parking.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    @Value("${SECRET_KEY}")
    private String jwtSecretKey;


    //@Value("${JWT_EXPIRATION_TIME}")
    private final Long expirationTime = 1000L * 60 * 120; // 2h

    public String createToken(Long id) {
        Map<String, String> claims = new HashMap<>();
        claims.put("id", String.valueOf(id));
        //claims.put("loginType", loginType.name());

        SecretKey secretKey = createSecretKey();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public Long getUserId(String token) {
        return Long.parseLong(getClaims(token).get("id", String.class));
    }

//    public LoginType getLoginType(String token) {
//        return LoginType.valueOf(getClaims(token).get("loginType", String.class));
//    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(createSecretKey()).build().parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey createSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
