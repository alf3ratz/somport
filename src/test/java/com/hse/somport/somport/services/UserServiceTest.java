package com.hse.somport.somport.services;

import com.hse.somport.somport.config.exceptions.SomportConflictException;
import com.hse.somport.somport.config.exceptions.SomportNotFoundException;
import com.hse.somport.somport.dto.UserDto;
import com.hse.somport.somport.entities.UserEntity;
import com.hse.somport.somport.entities.repositories.UserRepository;
import com.hse.somport.somport.service.UserService;
import com.hse.somport.somport.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "password123";
    private static final String ENCRYPTED_PASSWORD = "encryptedPassword";

    @Autowired
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDto = new UserDto();
        userDto.setUsername(USERNAME);
        userDto.setPassword(PASSWORD);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername(USERNAME);
        userEntity.setPassword(ENCRYPTED_PASSWORD);
    }

    @Test
    void getUserByIdSuccessTest() {
        // Prepare data
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        // get user
        var foundUser = userService.getUserById(1L);

        // Assert
        assertNotNull(foundUser);
        assertEquals(USERNAME, foundUser.getUsername());
    }

    @Test
    void getUserByIdNotFoundTest() {
        // Prepare Data
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Get user
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });

        // Assert
        assertEquals("Пользователь не найден", exception.getMessage());
    }

    //
    @Test
    void getAllUsersSuccessTest() {
        // Prepare data
        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        // Get all users
        var users = userService.getAllUsers();

        // Assert
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
    }

}