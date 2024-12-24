package com.interplacement.response;

import com.interplacement.enums.ProfileStatus;
import com.interplacement.enums.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CompanyResponse {

	private String id;
	private String name;
	private String mobNumber;
	private String email;
	private String address;
	private String password;
	private ProfileStatus profileStatus;
    private String profileImagePath; 
	private String companyType;
	private Role role;


}
