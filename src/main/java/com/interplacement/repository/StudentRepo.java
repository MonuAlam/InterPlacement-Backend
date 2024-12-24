package com.interplacement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interplacement.entity.Student;

public interface StudentRepo extends JpaRepository<Student, String> {

	Student findTopByOrderByIdDesc();
	
    Optional<Student> findByEmail(String email);

}
