package com.interplacement.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.interplacement.entity.Admin;
import com.interplacement.enums.ProfileStatus;
import com.interplacement.enums.Role;
import com.interplacement.repository.AdminRepo;
import com.interplacement.request.AdminRequest;
import com.interplacement.response.AdminResponse;
import com.interplacement.util.AdminDtoMapper;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Service
public class AdminService {

	@Autowired
	private AdminRepo adminRepo;

	private final AtomicInteger COUNTER = new AtomicInteger(0);
	
	BCryptPasswordEncoder cryptPasswordEncoder=new BCryptPasswordEncoder();

	@PostConstruct
	private void InitializeCounter() {
		Admin lastAdmin = adminRepo.findTopByOrderByIdDesc();

		int lastId = 0;

		if (lastAdmin != null && lastAdmin.getId().startsWith("IP")) {

			lastId = Integer.parseInt(lastAdmin.getId().substring(2));
		}
		COUNTER.set(lastId);
	}

	public AdminResponse createAdmin(AdminRequest request) {
		
		Optional<Admin> existingAdmin=adminRepo.findByEmail(request.getEmail());
		
		if (existingAdmin.isPresent()) {
			throw new RuntimeException("Admin Already Exist can't created");
		}

		Admin admin = toEntity(request);

		return AdminDtoMapper.toResponseDto(adminRepo.save(admin));
	}

	private Admin toEntity(AdminRequest request) {

		return Admin.builder().id(generateCustomId()).email(request.getEmail())
				.password(cryptPasswordEncoder.encode(request.getPassword()))
				.status(ProfileStatus.ACTIVE)
				.role(Role.ADMIN)
				.build();

	}

	private String generateCustomId() {
		int currentId = COUNTER.incrementAndGet();

		return String.format("IP%02d", currentId);
	}

	public List<AdminResponse> getAllAdmin() {

		return adminRepo.findAll().stream().map(AdminDtoMapper::toResponseDto).toList();
	}

	public AdminResponse getAdminById(String id) {

		Admin admin = adminRepo.findById(id).orElseThrow(() -> new RuntimeException("Admin Not found"));

		return AdminDtoMapper.toResponseDto(admin);
	}

	public AdminResponse updateAdmin(String id, @Valid AdminRequest request) {

		Admin admin = adminRepo.findById(id).orElseThrow(() -> new RuntimeException("Admin Not found"));

		Admin updatedAdmin = updateWithBuilder(admin, request);

		return AdminDtoMapper.toResponseDto(adminRepo.save(updatedAdmin));
	}

	private Admin updateWithBuilder(Admin admin, AdminRequest request) {

		return admin.toBuilder().email(request.getEmail()).password(request.getPassword()).build();
	}

	public AdminResponse deleteAdmin(String id) {

		Admin admin = adminRepo.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));

		adminRepo.delete(admin);

		return AdminDtoMapper.toResponseDto(admin);
	}

	public AdminResponse updateStatus(String id, String status) {
		Admin admin = adminRepo.findById(id).orElseThrow(() -> new RuntimeException("Admin Not Found"));

		ProfileStatus newStatus;

		try {
			newStatus = ProfileStatus.valueOf(status.toUpperCase());
		} catch (Exception e) {

			throw new RuntimeException("Invalid Status value");
		}
		
		admin.setStatus(newStatus);
		
		adminRepo.save(admin);

		return AdminDtoMapper.toResponseDto(admin);
	}
}
