package com.hse.somport.somport.service;

import com.hse.somport.somport.config.exceptions.SomportConflictException;
import com.hse.somport.somport.config.exceptions.SomportNotFoundException;
import com.hse.somport.somport.config.mappers.UserMapper;
import com.hse.somport.somport.entities.UserEntity;
import com.hse.somport.somport.entities.repositories.UserRepository;
import com.hse.somport.somport.dto.UserDto;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private static final String USER_NOT_FOUND_MSG = "Пользователь не найден";

    // Метод для получения пользователя по id
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::userEntityToUserDTO)
                .orElseThrow(() -> new SomportNotFoundException(USER_NOT_FOUND_MSG));
    }

    // Метод для получения пользователя по username
    public UserDto getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::userEntityToUserDTO)
                .orElseThrow(() -> new SomportNotFoundException(USER_NOT_FOUND_MSG));
    }

    public boolean isUserExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Метод для удаления пользователя
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::userEntityToUserDTO)
                .toList();
    }

    @Override
    public boolean existsByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new SomportNotFoundException("Пользователь с именем " + username + " не найден"));
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Поиск пользователя в репозитории
        return userRepository.findByUsername(username)
                // Если пользователь не найден, выбрасываем исключение
                .orElseThrow(() -> new SomportNotFoundException("Пользователь с именем " + username + " не найден"));
    }
}

