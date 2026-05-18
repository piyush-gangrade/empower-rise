package com.empower.config;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JWTService {

    // @Value("${application.security.jwt.secret-key}")
    private String secretKey = "7lEmBc9kDYpeUWgQNkfejO6badXXMvWogeolfpkkk=kkk";

    @Value("${application.security.jwt.access-token-expiration}")
    private long accessTokenExpire;

    // @Value("${application.security.jwt.refresh-token-expiration}")
    // private long refreshTokenExpire;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // public boolean isValid(String token, UserDetails user) {
    //     String username = extractUsername(token);

    //     boolean validToken = tokenRepository
    //             .findByAccessToken(token)
    //             .map(t -> !t.isLoggedOut())
    //             .orElse(false);

    //     return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    // }

    // public boolean isValidRefreshToken(String token, User user) {
    //     String username = extractUsername(token);

    //     boolean validRefreshToken = tokenRepository
    //             .findByRefreshToken(token)
    //             .map(t -> !t.isLoggedOut())
    //             .orElse(false);

    //     return (username.equals(user.getUsername())) && !isTokenExpired(token) && validRefreshToken;
    // }

    // private boolean isTokenExpired(String token) {
    //     return extractExpiration(token).before(new Date());
    // }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // public String generateAccessToken(User user) {
    //     return generateToken(user, accessTokenExpire);
    // }

    // public String generateRefreshToken(User user) {
    //     return generateToken(user, refreshTokenExpire);
    // }
// SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // or HS384, HS512
// String encodedKey = Encoders.BASE64.encode(secretKey.getEncoded());
public String generateToken(Long id, String role, long expireTime) {
        
        Long tokenExpireTime = 10000000000L;
        HashMap<String, Object> user = new HashMap<>();
        user.put("id", id);
        user.put("role", role);
        String token = Jwts
                .builder()
                .subject("userDetails")
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(user)
                .expiration(new Date(System.currentTimeMillis() + tokenExpireTime))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}