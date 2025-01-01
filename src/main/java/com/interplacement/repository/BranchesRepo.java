package com.interplacement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interplacement.entity.Branches;

public interface BranchesRepo extends JpaRepository<Branches,String > {

	Branches findTopByOrderByIdDesc();

}
