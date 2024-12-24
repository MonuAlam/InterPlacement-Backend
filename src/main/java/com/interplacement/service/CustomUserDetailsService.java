package com.interplacement.service;

import com.interplacement.entity.Admin;
import com.interplacement.entity.Company;
import com.interplacement.entity.Student;
import com.interplacement.enums.ProfileStatus;
import com.interplacement.enums.Role;
import com.interplacement.repository.AdminRepo;
import com.interplacement.repository.CompanyRepo;
import com.interplacement.repository.StudentRepo;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  
//    @PostConstruct
//    private void initializeDefaultAdmin() {
//        if (adminRepo.count() == 0) {
//            Admin defaultAdmin = Admin.builder()
//                    .id("IP01")
//                    .email("admin@gmail.com")
//                    .password(new BCryptPasswordEncoder().encode("admin123"))
//                    .status(ProfileStatus.ACTIVE)
//                    .build();
//            adminRepo.save(defaultAdmin);
//        }
//    }

    
    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Check in Admin table
        Admin admin = adminRepo.findByEmail(email).orElse(null);
        if (admin != null) {
            return User.builder()
                    .username(admin.getEmail())
                    .password(admin.getPassword())
                    .roles(Role.ADMIN.name()) // Use Role enum for admin
                    .build();
        }

        // Check in Student table
        Student student = studentRepo.findByEmail(email).orElse(null);
        if (student != null) {
            return User.builder()
                    .username(student.getEmail())
                    .password(student.getPassword())
                    .roles(Role.STUDENT.name()) // Use Role enum for student
                    .build();
        }

        // Check in Company table
        Company company = companyRepo.findByEmail(email).orElse(null);
        if (company != null) {
            return User.builder()
                    .username(company.getEmail())
                    .password(company.getPassword())
                    .roles(Role.COMPANY.name()) // Use Role enum for company
                    .build();
        }

        // If no match is found, throw an exception
        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
