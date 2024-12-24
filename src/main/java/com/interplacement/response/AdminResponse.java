package com.interplacement.response;

import com.interplacement.enums.ProfileStatus;
import com.interplacement.enums.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AdminResponse {

	private String id;
	private String email;
	private String password;
	private ProfileStatus status;
	private Role role;

}
