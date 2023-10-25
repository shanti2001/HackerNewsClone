package com.hackernews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hackernews.entity.Content;
import com.hackernews.service.ContentService;


@Controller
public class ContentController {
	
	@Autowired
	ContentService contentService;
	
	@GetMapping("/submit")
	public String newContent() {
		return "newContent";
	}
	
	@PostMapping("/addcontent")
	public String addPost(@ModelAttribute Content content) {
		contentService.addContent(content);
		return "redirect:/";
	}
}
