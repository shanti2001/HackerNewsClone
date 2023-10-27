package com.hackernews.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.hackernews.entity.Content;
import com.hackernews.entity.User;
import com.hackernews.repository.ContentRepositroy;
import com.hackernews.repository.UserRepository;
import com.hackernews.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	ContentRepositroy contentRepositroy;
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping({"/","/news"})
	public String getHome(Model model) {
		List<Content> contents = contentRepositroy.getAllShowAndNormal();
		User user = userRepository.findById(1).get();
		List<Integer> hiddenContent = user.getHiddenConnentIds();
//		List<Content> allContents = new ArrayList<>();
		for(int id:hiddenContent) {
			if(contents.contains(contentRepositroy.findById(id).get())) {
				contents.remove(contentRepositroy.findById(id).get());
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
	@PostMapping("/authenticateTheUser")
	public String loginPost() {
		return "redirect:/";
	}
	
	@GetMapping("/user")
	public String userData(@RequestParam("id") String userId, Model model) {
		User user = userRepository.findById(Integer.parseInt(userId)).get();
	
		model.addAttribute("user",user);
		return "UserPageForOther";
	}
	
	@GetMapping("/submitted")
	public String userContent(@RequestParam("id") String userId, Model model) {
		User user = userRepository.findById(Integer.parseInt(userId)).get();		
		model.addAttribute("contents",user.getContents());
		model.addAttribute("user",user);
		return "home";
	}
	@GetMapping("/userpage")
	public String userPage(@RequestParam("id") String userId, Model model) {
		User user = userRepository.findById(Integer.parseInt(userId)).get();
		model.addAttribute("user",user);
//		model.addAttribute("contents",user.getContents());
		return "userpage";
	}
	@GetMapping("/hide")
	public String hideContent(@RequestParam("id") String id, Model model) {
		Content content = contentRepositroy.findById(Integer.parseInt(id)).get();
		User user = userRepository.findById(1).get();
		List<Integer> hiddenContent = user.getHiddenConnentIds();
		if(hiddenContent==null) {
			hiddenContent = new ArrayList<>();
		}
		hiddenContent.add(content.getId());
		userRepository.save(user);
		
		return "redirect:/";
	}
	@GetMapping("/hidden")
	public String hidePage(@RequestParam("id") String id, Model model) {
		
		User user = userRepository.findById(Integer.parseInt(id)).get();
		List<Integer> hiddenContent = user.getHiddenConnentIds();
		
		List<Content> contents = new ArrayList<>();
		for(int contentId:hiddenContent) {
			contents.add(contentRepositroy.findById(contentId).get());
		}
		model.addAttribute("contents",contents);
		model.addAttribute("user",user);
		
		return "hidecontent";
	}
	@GetMapping("/unhide")
	public String unhidePage(@RequestParam("id") String id, Model model) {
		
		Content content = contentRepositroy.findById(Integer.parseInt(id)).get();
		User user = userRepository.findById(1).get();
		List<Integer> hiddenContent = user.getHiddenConnentIds();
		

		hiddenContent.remove((Integer)content.getId());
		
		user.setHiddenConnentIds(hiddenContent);
		userRepository.save(user);
		
		return "redirect:/hidden?id="+user.getId();
	}
	
	@GetMapping("/upvote")
	public String upVote(@RequestParam("id") String id, Model model) {
		Content content = contentRepositroy.findById(Integer.parseInt(id)).get();
		User user = userRepository.findById(1).get();
		List<Content> upVoteContent = user.getUpVoteContent();
		if(upVoteContent==null) {
			upVoteContent = new ArrayList<>();
		}
		upVoteContent.add(content);
		userRepository.save(user);
		
		return "redirect:/";
	}
	@GetMapping("/unvote")
	public String unVote(@RequestParam("id") String id, Model model) {
		Content content = contentRepositroy.findById(Integer.parseInt(id)).get();
		User user = userRepository.findById(1).get();
		List<Content> upVoteContent = user.getUpVoteContent();
		
		upVoteContent.remove(content);
		userRepository.save(user);
		
		return "redirect:/";
	}
	
	@GetMapping("/upvoted")
	public String upVotedContent(@RequestParam("id") String id, Model model) {
		
		User user = userRepository.findById(Integer.parseInt(id)).get();
		List<Content> upVotedContent = user.getUpVoteContent();
		
		
		model.addAttribute("contents",upVotedContent);
		model.addAttribute("user",user);
		
		return "upVoteContent";
	}
	
	@GetMapping("/unvoted")
	public String unVotedPage(@RequestParam("id") String id, Model model) {
		
		Content content = contentRepositroy.findById(Integer.parseInt(id)).get();
		User user = userRepository.findById(1).get();
		List<Content> upVotedContent = user.getUpVoteContent();
		
		upVotedContent.remove(content);
		
		user.setUpVoteContent(upVotedContent);
		userRepository.save(user);
		
		return "redirect:/upvoted?id="+user.getId();
	}
	
	@PostMapping("/updateuser")
	public String updateUser(@ModelAttribute User user) {
		User updateUser = userRepository.findById(user.getId()).get();
		updateUser.setAbout(user.getAbout());
		userRepository.save(updateUser);
		return "redirect:/userpage?id="+user.getId();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}