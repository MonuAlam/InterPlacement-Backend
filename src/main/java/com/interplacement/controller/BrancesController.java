package com.interplacement.controller;

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
import com.interplacement.request.BranchesRequest;
import com.interplacement.response.BranchesResponse;
import com.interplacement.service.BranchesService;

@RestController
@RequestMapping("/branches")
public class BrancesController {

	@Autowired
	private BranchesService branchesService;

	@PostMapping("/{courseId}")
	public BranchesResponse addBranchToCourse(@PathVariable String courseId, @RequestBody BranchesRequest request) {
		return branchesService.addBranchToCourse(courseId, request);
	}

	@GetMapping("/{branchId}")
	public BranchesResponse getBranchById(@PathVariable String branchId) {
		return branchesService.getBranchById(branchId);
	}

	@GetMapping
	public List<BranchesResponse> getAllBranches() {
		return branchesService.getAllBranches();
	}

	@PutMapping("/{branchId}")
	public BranchesResponse updateBranch(@PathVariable String branchId, @RequestBody BranchesRequest request) {
		return branchesService.updateBranch(branchId, request);
	}

	@DeleteMapping("/{branchId}")
	public BranchesResponse deleteBranch(@PathVariable String branchId) {

		return branchesService.deleteBranch(branchId);
	}

	@PatchMapping("/{id}/status")
	public BranchesResponse updateStatus(@PathVariable String id, @RequestParam String status) {

		return branchesService.updateStatus(id, status);
	}

}
