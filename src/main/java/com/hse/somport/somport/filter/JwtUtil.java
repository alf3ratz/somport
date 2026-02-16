//package com.hse.somport.somport.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.security.InvalidKeyException;
//import java.security.Key;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Component
//@ConfigurationProperties(prefix = "security.jwt")
//@Data
//public class JwtUtil {
//
//    private String secretKey;
//    private long accessTokenExpiration = 36000000L;  // 10 часов
//    private long refreshTokenExpiration = 252000000L; // 7 дней
//
//    private Mac getHmacSha256() throws InvalidKeyException, NoSuchAlgorithmException {
//        SecretKeySpec keySpec = new SecretKeySpec(
//                secretKey.getBytes(StandardCharsets.UTF_8),
//                "HmacSHA256"
//        );
//        return Mac.getInstance("HmacSHA256");
//    }
//
//    public String generateRefreshToken(String username) {
//        Map<String, Object> claims = Map.of(
//                "sub", username,
//                "iat", System.currentTimeMillis() / 1000,
//                "exp", (System.currentTimeMillis() + refreshTokenExpiration) / 1000
//        );
//        return createToken(claims);
//    }
//
//    private String createToken(Map<String, Object> claims) {
//        try {
//            // Header
//            String header = base64Encode("{\"alg\":\"HS256\",\"typ\":\"JWT\"}");
//            // Payload
//            String payload = base64Encode(new ObjectMapper().writeValueAsString(claims));
//            // Signature
//            String unsigned = header + "." + payload;
//            Mac mac = getHmacSha256();
//            byte[] signature = mac.doFinal(unsigned.getBytes(StandardCharsets.UTF_8));
//            String signatureB64 = base64Encode(signature);
//
//            return unsigned + "." + signatureB64;
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create JWT", e);
//        }
//    }
//
//    public String getUsernameFromToken(String token) {
//        Claims claims = parseClaims(token);
//        return claims.get("sub", String.class);
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Claims claims = parseClaims(token);
//            return claims.get("exp", Long.class) > (System.currentTimeMillis() / 1000);
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    private Claims parseClaims(String token) {
//        String[] parts = token.split("\\.");
//        if (parts.length != 3) throw new IllegalArgumentException("Invalid JWT");
//
//        // Verify signature
//        String header = parts[0];
//        String payload = parts[1];
//        String signature = parts[2];
//
//        String unsigned = header + "." + payload;
//        Mac mac;
//        try {
//            mac = getHmacSha256();
//        } catch (InvalidKeyException e) {
//            throw new RuntimeException("Invalid secret key", e);
//        }
//
//        byte[] expectedSignature = mac.doFinal(unsigned.getBytes(StandardCharsets.UTF_8));
//        byte[] actualSignature = base64Decode(signature);
//
//        if (!MessageDigest.isEqual(expectedSignature, actualSignature)) {
//            throw new IllegalArgumentException("Invalid JWT signature");
//        }
//
//        // Parse payload
//        try {
//            String jsonPayload = new String(base64Decode(payload), StandardCharsets.UTF_8);
//            return new ObjectMapper().readValue(jsonPayload, Claims.class);
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Invalid JWT payload");
//        }
//    }
//
//    private String base64Encode(String value) {
//        return Base64.getUrlEncoder()
//                .withoutPadding()
//                .encodeToString(value.getBytes(StandardCharsets.UTF_8));
//    }
//
//    private String base64Encode(byte[] bytes) {
//        return Base64.getUrlEncoder()
//                .withoutPadding()
//                .encodeToString(bytes);
//    }
//
//    private byte[] base64Decode(String value) {
//        return Base64.getUrlDecoder().decode(value);
//    }
//}
