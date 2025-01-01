package com.interplacement.controller;

import java.io.IOException;
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

import com.interplacement.request.StudentRequest;
import com.interplacement.response.StudentResponse;
import com.interplacement.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ip/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@PostMapping("/{collegeId}")
	public StudentResponse createStudent(@PathVariable String collegeId,@Valid @RequestBody StudentRequest request) throws IOException {
		
		return studentService.createStudent(collegeId,request);
	}
	
	@PostMapping("/register")
	public StudentResponse registerStudent(@Valid @RequestBody StudentRequest request) throws IOException {
		
		return studentService.registerStudent(request);
	}
	
	
	@GetMapping
	public List<StudentResponse> getAllStudent() {
		
		return studentService.getAllStudent();
	}
	
	@GetMapping("/{id}")
	public StudentResponse getStudentById(@PathVariable String id) {
		
		return studentService.getStudentById(id);
	}
	
	@PutMapping("/{id}")
	public StudentResponse updateStudent(@PathVariable String id,@Valid @RequestBody StudentRequest request) throws IOException {
		
		return studentService.updateStudent(id,request);
	}
	
	@PatchMapping("/{id}/status")
	public StudentResponse updateStatus(@PathVariable String id, @RequestParam String status) {
		
		return studentService.updateStatus(id,status);
	}
	
	@DeleteMapping("/{id}")
	public StudentResponse deleteStudent(@PathVariable String id) {
		
		return studentService.deleteStudent(id);
	}
}
