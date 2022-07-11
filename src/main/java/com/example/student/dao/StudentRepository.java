package com.example.student.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.student.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>{
	    List<Student>findDistinctByIdContainingOrNameContainingOrAttendCourses_NameContaining(String studentId, String studentName, String courseName);
	    
}
