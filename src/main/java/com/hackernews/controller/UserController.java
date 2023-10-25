package com.hackernews.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hackernews.entity.Content;
import com.hackernews.repository.ContentRepositroy;
import com.hackernews.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	ContentRepositroy contentRepositroy;
	
	@RequestMapping({"/","/news"})
	public String getHome(Model model) {
		List<Content> contents = contentRepositroy.findAll();
		model.addAttribute("contents",contents);
		return "home";
	}
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@PostMapping("/register")
	public String addUser(@RequestParam(name = "name") String name,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "confirmPassword") String confirmPassword,Model model) {
		
		if(password.equals(confirmPassword) ) {
			userService.addUser(name, email, password, password);
			return "redirect:/login";
		}
		else {
			model.addAttribute("error","Sorry! Email already in use");
			return "register";
		}
		
	}
}