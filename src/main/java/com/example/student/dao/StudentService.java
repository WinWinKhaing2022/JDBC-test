package com.example.student.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.model.Student;
import com.example.student.model.User;




@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;
	
	public List<Student> getAllStudent()
	{
	List<Student> list = (List<Student>) studentRepository.findAll();
	return list;
	}
	
	public void save(Student student)
	{
		studentRepository.save(student);
	}
	
	public void delete(String id)
	{
	studentRepository.deleteById(id);
	}
	
	public void update(Student student)
	{
	studentRepository.save(student);
	}
	
	public Student getStudentId(String id) {

		return studentRepository.findById(id).get();

		}
	
	public List<Student> searchStudent(String id,String name,String course){
		 return studentRepository.findDistinctByIdContainingOrNameContainingOrAttendCourses_NameContaining(id, name, course);
	}
	
	
	
	
}
