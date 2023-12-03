package com.example.jwtsessionwithredis.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;

@Service
public class JwtUtil {

    private static String SECRET_KEY = "fwhegyuerbiyi5o4y89y54n0wb954g5i9jg40h5mnn4n4gn4398";

    public static String retrieveSessionId(String authorizationHeader) {

        String jwtToken;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7); // Skip "Bearer "
//
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(jwtToken)
                    .getBody();

            // Retrieve the new field ("sessionId")
            String sessionId = (String) claims.get("sessionId");

            return sessionId;
        }
        return null;
    }

    public static String generateJwtToken(String sessionId) {

        Date expirationTime = new Date(System.currentTimeMillis() + 3600000);

        // Create the token
        String token = Jwts.builder()
                .setSubject("Nikhil")
                .setExpiration(expirationTime)
                .claim("sessionId", sessionId)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return token;
    }


    private static String generateRandomKey(int sizeInBytes) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[sizeInBytes];
        secureRandom.nextBytes(key);
        return java.util.Base64.getEncoder().encodeToString(key);
    }


}
