package com.interplacement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interplacement.entity.SuperAdmin;

public interface SuperAdminRepo extends JpaRepository<SuperAdmin, String>{

	SuperAdmin findTopByOrderByIdDesc();

	Optional<SuperAdmin> findByEmail(String email);

}
