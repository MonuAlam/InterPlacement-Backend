package com.interplacement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interplacement.entity.Courses;

public interface CoursesRepo extends JpaRepository<Courses, String>{

	Courses findTopByOrderByIdDesc();

	boolean existsByName(String name);

}
