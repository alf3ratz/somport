//package com.hse.somport.somport.service;
//
//import com.hse.somport.somport.config.exceptions.SomportConflictException;
//import com.hse.somport.somport.config.exceptions.SomportNotFoundException;
//import com.hse.somport.somport.config.exceptions.SomportUnauthorizedException;
//import com.hse.somport.somport.dto.AuthenticationResponseDto;
//import com.hse.somport.somport.dto.LoginRequestDto;
//import com.hse.somport.somport.dto.RegistrationRequestDto;
//import com.hse.somport.somport.enm.Role;
//import com.hse.somport.somport.entities.TokenEntity;
//import com.hse.somport.somport.entities.UserEntity;
//import com.hse.somport.somport.entities.repositories.TokenRepository;
//import com.hse.somport.somport.entities.repositories.UserRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//import static com.hse.somport.somport.enm.Role.USER;
//
//@Service
//public class AuthenticationService {
//
//    private static final String USER_NOT_FOUND_MSG = "Пользователь не найден";
//    private static final String USER_UNAUTHORIZED_MSG = "Пользователь не авторизован";
//    private static final String USER_CONFLICT_MSG = "Пользователь уже существует";
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private TokenRepository tokenRepository;
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    public void register(RegistrationRequestDto request) {
//        if (userService.isUserExists(request.getUsername())) {
//            throw new SomportConflictException(USER_CONFLICT_MSG);
//        }
//
//        var user = UserEntity.builder()
//                .username(request.getUsername())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(USER)
//                .build();
//        user = userRepository.save(user);
//    }
//
//    private void revokeAllToken(UserEntity user) {
//        var validTokens = tokenRepository.findAllAccessTokenByUser(user.getId());
//        if (!validTokens.isEmpty()) {
//            validTokens.forEach(t -> {
//                t.setLoggedOut(true);
//            });
//        }
//        tokenRepository.saveAll(validTokens);
//    }
//
//    private void saveUserToken(String accessToken, String refreshToken, UserEntity user) {
//        var token = TokenEntity.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .loggedOut(false)
//                .user(user)
//                .build();
//        tokenRepository.save(token);
//    }
//
//    public AuthenticationResponseDto authenticate(LoginRequestDto request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(),
//                        request.getPassword()
//                )
//        );
//        var user = userRepository.findByUsername(request.getUsername())
//                .orElseThrow(() -> new SomportNotFoundException(USER_NOT_FOUND_MSG));
//        var accessToken = jwtService.generateAccessToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
//        revokeAllToken(user);
//        saveUserToken(accessToken, refreshToken, user);
//        return new AuthenticationResponseDto(accessToken, refreshToken);
//    }
//
//    public AuthenticationResponseDto refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response) {
//
//        var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            throw new SomportUnauthorizedException(USER_UNAUTHORIZED_MSG);
//        }
//
//        var token = authorizationHeader.substring(7);
//        var username = jwtService.extractUsername(token);
//
//        UserEntity user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new SomportNotFoundException(USER_NOT_FOUND_MSG));
//
//        if (jwtService.isValidRefresh(token, user)) {
//            var accessToken = jwtService.generateAccessToken(user);
//            var refreshToken = jwtService.generateRefreshToken(user);
//            revokeAllToken(user);
//            saveUserToken(accessToken, refreshToken, user);
//            return new AuthenticationResponseDto(accessToken, refreshToken);
//        }
//        return null;
//    }
//}
