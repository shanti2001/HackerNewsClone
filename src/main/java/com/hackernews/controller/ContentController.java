package com.hackernews.controller;

import com.hackernews.repository.ContentRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hackernews.entity.Content;
import com.hackernews.service.ContentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


@Controller
public class ContentController {
	
	@Autowired
	ContentService contentService;

	@Autowired
	ContentRepositroy contentRepositroy;



	@GetMapping("/submit")
	public String newContent() {
		return "newContent";
	}
	
	@PostMapping("/addcontent")
	public String addPost(@ModelAttribute Content content) {

		contentService.addContent(content);
		return "redirect:/";
	}

	@GetMapping("/newest")
	public String sortedPostFromLatestDate(Model model)
	{
		List<Content> sortedList = contentRepositroy.findAll(Sort.by(Sort.Order.desc("submitTime")));
		model.addAttribute("contents", sortedList);
		return "home";
	}

	@GetMapping("/front")
	public String sortedPostFromPreviousDate(Model model, @RequestParam("dayQuery") int dayQuery)
	{
		LocalDate oneDayBefore = LocalDate.now().minusDays(dayQuery);
		LocalDateTime startOfDay = oneDayBefore.atStartOfDay();
		LocalDateTime endOfDay = oneDayBefore.atTime(LocalTime.MAX);

		List<Content> sortedList = contentRepositroy.findAllBySubmitTimeBetween(startOfDay, endOfDay);
		model.addAttribute("contents", sortedList);
		return "past";
	}

//	@GetMapping("/content/search")
//	public List<Content> searchContentByKeyword(@RequestParam("keyword") String keyword) {
//		List<Content> searchResults = contentRepositroy.searchByKeyword(keyword);
//		return ResponseEntity.ok(searchResults);;
//	}

	@RequestMapping("/search")
	public String searchBlogPosts(@RequestParam(name = "q", required = false) String query,
								  Model model) {

		Set<Content> searchResults = contentService.findContentsByTitle(query);
		model.addAttribute("contents", searchResults);
		return "home";
	}

}
