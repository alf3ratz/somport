package com.hse.somport.somport.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.vaadin.flow.server.DevToolsToken.getToken;

//@Component
//public class JwtFilter extends OncePerRequestFilter {
//    private final UserDetailsService userDetailsService;
//    private final JwtUtil jwtUtil; // твой JWT утилита
//
//    public JwtFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
//        this.userDetailsService = userDetailsService;
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc) throws ServletException, IOException {
//        String token = getToken(/*req*/);
//        if (token != null && jwtUtil.validateToken(token)) {
//            String username = jwtUtil.getUsernameFromToken(token);
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username); // <-- здесь!
//            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
//                    userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//        fc.doFilter(req, res);
//    }
//}
