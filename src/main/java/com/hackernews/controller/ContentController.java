package com.hackernews.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	@GetMapping("/newest")
	public String sortedPostFromLatestDate(Model model) {
		List<Content> sortedList = contentRepositroy.findAll(Sort.by(Sort.Order.desc("submitTime")));
		User user = userRepository.findById(1).get();
		model.addAttribute("contents", sortedList);
		model.addAttribute("user",user);
		return "home";
	}

	@GetMapping("/front")
	public String sortedPostFromPreviousDate(Model model, @RequestParam("dayQuery") int dayQuery) {
		LocalDate oneDayBefore = LocalDate.now().minusDays(dayQuery);
		LocalDateTime startOfDay = oneDayBefore.atStartOfDay();
		LocalDateTime endOfDay = oneDayBefore.atTime(LocalTime.MAX);
		
		User user = userRepository.findById(1).get();
		List<Content> sortedList = contentRepositroy.findAllBySubmitTimeBetween(startOfDay, endOfDay);
		model.addAttribute("contents", sortedList);
		model.addAttribute("user",user);
		return "past";
	}

	@RequestMapping("/search")
	public String searchBlogPosts(@RequestParam(name = "q", required = false) String query,
								  Model model) {
		
		User user = userRepository.findById(1).get();
		Set<Content> searchResults = contentService.findContentsByTitle(query);
		model.addAttribute("contents", searchResults);
		model.addAttribute("user",user);
		return "searchResults";
	}
}
