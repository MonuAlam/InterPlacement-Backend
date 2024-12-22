package com.interplacement.entity;

import java.time.LocalDate;

import com.interplacement.enums.CourseBranches;
import com.interplacement.enums.Courses;
import com.interplacement.enums.ProfileStatus;
import com.interplacement.enums.StudentGender;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Student {
	
	@Id
	private String id;
	private String name;
	private String email;
	private String password;
	private String persuingYear;
	
	@Enumerated(EnumType.STRING)
	private StudentGender geneder;
	
	private String rollNumber;
	private String phone;
	
	@Enumerated(EnumType.STRING)
	private Courses course;
	
	@Enumerated(EnumType.STRING)
	private CourseBranches branch;
	
	private String year;
	private Double cgpa;
	private LocalDate dob;
	private String address;
	private String profileImagePath;
	
	@Enumerated(EnumType.STRING)
	private ProfileStatus status;
	
}
