package com.example.student.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student.model.User;
@Repository
public interface UserRepository extends JpaRepository<User,String>{
	public boolean existsByEmail(String email);
	List<User> findAllById(String id);
	List<User> findAllByName(String name);
	List<User> findAllByIdOrName(String id,String name);
	User findByEmail(String email);
	public boolean existsByEmailAndPassword(String email,String password);
}
