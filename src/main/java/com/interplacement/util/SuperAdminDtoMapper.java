package com.interplacement.util;

import com.interplacement.entity.SuperAdmin;
import com.interplacement.response.SuperAdminResponse;

public class SuperAdminDtoMapper {

	public static SuperAdminResponse toResponseDto(SuperAdmin superAdmin) {
		
		return SuperAdminResponse.builder()
				.id(superAdmin.getId())
				.email(superAdmin.getEmail())
				.password(superAdmin.getPassword())
				.role(superAdmin.getRole())
				.status(superAdmin.getStatus())
				.build();
	}
}
