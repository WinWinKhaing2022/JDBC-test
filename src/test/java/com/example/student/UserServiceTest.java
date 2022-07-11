package com.example.student;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.student.dao.UserRepository;
import com.example.student.dao.UserService;
import com.example.student.model.User;

@SpringBootTest
public class UserServiceTest {
	@Mock
	UserRepository repo;
	@InjectMocks
	UserService userService;
	@Test
	public void getAllUserTest() {
		List<User> list=new ArrayList<User>();
		User u1=new User();
		u1.setId("USR001");
		u1.setName("Mg");
		u1.setEmail("mg@gmail.com");
		u1.setPassword("122");
		u1.setCpassword("122");
		u1.setRole("Admin");
		User u2=new User();
		u2.setId("USR002");
		u2.setName("Kyaw");
		u2.setEmail("kyaw@gmail.com");
		u2.setPassword("121");
		u2.setCpassword("121");
		u2.setRole("User");
		list.add(u1);
		list.add(u2);
		when(repo.findAll()).thenReturn(list);
		List<User> userList=userService.getAllUser();
		assertEquals(3,userList.size());
		verify(repo,times(1)).findAll();
	}
	
	@Test
	public void getUserByIdTest() {
		List<User> list=new ArrayList<User>();
		User u1=new User();
		u1.setId("USR001");
		u1.setName("Mg");
		u1.setEmail("mg@gmail.com");
		u1.setPassword("122");
		u1.setCpassword("122");
		u1.setRole("Admin");
		User u2=new User();
		u2.setId("USR002");
		u2.setName("Hla");
		u2.setEmail("hla@gmail.com");
		u2.setPassword("333");
		u2.setCpassword("333");
		u2.setRole("User");
		list.add(u1);
		list.add(u2);
		when(repo.findAllById("USR001")).thenReturn(list);
		List<User> userlist=userService.getUserById("USR001");
		assertEquals(1,userlist.size());
		verify(repo,times(1)).findAllById("USR001");
		
	}
	
	@Test
	public void getUserByNameTest() {
		List<User> list=new ArrayList<User>();
		User u1=new User();
		u1.setId("USR001");
		u1.setName("Mg");
		u1.setEmail("mg@gmail.com");
		u1.setPassword("122");
		u1.setCpassword("122");
		u1.setRole("Admin");
		User u2=new User();
		u2.setId("USR002");
		u2.setName("Hla");
		u2.setEmail("hla@gmail.com");
		u2.setPassword("333");
		u2.setCpassword("333");
		u2.setRole("User");
		list.add(u1);
		list.add(u2);
		when(repo.findAllByName("Mg")).thenReturn(list);
		List<User> userlist=userService.getUserByName("Mg");
		assertEquals(1,userlist.size());
		verify(repo,times(1)).findAllByName("Mg");
	}
	
	@Test
	public void getUserIdAndNameTest() {
		List<User> list=new ArrayList<User>();
		User u1=new User();
		u1.setId("USR001");
		u1.setName("Mg");
		u1.setEmail("mg@gmail.com");
		u1.setPassword("122");
		u1.setCpassword("122");
		u1.setRole("Admin");
		User u2=new User();
		u2.setId("USR002");
		u2.setName("Hla");
		u2.setEmail("hla@gmail.com");
		u2.setPassword("333");
		u2.setCpassword("333");
		u2.setRole("User");
		list.add(u1);
		list.add(u2);
		
		when(repo.findAllByIdOrName("USR001","Mg")).thenReturn(list);
		List<User> userlist=userService.getUserIdAndName("USR001","Mg");
		assertEquals(1,userlist.size());
		verify(repo,times(1)).findAllByIdOrName("USR001","Mg");
		
	}
	
	@Test
	public void saveTest() {
		User user=new User();
		user.setId("USR001");
		user.setName("Mg");
		user.setEmail("mg@gmail.com");
		user.setPassword("122");
		user.setCpassword("122");
		user.setRole("Admin");
		userService.save(user);
		verify(repo,times(1)).save(user);
		
	}
	
	@Test
	public void updateTest() {
		User user=new User();
		user.setId("USR001");
		user.setName("Mg");
		user.setEmail("mg@gmail.com");
		user.setPassword("122");
		user.setCpassword("122");
		user.setRole("Admin");
		userService.update(user);
		verify(repo,times(1)).save(user);
		
	}
	
	@Test
	public void deleteTest() {
		userService.delete("USR001");
		verify(repo,times(1)).deleteById("USR001");
	}
	
	@Test
	public void getIdTest() {
		User user=new User();
		user.setId("USR001");
		user.setName("Mg");
		user.setEmail("mg@gmail.com");
		user.setPassword("122");
		user.setCpassword("122");
		user.setRole("Admin");
		when(repo.findById("USR001")).thenReturn(Optional.ofNullable(user));
		User getUser=userService.getId("USR001");
		assertEquals("Mg",getUser.getName());
		assertEquals("mg@gmail.com",getUser.getEmail());
		assertEquals("122",getUser.getPassword());
		assertEquals("122",getUser.getCpassword());
		assertEquals("Admin",getUser.getRole());
		verify(repo,times(1)).findById("USR001");
		
		
	}
	
	@Test
	public void getEmailTest() {
		User user=new User();
		user.setId("USR001");
		user.setName("Mg");
		user.setEmail("mg@gmail.com");
		user.setPassword("122");
		user.setCpassword("122");
		user.setRole("Admin");
		when(repo.findByEmail("USR001")).thenReturn(user);
		User getUser=userService.getId("USR001");
		assertEquals("Mg",getUser.getName());
		assertEquals("mg@gmail.com",getUser.getEmail());
		assertEquals("122",getUser.getPassword());
		assertEquals("122",getUser.getCpassword());
		assertEquals("Admin",getUser.getRole());
		verify(repo,times(1)).findById("USR001");
		
		
	}
	
	
	
	@Test
	public void existsByEmailTest() {
		Boolean check = true;
		when(repo.existsByEmail("win@gmail.com")).thenReturn(check);
		Boolean b=repo.existsByEmail("win@gmail.com");
		assertEquals(true,b);
		verify(repo,times(1)).existsByEmail("win@gmail.com");
	}
	
	@Test
	public void existsByEmailAndPasswordTest() {
		Boolean check=true;
		when(repo.existsByEmailAndPassword("win@gmail.com", "122")).thenReturn(check);
		Boolean b=repo.existsByEmailAndPassword("win@gmail.com", "122");
		assertEquals(true,b);
		verify(repo,times(1)).existsByEmailAndPassword("win@gmail.com", "122");
	}
}
