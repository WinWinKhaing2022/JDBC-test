package com.example.student.dao;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.model.User;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public List<User> getAllUser()
	{
	List<User> list = (List<User>) userRepository.findAll();
	return list;
	}
	
	public void save(User user)
	{
		userRepository.save(user);
	}
	
	public void delete(String id)
	{
	userRepository.deleteById(id);
	}
	
	public void update(User user)
	{
	userRepository.save(user);
	}
	
	
	public List<User> getUserById(String id){
		return userRepository.findAllById(id);
	}
	
	public List<User> getUserByName(String name){
		return userRepository.findAllByName(name);
	}
	
	public List<User> getUserIdAndName(String id,String name){
		return userRepository.findAllByIdOrName(id, name);
	}
	public User getId(String id) {

		return userRepository.findById(id).get();

		}
	

	public User getEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public boolean existsByEmailAndPassword(String email,String password) {
		return userRepository.existsByEmailAndPassword(email, password);
	}
	

}