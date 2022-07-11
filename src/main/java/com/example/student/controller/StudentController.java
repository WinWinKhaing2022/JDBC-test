package com.example.student.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.student.dao.CourseService;
import com.example.student.dao.StudentRepository;
import com.example.student.dao.StudentService;
import com.example.student.model.Course;
import com.example.student.model.Student;


@Controller
public class StudentController {
	 @Autowired
	 private StudentService studentService;
	 @Autowired
	 private CourseService courseService;
	 
	 	 
	 
	 	@RequestMapping(value="/setupaddstudent",method=RequestMethod.GET)
	 	public ModelAndView setupaddstudent(ModelMap model) {
	 		List<Course> list=courseService.getAllCourse();
			model.addAttribute("courseList", list);
			return new ModelAndView("STU001","sbean",new Student());
	 	}
	 	
	 	@RequestMapping(value="/addstudent",method=RequestMethod.POST)
	 	public String addStudent(@ModelAttribute("sbean")@Validated Student sbean,BindingResult br,ModelMap model) {
	 		if(br.hasErrors()) {
	 			List<Course> list=courseService.getAllCourse();
				model.addAttribute("courseList", list);
	 			return "STU001";
	 		}
	 		String name=sbean.getName();
	 		String dob=sbean.getDob();
	 		String gender=sbean.getGender();
	 		String phone=sbean.getPhone();
	 		String education=sbean.getEducation();
	 		List<Course>attendCourses=sbean.getAttendCourses();
	 		if(attendCourses==null) {
	 			model.addAttribute("error","Courses field can not be blank!!");
	 			model.addAttribute("data",sbean);
	 			return "STU001";
	 		}
	 		else 	if (name.isBlank() || dob.isBlank() || gender.isBlank() || phone.isBlank()|| education.isBlank() || attendCourses.isEmpty()) {
	 				model.addAttribute("data",sbean);
	 				model.addAttribute("error","Field can not be blank!!");
	 				return "STU001";
	 				}
	 		else {
	 					List<Student> studentList = studentService.getAllStudent();
	 					
	 					if (studentList.size() == 0) {
	 						sbean.setId("STU001");

	 					} 
	 					else {
	 						int tempId = Integer.parseInt(studentList.get(studentList.size() - 1).getId().substring(3)) + 1;
	 						String userId = String.format("STU%03d", tempId);
	 						sbean.setId(userId);
	 					}
	 					studentService.save(sbean);	 					
	 					return "redirect:/showstudent";
	 		}
	 	}
	 	
	 	 @RequestMapping (value="/showstudent",method=RequestMethod.GET)
		 	public  String setupSearch(ModelMap model) {
		 	List<Student> studentList = studentService.getAllStudent();
				model.addAttribute("studentList", studentList);
				return "STU003";
	 	}

	 	@RequestMapping(value="/searchstudent",method=RequestMethod.POST)
	 	public	String search(ModelMap model,@RequestParam("id") String id,@RequestParam("name") String name,@RequestParam("attendCourses") String attendCourses) {
	 			String sid= id.isBlank()?"@#$%": id;
	 			String sname=name.isBlank()?"@#$%" :name;
	 			String scourse=attendCourses.isBlank()?"@#$%" : attendCourses;
	 			List<Student> studentList=null;
	 					studentList=studentService.searchStudent(sid, sname, scourse);
	 					if (studentList.size() == 0) {
	 		 				studentList = studentService.getAllStudent();
	 		 				model.addAttribute("studentList", studentList);
	 		 				return "STU003";
	 		 			} 
	 		 			else {
	 		 				model.addAttribute("studentList", studentList);
	 		 				return"STU003";
	 		 			}
	 		}
	 		
	 	@RequestMapping (value="/seemore", method=RequestMethod.GET)
	 		public	ModelAndView seeMore(ModelMap model,@RequestParam("id") String id) {
	 			
	 				Student sbean = studentService.getStudentId(id);
	 				
	 				List<Course> courses = courseService.getAllCourse();
	 				
	 				
	 				model.addAttribute("courseList",courses);
	 				return new ModelAndView("STU002","sbean",sbean);
	 			}
	 	
	 
	 	
	 				
	 		@RequestMapping(value="/updatestudent",method=RequestMethod.POST)
	 			public String updateStudent	(ModelMap model,@ModelAttribute ("sbean") @Validated Student sbean,BindingResult br) {
	 			if(br.hasErrors()) {
	 				List<Course> list=courseService.getAllCourse();
	 				model.addAttribute("courseList", list);
	 				return "STU002";
	 			}
	 			String id=sbean.getId();
	 			String name=sbean.getName();
	 	 		String dob=sbean.getDob();
	 	 		String gender=sbean.getGender();
	 	 		String phone=sbean.getPhone();
	 	 		String education=sbean.getEducation();
	 	 		List<Course>attendCourses=sbean.getAttendCourses();
	 		
	 			if (attendCourses == null) {
	 				sbean = new Student(id, name, dob, gender, phone, education);
	 				model.addAttribute("error", "Fill the blank !!");
	 				
	 				return "STU002";
	 			}
	 			else {
	 				 if (name.isBlank() || dob.isBlank() || gender.isBlank() || phone.isBlank() || education.isBlank()) {
	 					
	 					sbean = new Student(id, name, dob, gender, phone, education, attendCourses);
	 					model.addAttribute("error", "Fill the blank !!");
	 					
	 					return "STU002";
	 					
	 				 }
	 				 else {
	 					studentService.update(sbean);
	 					return "redirect:/showstudent";
	 				 }
	 			
	 		}
	 			
	 	}
	 		@RequestMapping (value="/Deletestudent", method=RequestMethod.GET)
	 		public	String deleteStudent(ModelMap model,@RequestParam("id") String stuid) {
	 			studentService.delete(stuid);
	 			return "redirect:/showstudent";
	 			
	 		}
}
