package com.example.student.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,String>{
	
	public boolean existsByName(String name);

	
}
