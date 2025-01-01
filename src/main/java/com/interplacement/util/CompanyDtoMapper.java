package com.interplacement.util;

import com.interplacement.entity.Company;
import com.interplacement.response.CompanyResponse;

public class CompanyDtoMapper {
	
	public static CompanyResponse toResponseDto(Company company) {
		
		return CompanyResponse.builder()
				.id(company.getId())
				.password(company.getPassword())
				.email(company.getEmail())
				.name(company.getName())
				.mobNumber(company.getMobNumber())
				.address(company.getAddress())
				.companyType(company.getCompanyType())
				.status(company.getStatus())
				.profileImagePath(company.getProfileImagePath())
				.role(company.getRole())
				.build();
	}

}
