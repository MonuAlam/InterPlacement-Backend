package com.interplacement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interplacement.entity.JobPost;

public interface JobPostRepo extends JpaRepository<JobPost, String> {

}
