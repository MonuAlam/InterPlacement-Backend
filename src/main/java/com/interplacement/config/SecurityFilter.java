package com.interplacement.config;

import com.interplacement.authentication.JwtAuthentication;
import com.interplacement.authentication.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilter {

    private final JwtAuthentication jwtAuthentication;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityFilter(JwtAuthentication jwtAuthentication, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthentication = jwtAuthentication;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .cors().disable()
                .authorizeHttpRequests()
                    .requestMatchers("/authentication").permitAll() 
                    .anyRequest().authenticated()  
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthentication) 
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) 
                .build();
    }
}
