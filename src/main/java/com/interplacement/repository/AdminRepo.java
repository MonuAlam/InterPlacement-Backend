package com.interplacement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interplacement.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, String> {

	Admin findTopByOrderByIdDesc();

    Optional<Admin> findByEmail(String email);

}
