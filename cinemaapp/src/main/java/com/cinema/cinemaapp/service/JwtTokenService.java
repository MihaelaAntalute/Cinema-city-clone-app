package com.cinema.cinemaapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {

    private Algorithm hmac512;

    private JWTVerifier verifier;

    public static final long JWT_TOKEN_VALIDITY = 220686888L;

    public JwtTokenService(@Value("${jwt.secret}") String secret){
        this.hmac512 = Algorithm.HMAC512(secret);
        this.verifier = JWT.require(this.hmac512).build();
    }

    public String generateToken (UserDetails userDetails){
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .sign(this.hmac512);
    }

    public String validateToken(String token){
        return verifier.verify(token).getSubject();
    }

}
