package ru.nsu.mkorovkina.rubics_cube.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nsu.mkorovkina.rubics_cube.model.UserModel;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms}")
    private long accessExpirationMs;

    @Getter
    @Value("${app.jwt.refresh-expiration-ms}")
    private long refreshExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(UserModel user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }

    public String extractUsername(String accesstoken) {
        return extractClaim(accesstoken, claims -> claims.getSubject());
    }

    public String extractRole(String accesstoken) {
        return extractClaim(accesstoken, claims -> claims.get("role", String.class));
    }

    public Date extractExpiration(String accesstoken) {
        return extractClaim(accesstoken, claims -> claims.getExpiration());
    }

    private boolean isTokenExpired(String accesstoken) {
        return extractExpiration(accesstoken).before(new Date());
    }

    public boolean isTokenValid(String accesstoken, UserModel user) {
        String username = extractUsername(accesstoken);
        return username.equals(user.getUsername()) && !isTokenExpired(accesstoken);
    }
}

