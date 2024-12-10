//package com.hse.somport.somport.util;
//
//import io.jsonwebtoken.Jwts;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtTokenUtil {
//
//    private String secretKey = "your-secret-key"; // Секретный ключ для подписания JWT
//
//    // Генерация JWT токена
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 день
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
//
//    // Проверка токена на валидность
//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    // Получение имени пользователя из токена
//    public String getUsernameFromToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//
//        Jwts.parser()
//                .verifyWith(secretKey)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//
//    // Проверка, истек ли токен
//    private boolean isTokenExpired(String token) {
//        return getExpirationDateFromToken(token).before(new Date());
//    }
//
//    // Получение времени истечения из токена
//    private Date getExpirationDateFromToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration();
//    }
//}
//
