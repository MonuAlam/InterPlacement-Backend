package com.interplacement.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CompanyRequest {

	@NotBlank
	private String name;
	@NotBlank
	private String mobNumber;
	@NotBlank
	private String email;
	@NotBlank
	private String address;
	@NotBlank
	private String password;

	@NotBlank
	private String companyType;

    private String profileImagePath; 
    
    private String profileImageBase64; 

    private String profileImageName; 

}
