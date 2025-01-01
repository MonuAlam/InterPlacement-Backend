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
import com.interplacement.request.CoursesRequest;
import com.interplacement.response.CoursesResponse;
import com.interplacement.service.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {
	
	@Autowired
	private CourseService courseService;

	@PostMapping
	public CoursesResponse createCourse(@RequestBody CoursesRequest courseRequest) {
		
		return courseService.createCourse(courseRequest);
	}

	@GetMapping
	public List<CoursesResponse> getAllCourses() {
		
		return courseService.getAllCourses();
	}

	@GetMapping("/{id}")
	public CoursesResponse getCourseById(@PathVariable String id) {

		return courseService.getCourseById(id);
	}

	@PutMapping("/{id}")
	public CoursesResponse updateCourse(@PathVariable String id, @RequestBody CoursesRequest courseRequest) {

		return courseService.updateCourse(id, courseRequest);
	}

	@DeleteMapping("/{id}")
	public CoursesResponse deleteCourse(@PathVariable String id) {

		return courseService.deleteCourse(id);
	}

	@PatchMapping("/{id}/status")
	public CoursesResponse updateStatus(@PathVariable String id, @RequestParam String status) {

		return courseService.updateStatus(id, status);
	}

	
	}
