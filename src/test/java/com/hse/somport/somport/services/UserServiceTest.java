package com.hse.somport.somport.services;

import com.hse.somport.somport.config.exceptions.SomportConflictException;
import com.hse.somport.somport.config.exceptions.SomportNotFoundException;
import com.hse.somport.somport.controllers.UserService;
import com.hse.somport.somport.entities.UserEntity;
import com.hse.somport.somport.entities.UserRepository;
import com.hse.somport.somport.entities.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("password123");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testUser");
        userEntity.setPassword("encryptedPassword");
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(null);  // Нет пользователя с таким именем
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encryptedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        UserEntity registeredUser = userService.registerUser(userDto);

        // Assert
        assertNotNull(registeredUser);
        assertEquals("testUser", registeredUser.getUsername());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testRegisterUser_Conflict() {
        // Arrange
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(userEntity);  // Пользователь уже существует

        // Act & Assert
        SomportConflictException exception = assertThrows(SomportConflictException.class, () -> {
            userService.registerUser(userDto);
        });

        assertEquals("Пользователь с таким именем или email уже существует", exception.getMessage());
    }

    @Test
    void testLoginUser_Success() {
        // Arrange
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(userEntity);

        // Act
        UserEntity loggedInUser = userService.loginUser(userDto);

        // Assert
        assertNotNull(loggedInUser);
        assertEquals("testUser", loggedInUser.getUsername());
    }

    @Test
    void testLoginUser_NotFound() {
        // Arrange
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(null);

        // Act & Assert
        SomportNotFoundException exception = assertThrows(SomportNotFoundException.class, () -> {
            userService.loginUser(userDto);
        });

        assertEquals("Пользователь с таким именем или email не существует", exception.getMessage());
    }

    @Test
    void testGetUserById_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        // Act
        UserEntity foundUser = userService.getUserById(1L);

        // Assert
        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
    }

    @Test
    void testGetUserById_NotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });

        assertEquals("Пользователь  не найден", exception.getMessage());
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        UserEntity updatedUser = userService.updateUser(1L, "newUsername", "newPassword");

        // Assert
        assertNotNull(updatedUser);
        assertEquals("newUsername", updatedUser.getUsername());
        assertNotEquals("encryptedPassword", updatedUser.getPassword());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testUpdateUser_UserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(1L, "newUsername", "newPassword");
        });

        assertEquals("Пользователь не найден", exception.getMessage());
    }

    @Test
    void testDeleteUser_Success() {
        // Arrange
        doNothing().when(userRepository).deleteById(1L);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        // Act
        List<UserEntity> users = userService.getAllUsers();

        // Assert
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
    }
}