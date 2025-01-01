package com.interplacement.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.interplacement.request.JobApplyRequest;
import com.interplacement.response.JobApplyResponse;
import com.interplacement.service.JobApplyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ip/jobapply")
public class JobApplyController {

	@Autowired
	private JobApplyService jobApplyService;

	@PostMapping("/{studentId}/{jobPostId}")
	public JobApplyResponse createJobApplication(@PathVariable String studentId, @PathVariable String jobPostId,
			@Valid @RequestBody JobApplyRequest request) throws IOException {

		return jobApplyService.createJobApply(studentId, jobPostId, request);
	}

	@GetMapping
	public List<JobApplyResponse> getAllApplications() {

		return jobApplyService.getAllApplications();
	}

	@GetMapping("/{id}")
	public JobApplyResponse getApplicationById(@PathVariable String id) {

		return jobApplyService.getApplicationById(id);
	}

	@PutMapping("/{id}")
	public JobApplyResponse updateApplication(@PathVariable String id, @Valid @RequestBody JobApplyRequest request)
			throws IOException {

		return jobApplyService.updateApplication(id, request);
	}

	@DeleteMapping("/{id}")
	public JobApplyResponse deleteApplication(@PathVariable String id) {

		return jobApplyService.deleteApplication(id);
	}

	@PatchMapping("/{id}/status")
	public JobApplyResponse updateApplicationStatus(@PathVariable String id, @RequestParam String status) {
		
		return jobApplyService.updateStatus(id, status);
	}

}
