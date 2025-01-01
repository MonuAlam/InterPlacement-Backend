package com.interplacement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interplacement.entity.JobApply;

public interface JobApplyRepo  extends JpaRepository<JobApply, String>{

	JobApply findTopByOrderByIdDesc();

}
