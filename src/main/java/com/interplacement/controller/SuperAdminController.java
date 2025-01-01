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

import com.interplacement.request.SuperAdminRequest;
import com.interplacement.response.SuperAdminResponse;
import com.interplacement.service.SuperAdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ip/superadmin")
public class SuperAdminController {

	@Autowired
	private SuperAdminService superAdminService;

	@PostMapping
	public SuperAdminResponse createSuperAdmin(@Valid @RequestBody SuperAdminRequest request) {

		return superAdminService.createSuperAdmin(request);
	}

	@GetMapping
	public List<SuperAdminResponse> getAllSuperAdmins() {

		return superAdminService.getAllSuperAdmins();
	}

	@GetMapping("/{id}")
	public SuperAdminResponse getSuperAdminById(@PathVariable String id) {
		return superAdminService.getSuperAdminById(id);
	}

	@PutMapping("/{id}")
	public SuperAdminResponse updateSuperAdmin(@PathVariable String id, @Valid @RequestBody SuperAdminRequest request) {
		return superAdminService.updateSuperAdmin(id, request);
	}

	@DeleteMapping("/{id}")
	public SuperAdminResponse deleteSuperAdmin(@PathVariable String id) {
		return superAdminService.deleteSuperAdmin(id);
	}

	@PatchMapping("/{id}/status")
	public SuperAdminResponse updateStatus(@PathVariable String id, @RequestParam String status) {
	
		return superAdminService.updateStatus(id, status);
	}

}
