package com.interplacement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.interplacement.request.JwtAuthenticationRequest;
import com.interplacement.response.JwtAuthenticationResponse;
import com.interplacement.service.AuthenticationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/authentication")
	public JwtAuthenticationResponse login(@RequestBody JwtAuthenticationRequest request) {

		return authenticationService.authenticateUser(request);
	}

}
