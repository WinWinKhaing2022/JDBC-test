package com.example.student.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.model.Course;


@Service

public class CourseService {

	@Autowired
	CourseRepository courseRepository;
	
	public List<Course> getAllCourse()
	{
	List<Course> list = (List<Course>) courseRepository.findAll();
	return list;
	}
	
	public void save(Course course) {
		courseRepository.save(course);
	}
	
	
	
}
