package com.interplacement.util;

import com.interplacement.entity.Student;
import com.interplacement.response.StudentResponse;

public class StudentDtoMapper {
	
	public static StudentResponse toResponseDto(Student student) {
		
		return StudentResponse.builder()
				.id(student.getId())
				.name(student.getName())
				.email(student.getEmail())
				.password(student.getPassword())
				.persuingYear(student.getPersuingYear())
				.geneder(student.getGeneder())
				.phone(student.getPhone())
				.course(student.getCourse())
				.branch(student.getBranch())
				.year(student.getYear())
				.cgpa(student.getCgpa())
				.dob(student.getDob())
				.address(student.getAddress())
				.profileImagePath(student.getProfileImagePath())
				.status(student.getStatus())
				.build();
	}

}
