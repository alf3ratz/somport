package com.hse.somport.somport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
////                .authorizeRequests()
////                .requestMatchers("/api/**").authenticated()
////                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()  // Включите Swagger URL в исключения
////                .anyRequest().authenticated();
////                .anyRequest().permitAll();
//                .authorizeHttpRequests(authz -> authz
//                        // Разрешаем доступ к Swagger UI и API Docs
//                        .anyRequest().permitAll()
//                )
//                .csrf(AbstractHttpConfigurer::disable)  // Отключаем CSRF (для Swagger UI и других REST API)
//                .formLogin(AbstractHttpConfigurer::disable);
//        return http.build();
        return http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                //.exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(unauthorizedHandler))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                            try {
                                authorizationManagerRequestMatcherRegistry
                                        //.requestMatchers(HttpMethod.POST, POST_AUTH_WHITELIST).permitAll()
                                        //.requestMatchers(HttpMethod.GET, GET_AUTH_WHITELIST).permitAll()
                                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html","/api/**").permitAll()
                                        .anyRequest()
                                        .authenticated();
                                //.and()
                                //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                            } catch (Exception e) {
                                throw new RuntimeException(e.getMessage());
                            }
                        }
                )
                .formLogin(AbstractHttpConfigurer::disable).build();
        // .httpBasic(AbstractHttpConfigurer::disable).addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        //.authenticationProvider(daoAuthenticationProvider()).build();
    }
}
