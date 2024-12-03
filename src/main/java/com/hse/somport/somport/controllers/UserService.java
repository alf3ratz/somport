package com.hse.somport.somport.controllers;

import com.hse.somport.somport.entities.UserEntity;
import com.hse.somport.somport.entities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Метод для регистрации нового пользователя
    public UserEntity registerUser(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Пользователь с таким именем или email уже существует");
        }

        // Шифруем пароль перед сохранением
        String encryptedPassword = passwordEncoder.encode(password);
        UserEntity user = UserEntity.builder()
                .username(username)
                .password(password)
                .build();
        return userRepository.save(user);
    }

    // Метод для получения пользователя по id
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    // Метод для получения пользователя по username
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Метод для обновления пользователя
    public UserEntity updateUser(Long id, String username, String password) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Обновляем только если есть изменения
        if (username != null && !username.isEmpty()) {
            user.setUsername(username);
        }
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        return userRepository.save(user);
    }

    // Метод для удаления пользователя
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}

