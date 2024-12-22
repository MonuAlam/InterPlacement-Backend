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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Admin {

	@Id
	private String id;
	private String email;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private ProfileStatus status;
	
//	private String role;//enum

}
