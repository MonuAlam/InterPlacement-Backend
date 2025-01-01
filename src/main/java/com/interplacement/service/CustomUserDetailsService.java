package com.interplacement.service;

import com.interplacement.entity.Admin;
import com.interplacement.entity.Company;
import com.interplacement.entity.Student;
import com.interplacement.entity.SuperAdmin;
import com.interplacement.enums.ProfileStatus;
import com.interplacement.enums.Role;
import com.interplacement.repository.AdminRepo;
import com.interplacement.repository.CompanyRepo;
import com.interplacement.repository.StudentRepo;
import com.interplacement.repository.SuperAdminRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

	@Autowired
	private SuperAdminRepo superAdminRepo;

	private SuperAdmin SuperAdmin;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Admin admin = adminRepo.findByEmail(email).orElse(null);
		if (admin != null) {
			if (admin.getStatus() == ProfileStatus.INACTIVE) {
				throw new RuntimeException("Admin account is inactive.");
			}
			return buildUserDetails(admin.getEmail(), admin.getPassword(), Role.ADMIN.name());
		}

		Student student = studentRepo.findByEmail(email).orElse(null);
		if (student != null) {
			if (student.getStatus() == ProfileStatus.INACTIVE) {
				throw new UsernameNotFoundException("Student account is inactive.");
			}
			return buildUserDetails(student.getEmail(), student.getPassword(), Role.STUDENT.name());
		}

		Company company = companyRepo.findByEmail(email).orElse(null);
		if (company != null) {
			if (company.getStatus() == ProfileStatus.INACTIVE) {
				throw new UsernameNotFoundException("Company account is inactive.");
			}
			return buildUserDetails(company.getEmail(), company.getPassword(), Role.COMPANY.name());
		}

		SuperAdmin superAdmin = superAdminRepo.findByEmail(email).orElse(null);

		if (superAdmin != null) {
			if (superAdmin.getStatus() == ProfileStatus.INACTIVE) {
				throw new UsernameNotFoundException("Super Admin account is inactive.");
			}
			return buildUserDetails(superAdmin.getEmail(), superAdmin.getPassword(), Role.SUPER.name());
		}

		throw new UsernameNotFoundException("User not found with email: " + email);
	}

	private UserDetails buildUserDetails(String email, String password, String role) {
		
		return User.builder().username(email).password(password).roles(role).build();
	}

}
