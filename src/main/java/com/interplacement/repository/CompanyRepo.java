package com.interplacement.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.interplacement.entity.Company;

public interface CompanyRepo extends JpaRepository<Company, String> {
	
	Company findTopByOrderByIdDesc();

}
