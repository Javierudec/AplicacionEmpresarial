package es.model.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.model.service.MovieService;

@Controller
public class MovieController {
	
	private MovieService movieService;
	
	@RequestMapping(method = RequestMethod.GET)
	public void list(Model model) {		
		//List<Department> list = departmentService.getDepartments();
		//model.addAttribute("departmentList", list);
	}
	
	/*
	@RequestMapping(method = RequestMethod.GET)
	public Department form(Model model) {
		return new Department(0, "No name", "No location");
	}
	
	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
	public String addDepartment(Department department) {
		
		System.out.println(department);
		
		return "redirect:/main/dep/list";
	}
	
	*/
	/* EmployeeService */
	public void setMovieService(MovieService movieService) {
		this.movieService = movieService;
	}
}
