package com.interplacement.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SuperAdminRequest {

	private String email;
	private String password;

}
