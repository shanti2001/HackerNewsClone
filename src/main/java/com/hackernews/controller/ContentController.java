package com.hackernews.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hackernews.entity.Content;
import com.hackernews.entity.User;
import com.hackernews.repository.ContentRepositroy;
import com.hackernews.repository.UserRepository;
import com.hackernews.service.ContentService;


@Controller
public class ContentController {
	
	@Autowired
	ContentService contentService;
	@Autowired
	ContentRepositroy contentRepositroy;
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/submit")
	public String newContent() {
		return "newContent";
	}
	
	@PostMapping("/addcontent")
	public String addPost(@ModelAttribute Content content) {
		contentService.addContent(content);
		return "redirect:/";
	}
	@GetMapping("/ask")
	public String askContent(Model model) {
		List<Content> contents = contentRepositroy.getAllAsk();
		User user = userRepository.findById(1).get();
		model.addAttribute("contents",contents);
		model.addAttribute("user",user);
		return "showAsk";
	}
	@GetMapping("/show")
	public String showContent(Model model) {
		List<Content> contents = contentRepositroy.getAllShow();
		User user = userRepository.findById(1).get();
		model.addAttribute("contents",contents);
		model.addAttribute("user",user);
		return "showAsk";
	}
	@GetMapping("/job")
	public String jobContent(Model model) {
		List<Content> contents = contentRepositroy.getAllJob();
		User user = userRepository.findById(1).get();
		model.addAttribute("contents",contents);
		model.addAttribute("user",user);
		return "job";
	}
}
