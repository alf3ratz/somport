package com.hse.somport.somport.controllers;

import com.hse.somport.somport.entities.UserEntity;
import com.hse.somport.somport.dto.UserDto;
import com.hse.somport.somport.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    // Регистрация нового пользователя
    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody UserDto userDto) {
        return userServiceImpl.registerUser(userDto);
    }

    @PostMapping("/login")
    public UserEntity loginUser(@RequestBody UserDto userDto) {
        return userServiceImpl.loginUser(userDto);
    }

    // Получение пользователя по id
    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userServiceImpl.getUserById(id);
    }

    // Получение пользователя по username
    @GetMapping("/username/{username}")
    public UserEntity getUserByUsername(@PathVariable String username) {
        return userServiceImpl.getUserByUsername(username);
    }

    // Обновление пользователя
    @PutMapping("/{id}")
    public UserEntity updateUser(@PathVariable Long id,
                                 @RequestParam(required = false) String username,
                                 @RequestParam(required = false) String password) {
        return userServiceImpl.updateUser(id, username, password);
    }

    // Удаление пользователя
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userServiceImpl.deleteUser(id);
    }

    // Получение списка всех пользователей
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }


}
