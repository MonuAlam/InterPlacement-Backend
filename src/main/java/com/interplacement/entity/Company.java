package com.interplacement.entity;

import com.interplacement.enums.ProfileStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Company {

	@Id
	private String id;
	private String name;
	private String mobNumber;
	private String email;
	private String password;
	private String address;
	private String companyType;
	private String profileImagePath;
	
	@Enumerated(EnumType.STRING)
	private ProfileStatus profileStatus;

}