package com.interplacement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.interplacement.authentication.JwtHelper;
import com.interplacement.request.JwtAuthenticationRequest;
import com.interplacement.response.JwtAuthenticationResponse;

@Service
public class AuthenticationService {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper helper;

    public JwtAuthenticationResponse authenticateUser(JwtAuthenticationRequest request) {
        try {

            doAuthenticate(request.getUsername(), request.getPassword());


            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());


            String token = helper.generateToken(userDetails);

            List<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.toList());

            return JwtAuthenticationResponse.builder()
                    .jwtToken(token)
                    .username(userDetails.getUsername())
                    .success(true)
                    .roles(roles)
                    .message("Login Successfully")
                    .build();
        } catch (BadCredentialsException ex) {

            return JwtAuthenticationResponse.builder()
                    .success(false)
                    .message("Invalid username or password")
                    .build();
        } catch (UsernameNotFoundException ex) {

            return JwtAuthenticationResponse.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .build();
        } catch (RuntimeException ex) {

            return JwtAuthenticationResponse.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .build();
        }
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authenticationToken);
    }
}
