package com.interplacement.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AdminRequest {
	
	@NotBlank
	private String email;
	@NotBlank
	private String password;


}
