package com.example.student.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.student.dao.UserRepository;
import com.example.student.dao.UserService;
import com.example.student.model.User;



@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@ModelAttribute("ubean")
	public User getUser() {
		return new User();
	}
	
	
	@GetMapping(value="/login")
		public String login() {
			return "LGN001";					
		}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String setlogin(@RequestParam("email")String email,@RequestParam("password")String password,HttpSession session,@ModelAttribute("ubean") User bean, ModelMap model) {		
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = new Date(System.currentTimeMillis());
	String currentdate = formatter.format(date);
	
	boolean user=userService.existsByEmailAndPassword(email,password);
	if(user==true) {
			User dto = userService.getEmail(email);
			session.setAttribute("userdata", dto);
			session.setAttribute("date", currentdate);
			return "MNU001";
		
	}
	else if(email.isBlank() || password.isBlank()) {
		model.addAttribute("error","Field cannot be blank!!!");
		return "LGN001";
	}
	else {
		model.addAttribute("error","Email and password do not match!!!");
		return "LGN001";
	}
}
	
	@RequestMapping(value="/menu",method=RequestMethod.GET)
	public String Menu() {
		return "MNU001";
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String Logout(ModelMap model,HttpSession session) {
		session.removeAttribute("userdata");
		session.invalidate();
		return "redirect:/login";
	}
	
	@RequestMapping(value="/showuser",method=RequestMethod.GET)
	public String showUser(ModelMap model) {
		List<User> list=userService.getAllUser();
			model.addAttribute("list", list);
		
		return "USR003";
	}
	 
	@RequestMapping(value="/searchUser",method=RequestMethod.POST)
	public String searchUser(@RequestParam("id")String id,@RequestParam("name")String name,ModelMap model) {
		List<User> searchlist=null;
		if(name.isEmpty() && id.isEmpty()) {
			searchlist=userService.getAllUser();
			
		}
			else if(id.isEmpty()) {
			searchlist=userService.getUserByName(name);
		}		
		else if(name.isEmpty()) {
			searchlist=userService.getUserById(id);
		}		
		else {
			searchlist =userService.getUserIdAndName(id,name);
			
		}
		model.addAttribute("list", searchlist);
		return "USR003";
	}
	@RequestMapping(value="/setupAddUser",method=RequestMethod.GET)
	public ModelAndView setupaddUser() {
		return new ModelAndView("USR001","ubean",new User());
	}
	
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public String addUser(@ModelAttribute("ubean")@Validated User bean,BindingResult bs, ModelMap model) {
		if(bs.hasErrors()) {
			return "USR001";
		}
		String name=bean.getName();
		String email=bean.getEmail();
		String password=bean.getPassword();
		String cpassword=bean.getCpassword();
		String role=bean.getRole();
		if(name.isBlank()||email.isBlank()||password.isBlank()||cpassword.isBlank()||role.isBlank()) {
			model.addAttribute("error","Filed cannot be blank!!!");
			model.addAttribute("ubean",bean);
			return "USR001";
		}
		else  if(!password.equals(cpassword)){
			model.addAttribute("error","Password do not match");
			model.addAttribute("ubean", bean);
			return "USR001";
		}
		else {
			List<User> list=userService.getAllUser();
			if(userService.existsByEmail(email)) {
				model.addAttribute("error", "Email already exist!!");
				model.addAttribute("ubean",bean);
				return "USR001";
			}
			else {
				if (list == null) {
					list = new ArrayList<>();
				}
				else if(list.size()==0) {
					bean.setId("USR001");
				}
				else {
					int id=Integer.parseInt(list.get(list.size()-1).getId().substring(3))+1;
					String userid=String.format("USR%03d", id);
					bean.setId(userid);
				}
				
				userService.save(bean);
				
					return "redirect:/showuser";
				}
			}
		}
	
	@RequestMapping (value="/setupUpdate", method=RequestMethod.GET)
	public	 ModelAndView Update(ModelMap model,@RequestParam("id") String id) {
	User user = userService.getId(id);
	
	return new ModelAndView("USR002","ubean",user);
}

@RequestMapping(value = "/UpdateUser", method = RequestMethod.POST)
public String updateUser(@ModelAttribute("ubean") @Validated User ubean, BindingResult bs,ModelMap model,HttpSession session,HttpServletRequest req)  {
		if(bs.hasErrors()) {
			return "USR002";
		}
		String id=ubean.getId();
		String name=ubean.getName();
		String email=ubean.getEmail();
		String password=ubean.getPassword();
		String cpassword=ubean.getCpassword();
		String role=ubean.getRole();
		User sessiondata = (User) session.getAttribute("userdata");
		
		 if(name.isBlank()||email.isBlank()||password.isBlank()||cpassword.isBlank()||role.isBlank()) {
			model.addAttribute("error","Filed cannot be blank!!!");
			return"USR002";
		} 
		else if (!password.equals(cpassword)) {
			model.addAttribute("error", "Do not match password and cpassword!!");
			return "USR002";
		}
		else {
			User res=userService.getId(id);
			User reqdto=new User(id,name,email,password,cpassword,role);
			if(!res.getEmail().equals(email)) {
				if(userService.existsByEmail(email)) {
					model.addAttribute("error","Email already Exist");
					return "USR002";
				} 
				else {
						userService.update( reqdto);
						if(reqdto.getEmail().equals(sessiondata.getEmail())) {
							session.setAttribute("userdata", reqdto);
						}
						return "redirect:/showuser";
				}
			}
			else {
				userService.update(reqdto);
				
				if(reqdto.getEmail().equals(sessiondata.getEmail())) {
					User resdto=new User(id,name,email,password,cpassword,role);
					session.setAttribute("userdata",resdto);
				}
				return "redirect:/showuser";
				
			}
		}
}

	@RequestMapping (value="/deleteUser", method=RequestMethod.GET)
	public	String Delete(ModelMap model,@RequestParam("id") String id) {
		userService.delete(id);
		return "redirect:/showuser";
		
	}
}

