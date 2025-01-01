package com.interplacement.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.interplacement.entity.SuperAdmin;
import com.interplacement.enums.ProfileStatus;
import com.interplacement.enums.Role;
import com.interplacement.repository.SuperAdminRepo;
import com.interplacement.request.SuperAdminRequest;
import com.interplacement.response.SuperAdminResponse;
import com.interplacement.util.SuperAdminDtoMapper;

import jakarta.annotation.PostConstruct;

@Service
public class SuperAdminService {

	
    @Autowired
    private SuperAdminRepo superAdminRepo;

    private final AtomicInteger COUNTER = new AtomicInteger(0);

    BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    private void initializeCounter() {
        SuperAdmin lastSuperAdmin = superAdminRepo.findTopByOrderByIdDesc();

        int lastId = 0;

        if (lastSuperAdmin != null && lastSuperAdmin.getId().startsWith("SA")) {
            lastId = Integer.parseInt(lastSuperAdmin.getId().substring(2));
        }
        COUNTER.set(lastId);
    }

    public SuperAdminResponse createSuperAdmin(SuperAdminRequest request) {
    	
        Optional<SuperAdmin> existingSuperAdmin = superAdminRepo.findByEmail(request.getEmail());

        if (existingSuperAdmin.isPresent()) {
            throw new RuntimeException("SuperAdmin already exists. Can't create.");
        }

        SuperAdmin superAdmin = toEntity(request);

        return SuperAdminDtoMapper.toResponseDto(superAdminRepo.save(superAdmin));
    }

    private SuperAdmin toEntity(SuperAdminRequest request) {
        return SuperAdmin.builder()
                .id(generateCustomId())
                .email(request.getEmail())
                .password(cryptPasswordEncoder.encode(request.getPassword()))
                .status(ProfileStatus.ACTIVE)
                .role(Role.SUPER)
                .build();
    }

    private String generateCustomId() {
        int currentId = COUNTER.incrementAndGet();
        return String.format("IP%02d", currentId);
    }

    public List<SuperAdminResponse> getAllSuperAdmins() {
        return superAdminRepo.findAll().stream().map(SuperAdminDtoMapper::toResponseDto).toList();
    }

    public SuperAdminResponse getSuperAdminById(String id) {
        SuperAdmin superAdmin = superAdminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("SuperAdmin not found"));

        return SuperAdminDtoMapper.toResponseDto(superAdmin);
    }

    public SuperAdminResponse updateSuperAdmin(String id,  SuperAdminRequest request) {
        SuperAdmin superAdmin = superAdminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("SuperAdmin not found"));

        SuperAdmin updatedSuperAdmin = updateWithBuilder(superAdmin, request);

        return SuperAdminDtoMapper.toResponseDto(superAdminRepo.save(updatedSuperAdmin));
    }

    private SuperAdmin updateWithBuilder(SuperAdmin superAdmin, SuperAdminRequest request) {
        return superAdmin.toBuilder()
                .email(request.getEmail())
                .password(cryptPasswordEncoder.encode(request.getPassword()))
                .build();
    }

    public SuperAdminResponse deleteSuperAdmin(String id) {
        SuperAdmin superAdmin = superAdminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("SuperAdmin not found"));

        superAdminRepo.delete(superAdmin);

        return SuperAdminDtoMapper.toResponseDto(superAdmin);
    }

    public SuperAdminResponse updateStatus(String id, String status) {
        SuperAdmin superAdmin = superAdminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("SuperAdmin not found"));

        ProfileStatus newStatus;
        try {
            newStatus = ProfileStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid status value");
        }

        superAdmin.setStatus(newStatus);
        superAdminRepo.save(superAdmin);

        return SuperAdminDtoMapper.toResponseDto(superAdmin);
    }
	
	
}
