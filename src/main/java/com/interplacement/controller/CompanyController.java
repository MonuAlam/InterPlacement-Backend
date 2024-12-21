package com.interplacement.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.interplacement.service.CompanyService;
import jakarta.validation.Valid;
import com.interplacement.request.CompanyRequest;
import com.interplacement.response.CompanyResponse;

@RestController
@RequestMapping("/ip/company")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PostMapping
	public CompanyResponse saveCompany(@Valid @RequestBody CompanyRequest companyRequest) throws IOException {

		return companyService.saveCompany(companyRequest);
	}

	@GetMapping
	public List<CompanyResponse> getAllCompanies() {

		return companyService.getAllCompanies();
	}

	@GetMapping("/{id}")
	public CompanyResponse getCompanyById(@PathVariable String id) {

		return companyService.getCompanyById(id);
	}

	@DeleteMapping("/{id}")
	public CompanyResponse deleteCompany(@PathVariable String id) {

		return companyService.deleteCompany(id);
	}

	@PutMapping("/{id}")
	public CompanyResponse updateCompany(@PathVariable String id, @Valid @RequestBody CompanyRequest companyRequest) throws IOException {

		return companyService.updateCompany(id, companyRequest);
	}

	@PatchMapping("/{id}/status")
	public CompanyResponse updateStatus(@PathVariable String id, @RequestParam String status) {

		return companyService.updateStatus(id, status);
	}
}
