package com.interplacement.util;

import com.interplacement.entity.Admin;
import com.interplacement.response.AdminResponse;

public class AdminDtoMapper {
	
	public static AdminResponse toResponseDto(Admin admin) {
		
		return AdminResponse.builder()
				.id(admin.getId())
				.email(admin.getEmail())
				.password(admin.getPassword())
				.status(admin.getStatus())
				.role(admin.getRole())
				.build();
	}

}
