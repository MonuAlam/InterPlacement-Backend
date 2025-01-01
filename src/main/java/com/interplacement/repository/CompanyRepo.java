package com.interplacement.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interplacement.entity.Company;
import com.interplacement.enums.ProfileStatus;

public interface CompanyRepo extends JpaRepository<Company, String> {
	
	Company findTopByOrderByIdDesc();

    Optional<Company> findByEmail(String email);

	List<Company> findByIdIn(List<String> companyIds);

	List<Company> findByIdInAndStatus(List<String> companyIds, ProfileStatus active);

}
