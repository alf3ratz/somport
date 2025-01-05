package com.hse.somport.somport.controllers;

import com.hse.somport.somport.entities.UserEntity;
import com.hse.somport.somport.entities.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Регистрация нового пользователя
    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

    @PostMapping("/login")
    public UserEntity loginUser(@RequestBody UserDto userDto) {
        return userService.loginUser(userDto);
    }

    // Получение пользователя по id
    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Получение пользователя по username
    @GetMapping("/username/{username}")
    public UserEntity getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    // Получение списка всех пользователей
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }


}
