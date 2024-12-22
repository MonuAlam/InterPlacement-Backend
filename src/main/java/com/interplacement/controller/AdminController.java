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

import com.interplacement.request.AdminRequest;
import com.interplacement.response.AdminResponse;
import com.interplacement.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ip/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping
	public AdminResponse createAdmin(@Valid @RequestBody AdminRequest request) {
		
		return adminService.createAdmin(request);
	}
	
	@GetMapping
	public List<AdminResponse> getAllAdmin(){
		
		return adminService.getAllAdmin();
	}
	
	@GetMapping("/{id}")
	public AdminResponse getAdminById(@PathVariable String id) {
		
		return adminService.getAdminById(id);
	}
	
	@PutMapping("/{id}")
	public AdminResponse updateAdmin(@PathVariable String id,@Valid @RequestBody AdminRequest request) {
		
		return adminService.updateAdmin(id,request);
	}
	
	@DeleteMapping("/{id}")
	public AdminResponse deleteAdmin(@PathVariable String id) {
		
		return adminService.deleteAdmin(id);
	}
	
	
	@PatchMapping("/{id}/status")
	public AdminResponse updateStatus(@PathVariable String id, @RequestParam String status) {
		
		return adminService.updateStatus(id,status);
	}

}
