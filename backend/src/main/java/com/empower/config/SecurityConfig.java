package com.empower.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

    @Configuration
public class SecurityConfig {

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http.csrf().disable()
    //             .authorizeHttpRequests(auth -> auth
    //                     .anyRequest().permitAll() // Allow all requests without authentication
    //             )
    //             .httpBasic().disable()
    //             .formLogin().disable(); // Disable form login

    //     return http.build();
    // }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

