package com.interplacement.request;

import java.time.LocalDate;

import com.interplacement.enums.StudentGender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentRequest {

	@NotBlank
	private String name;
	
	private String collegeId;
	
	@NotBlank
	private String email;

	private String password;
	@NotBlank
	private String persuingYear;
	
	@NotNull
	private StudentGender geneder;
	
	@NotBlank
	private String rollNumber;
	@NotBlank
	private String phone;
	@NotNull
	private String course;
	@NotNull
	private String branch;
	@NotBlank
	private String year;
	@NotNull
	private Double cgpa;

	@PastOrPresent
	private LocalDate dob;

	@NotBlank
	private String address;
	
	private String profileImagePath;

	private String profileImageBase64;

	private String profileImageName;

}
