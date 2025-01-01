package com.interplacement.util;

import com.interplacement.entity.Courses;
import com.interplacement.response.CoursesResponse;

public class CoursesDtoMapper {
	
	public static CoursesResponse toResponseDto(Courses courses) {
		
		return CoursesResponse.builder()
				.id(courses.getId())
				.name(courses.getName())
				.branches(courses.getBranches())
				.status(courses.getStatus())
				.build();
	}

}
