package com.interplacement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interplacement.entity.Student;
import com.interplacement.enums.ProfileStatus;

public interface StudentRepo extends JpaRepository<Student, String> {

	Student findTopByOrderByIdDesc();
	
    Optional<Student> findByEmail(String email);

    //public List<Student> findByYearAndStatus(String year, ProfileStatus status);

	List<Student> findByIdInAndStatus(List<String> studentIds, ProfileStatus active);

	List<Student> findByYearInAndStatus(List<String> years, ProfileStatus active);


	
}
