package com.interplacement.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthenticationResponse {

	private String username;
	private Boolean success;
	private String jwtToken;
	private String message;
    private List<String> roles; // Ensure this field is present and is a List<String>
}
