//package com.hse.somport.somport.services;
//
//import com.hse.somport.somport.config.exceptions.SomportConflictException;
//import com.hse.somport.somport.config.exceptions.SomportNotFoundException;
//import com.hse.somport.somport.controllers.UserService;
//import com.hse.somport.somport.entities.UserEntity;
//import com.hse.somport.somport.entities.UserRepository;
//import com.hse.somport.somport.entities.dto.UserDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Autowired
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UserService userService;
//
//    private UserDto userDto;
//    private UserEntity userEntity;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        userDto = new UserDto();
//        userDto.setUsername("testUser");
//        userDto.setPassword("password123");
//
//        userEntity = new UserEntity();
//        userEntity.setId(1L);
//        userEntity.setUsername("testUser");
//        userEntity.setPassword("encryptedPassword");
//    }
//
//    @Test
//    void successRegisterUserTest() {
//        // Готовим данные
//        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(null);
//        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
//
//        // Регистрация
//        UserEntity registeredUser = userService.registerUser(userDto);
//
//        // Проверка
//        assertNotNull(registeredUser);
//        assertEquals("testUser", registeredUser.getUsername());
//    }
//
//    @Test
//    void registerUserAlreadyExistTest() {
//        // Готовим данные
//        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(userEntity);
//
//        // Регистрация
//        SomportConflictException exception = assertThrows(SomportConflictException.class, () -> {
//            userService.registerUser(userDto);
//        });
//
//        // Проверка
//        assertEquals("Пользователь с таким именем или email уже существует", exception.getMessage());
//    }
//
//    @Test
//    void successLoginUserTest() {
//        // Готовим данные
//        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(userEntity);
//
//        // Login
//        UserEntity loggedInUser = userService.loginUser(userDto);
//
//        // Assert
//        assertNotNull(loggedInUser);
//        assertEquals("testUser", loggedInUser.getUsername());
//    }
//
//    @Test
//    void loginNotFoundUserTest() {
//        // Arrange
//        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(null);
//
//        // Login
//        SomportNotFoundException exception = assertThrows(SomportNotFoundException.class, () -> {
//            userService.loginUser(userDto);
//        });
//
//        // Assert
//        assertEquals("Пользователь с таким именем или email не существует", exception.getMessage());
//    }
//
//    @Test
//    void getUserByIdSuccessTest() {
//        // Prepare data
//        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
//
//        // get user
//        UserEntity foundUser = userService.getUserById(1L);
//
//        // Assert
//        assertNotNull(foundUser);
//        assertEquals(1L, foundUser.getId());
//    }
//
//    @Test
//    void getUserByIdNotFoundTest() {
//        // Prepare Data
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        // Get user
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            userService.getUserById(1L);
//        });
//
//        // Assert
//        assertEquals("Пользователь  не найден", exception.getMessage());
//    }
//
//    @Test
//    void getAllUsersSuccessTest() {
//        // Prepare data
//        when(userRepository.findAll()).thenReturn(List.of(userEntity));
//
//        // Get all users
//        List<UserEntity> users = userService.getAllUsers();
//
//        // Assert
//        assertNotNull(users);
//        assertFalse(users.isEmpty());
//        assertEquals(1, users.size());
//    }
//}