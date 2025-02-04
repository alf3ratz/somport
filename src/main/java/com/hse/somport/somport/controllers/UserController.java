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

    // Получение пользователя по id
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userServiceImpl.getUserById(id);
    }

    // Получение пользователя по username
    @GetMapping("/username/{username}")
    public UserDto getUserByUsername(@PathVariable String username) {
        return userServiceImpl.getUserByUsername(username);
    }

    // Удаление пользователя
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userServiceImpl.deleteUser(id);
    }

    // Получение списка всех пользователей
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }


}
