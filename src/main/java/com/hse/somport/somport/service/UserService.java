package com.hse.somport.somport.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
