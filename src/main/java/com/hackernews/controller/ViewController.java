package com.hackernews.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	public User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userRepository.getUserByUserName(username);
        return author;
	}
	
	@RequestMapping({"/","/news"})
	public String getHome(Model model) {
		List<Content> contents = contentRepositroy.getAllShowAndNormal();
		User user = getUser();
		if(user!=null) {
			List<Integer> hiddenContent = user.getHiddenConnentIds();
			for(int id:hiddenContent) {
				if(contents.contains(contentRepositroy.findById(id).get())) {
					contents.remove(contentRepositroy.findById(id).get());
				}
			}
		}
		
		model.addAttribute("user",user);
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
	@PostMapping("/authenticateTheUser")
	public String loginPost() {
		return "redirect:/";
	}
	
	@GetMapping("/newsguidelines")
	public String showGuidelines() {
		return "newsguidelines";
	}
	
	@GetMapping("/showcontent")
	public String showcontent(@RequestParam("id") String id, Model model) {
		Content content = contentRepositroy.findById(Integer.parseInt(id)).get();
		User user = getUser();
		
		
		model.addAttribute("content",content);
		model.addAttribute("user",user);
		
		return "showContent";
	}
}
