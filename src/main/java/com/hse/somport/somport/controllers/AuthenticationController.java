//package com.hse.somport.somport.controllers;
//
//import com.hse.somport.somport.dto.AuthenticationResponseDto;
//import com.hse.somport.somport.dto.LoginRequestDto;
//import com.hse.somport.somport.dto.RegistrationRequestDto;
//import com.hse.somport.somport.service.AuthenticationService;
//import com.hse.somport.somport.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class AuthenticationController {
//
//    @Autowired
//    private AuthenticationService authenticationService;
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/registration")
//    public ResponseEntity<String> register(
//            @RequestBody RegistrationRequestDto registrationDto) {
//        authenticationService.register(registrationDto);
//        return ResponseEntity.ok("Регистрация прошла успешно");
//    }
//
//    @PostMapping("/login")
//    public AuthenticationResponseDto authenticate(
//            @RequestBody LoginRequestDto request) {
//        return authenticationService.authenticate(request);
//    }
//
//    @PostMapping("/refresh_token")
//    public AuthenticationResponseDto refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response) {
//        return authenticationService.refreshToken(request, response);
//    }
//
//}
