package com.interplacement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SpringSecurity {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails companyUser = User.builder()
                .username("company")
                .password(passwordEncoder().encode("company"))
                .roles("COMPANY")
                .build();

        UserDetails studentUser = User.builder()
                .username("student")
                .password(passwordEncoder().encode("student"))
                .roles("STUDENT")
                .build();

        UserDetails superUser = User.builder()
                .username("super")
                .password(passwordEncoder().encode("super"))
                .roles("SUPER")
                .build();

        return new InMemoryUserDetailsManager(adminUser, companyUser, studentUser, superUser);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
