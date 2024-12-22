package com.interplacement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interplacement.request.JobPostRequest;
import com.interplacement.response.JobPostResponse;
import com.interplacement.service.JobPostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ip/jobpost")
@CrossOrigin(origins = "http://localhost:3000")
public class JobPostController {

	@Autowired
	private JobPostService jobPostService;

//	@PostMapping("/{companyId}")
//	public JobPostResponse createJobPost(@PathVariable String companyId, @Valid @RequestBody JobPostRequest request) {
//
//		return jobPostService.createJobPost(companyId, request);
//	}
//
//	@GetMapping("/{companyId}")
//	public List<JobPostResponse> getAllJobPostByComp(@PathVariable String companyId) {
//
//		return jobPostService.getAllJobPostByComp(companyId);
//	}
//
//	@GetMapping("/{id}")
//	public JobPostResponse getJobPostById(@PathVariable String id) {
//
//		return jobPostService.getJobPostById(id);
//	}
//
//	@PutMapping("/{id}")
//	public JobPostResponse updateJobPost(@PathVariable String id, @Valid @RequestBody JobPostRequest request) {
//
//		return jobPostService.updateJobPost(id, request);
//	}
//
//	@PatchMapping("/{id}/status")
//	public JobPostResponse updateStatus(@PathVariable String id, @RequestParam String status) {
//
//		return jobPostService.updateStatus(id, status);
//	}
//	
//	@DeleteMapping("/{id}")
//	public JobPostResponse deleteJobPost(@PathVariable String id) {
//		
//		return jobPostService.deleteJobPost(id);
//	}
}
