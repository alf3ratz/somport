package com.hse.somport.somport.controllers;

import com.hse.somport.somport.dto.AuthenticationResponseDto;
import com.hse.somport.somport.dto.LoginRequestDto;
import com.hse.somport.somport.dto.RegistrationRequestDto;
import com.hse.somport.somport.service.AuthenticationService;
import com.hse.somport.somport.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testRegister_Success() throws Exception {
        // Подготовка данных для регистрации
        RegistrationRequestDto registrationDto = new RegistrationRequestDto();
        registrationDto.setUsername("testuser");
        registrationDto.setPassword("password123");

        // Мокаем поведение сервиса регистрации
        doNothing().when(authenticationService).register(any(RegistrationRequestDto.class));

        // Выполняем POST-запрос на регистрацию
        MvcResult result = mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto)))
                .andExpect(status().isOk())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().string("Регистрация прошла успешно"))
                .andReturn();

        // Дополнительная проверка, если необходимо
        verify(authenticationService, times(1)).register(any(RegistrationRequestDto.class));
    }

    @Test
    void testLogin_Success() throws Exception {
        // Подготовка данных для входа
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("testuser");
        loginRequestDto.setPassword("password123");

        // Мокаем поведение сервиса аутентификации
        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto("access_token", "refresh_token");
        when(authenticationService.authenticate(any(LoginRequestDto.class))).thenReturn(authenticationResponseDto);

        // Выполняем POST-запрос на аутентификацию
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"accessToken\":\"access_token\",\"refreshToken\":\"refresh_token\"}"));

        // Дополнительная проверка
        verify(authenticationService, times(1)).authenticate(any(LoginRequestDto.class));
    }

    @Test
    void testLogin_Failure() throws Exception {
        // Подготовка данных для входа
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("testuser");
        loginRequestDto.setPassword("wrongpassword");

        // Мокаем поведение сервиса аутентификации для ошибки
        when(authenticationService.authenticate(any(LoginRequestDto.class)))
                .thenThrow(new RuntimeException("Ошибка аутентификации"));

        // Выполняем POST-запрос на аутентификацию и проверяем статус 401
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Ошибка аутентификации"));

        // Проверка вызова метода аутентификации
        verify(authenticationService, times(1)).authenticate(any(LoginRequestDto.class));
    }

    @Test
    void testRefreshToken_Success() throws Exception {
        // Мокаем поведение сервиса обновления токена
        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto("new_access_token", "new_refresh_token");
        when(authenticationService.refreshToken(any(), any())).thenReturn(authenticationResponseDto);

        // Выполняем POST-запрос на обновление токена
        mockMvc.perform(post("/refresh_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"accessToken\":\"new_access_token\",\"refreshToken\":\"new_refresh_token\"}"));

        // Проверка вызова метода обновления токена
        verify(authenticationService, times(1)).refreshToken(any(), any());
    }

    @Test
    void testRefreshToken_Failure() throws Exception {
        // Мокаем поведение сервиса обновления токена для ошибки
        when(authenticationService.refreshToken(any(), any())).thenThrow(new RuntimeException("Ошибка обновления токена"));

        // Выполняем POST-запрос на обновление токена и проверяем статус 403
        mockMvc.perform(post("/refresh_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Ошибка обновления токена"));

        // Проверка вызова метода обновления токена
        verify(authenticationService, times(1)).refreshToken(any(), any());
    }

}
