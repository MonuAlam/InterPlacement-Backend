package com.interplacement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.interplacement.authentication.JwtHelper;
import com.interplacement.request.JwtAuthenticationRequest;
import com.interplacement.response.JwtAuthenticationResponse;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class JwtAuthenticationController {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtHelper helper;
	
	
	@PostMapping("/authentication")
	public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody JwtAuthenticationRequest request){
	
		this.doAuthenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails=userDetailsService.loadUserByUsername(request.getUsername());
		
		String token=this.helper.generateToken(userDetails);
		
		JwtAuthenticationResponse response=JwtAuthenticationResponse.builder()
				.jwtToken(token)
				.username(userDetails.getUsername())
				.build();
		
		return new ResponseEntity<>(response,HttpStatus.OK);
				
	}
	
	private void doAuthenticate(String username,String password) {
		
		UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username, password);
		
		try {
		
			authenticationManager.authenticate(authenticationToken);
		
		} catch (BadCredentialsException e) {

			throw new BadCredentialsException("Credentials Invalid");
		}
	}
}
