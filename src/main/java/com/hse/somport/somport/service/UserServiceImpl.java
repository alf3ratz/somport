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


    //    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

//    @Autowired
//    public UserServiceImpl(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

    // Метод для регистрации нового пользователя
    public UserEntity registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new SomportConflictException("Пользователь с таким именем или email уже существует");
        }

        // Шифруем пароль перед сохранением
        //var encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        var user = userMapper.userDTOToUserEntity(userDto);
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    public UserEntity loginUser(UserDto userDto) {
        return Optional.ofNullable(userRepository.findByUsername(userDto.getUsername())).get()
                .orElseThrow(() -> new SomportNotFoundException("Пользователь с таким именем или email не существует"));
    }

    // Метод для получения пользователя по id
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    // Метод для получения пользователя по username
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    // Метод для обновления пользователя
    public UserEntity updateUser(Long id, String username, String password) {
        var user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Обновляем только если есть изменения
        if (username != null && !username.isEmpty()) {
            user.setUsername(username);
        }
        if (password != null && !password.isEmpty()) {
            //user.setPassword(passwordEncoder.encode(password));
            user.setPassword(password);
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

    @Override
    public boolean existsByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Поиск пользователя в репозитории
        return userRepository.findByUsername(username)
                // Если пользователь не найден, выбрасываем исключение
                .orElseThrow(() -> new SomportNotFoundException("Пользователь с именем " + username + " не найден"));
    }
}

