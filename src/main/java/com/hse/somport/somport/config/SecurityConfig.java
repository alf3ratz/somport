//package com.hse.somport.somport.config;
//
//import com.hse.somport.somport.filter.JwtFilter;
//import com.hse.somport.somport.handler.CustomAccessDeniedHandler;
//import com.hse.somport.somport.handler.CustomLogoutHandler;
//import com.hse.somport.somport.service.UserService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    private final JwtFilter jwtFIlter;
//
//    private final UserService userService;
//
//    private final CustomAccessDeniedHandler accessDeniedHandler;
//
//    private final CustomLogoutHandler customLogoutHandler;
//
//    public SecurityConfig(JwtFilter jwtFIlter,
//                          UserService userService,
//                          CustomAccessDeniedHandler accessDeniedHandler,
//                          CustomLogoutHandler customLogoutHandler) {
//
//        this.jwtFIlter = jwtFIlter;
//        this.userService = userService;
//        this.accessDeniedHandler = accessDeniedHandler;
//        this.customLogoutHandler = customLogoutHandler;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/login/**", "/registration/**", "/css/**", "/refresh_token/**", "/", "/v3/api-docs/**", "/swagger-ui/**")
//                            .permitAll();
//                    auth.requestMatchers("/hls/playlist").permitAll();
//                    auth.requestMatchers("/hls/stream.m3u8").permitAll();
//                    auth.requestMatchers("/ws/**", "/sockjs-node/**").permitAll();
//                    auth.requestMatchers("/admin/**").hasAuthority("ADMIN");
//                    auth.anyRequest().authenticated();
//                })
//                .userDetailsService(userService)
//                .exceptionHandling(e -> {
//                    e.accessDeniedHandler(accessDeniedHandler);
//                    e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
//                })
//                .securityMatcher("/ws/**") // ✅ Не применять JWT для WebSocket
//                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
//                .addFilterBefore(jwtFIlter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(jwtFIlter, UsernamePasswordAuthenticationFilter.class)
//                .logout(log -> {
//                    log.logoutUrl("/logout");
//                    log.addLogoutHandler(customLogoutHandler);
//                    log.logoutSuccessHandler((request, response, authentication) ->
//                            SecurityContextHolder.clearContext());
//                });
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://localhost:3000");  // Разрешаем фронтенду на этом порту
//        configuration.addAllowedMethod("GET");
//        configuration.addAllowedMethod("POST");
//        configuration.addAllowedMethod("PUT");
//        configuration.addAllowedMethod("DELETE");
//        configuration.addAllowedHeader("*");  // Разрешаем все заголовки
//        configuration.setAllowCredentials(true);  // Разрешаем передачу cookies
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
