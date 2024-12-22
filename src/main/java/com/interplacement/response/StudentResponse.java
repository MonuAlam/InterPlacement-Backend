package com.interplacement.response;

import java.time.LocalDate;

import com.interplacement.enums.CourseBranches;
import com.interplacement.enums.Courses;
import com.interplacement.enums.ProfileStatus;
import com.interplacement.enums.StudentGender;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class StudentResponse {

	private String id;
	private String name;
	private String email;
	private String password;
	private String persuingYear;
	private StudentGender geneder;
	private String rollNumber;
	private String phone;
	private Courses course;
	private CourseBranches branch;
	private String year;
	private Double cgpa;
	private LocalDate dob;
	private String address;
	private String profileImagePath;
	private ProfileStatus status;

}
