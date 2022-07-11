package com.example.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.student.dao.UserService;

@SpringBootApplication
public class StudentSpringBootJpaApplication implements CommandLineRunner{

	@Autowired
	private UserService userService;
	
	public static void main(String[] args){
		SpringApplication.run(StudentSpringBootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
userService.getUserIdAndName("USR001","John");
	}

}
