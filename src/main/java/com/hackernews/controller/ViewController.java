package com.hackernews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hackernews.entity.Content;
import com.hackernews.entity.User;
import com.hackernews.repository.ContentRepositroy;
import com.hackernews.repository.UserRepository;

@Controller
public class ViewController {
	@Autowired
	ContentRepositroy contentRepositroy;
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/newsguidelines")
	public String showGuidelines() {
		return "newsguidelines";
	}
	
	@GetMapping("/showcontent")
	public String showcontent(@RequestParam("id") String id, Model model) {
		Content content = contentRepositroy.findById(Integer.parseInt(id)).get();
		User user = userRepository.findById(1).get();
		
		
		model.addAttribute("content",content);
		model.addAttribute("user",user);
		
		return "showContent";
	}
}
