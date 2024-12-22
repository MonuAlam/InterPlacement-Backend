package com.interplacement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interplacement.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, String> {

	Admin findTopByOrderByIdDesc();


}
