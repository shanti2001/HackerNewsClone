package com.hackernews.controller;

import com.hackernews.repository.ContentRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hackernews.entity.Content;
import com.hackernews.service.ContentService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
	public String sortedPostFromLatestDate(Model model) {
		List<Content> sortedList = contentRepositroy.findAll(Sort.by(Sort.Order.desc("submitTime")));
		model.addAttribute("contents", sortedList);
		return "home";
	}

	@GetMapping("/front")
	public String sortedPostFromPreviousDate(Model model, @RequestParam(value = "day", required = false) String day) {
		DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		if(day == null){
			LocalDate currentDate = LocalDate.now();
			LocalDate previousDay = currentDate.minusDays(1);
			day = previousDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		LocalDate ld = LocalDate.parse(day, DATEFORMATTER);

		LocalDateTime startOfDay = ld.atStartOfDay();
		LocalDateTime endOfDay = ld.atTime(LocalTime.MAX);
		List<Content> sortedList = contentRepositroy.findAllBySubmitTimeBetween(startOfDay, endOfDay);
		model.addAttribute("contents", sortedList);
		model.addAttribute("date",ld);
		System.out.println(ld);
		return "past";
	}

	@GetMapping("/search")
	public String searchBlogPosts(@RequestParam(name = "q", required = false) String query,

								  Model model) {

		Set<Content> searchResults = contentService.findContentsByTitle(query);
		System.out.println(searchResults.size());
		model.addAttribute("contents", searchResults);
		model.addAttribute("query", query);
		return "searchResults";
	}


	@PostMapping("/search")
	public String filterContent(
			@RequestParam(name = "catagory", required = false) String catagory,
			@RequestParam(name = "catagory", required = false) String timeFrame,
			@RequestParam(name = "q", required = false) String query,
			Model model
	) {

		Set<Content> searchResults = contentRepositroy.findFilteredContent(catagory, query);
		//System.out.println(searchResults.size());
		model.addAttribute("contents", searchResults);
		model.addAttribute("query", query);
		return "searchResults";
	}
}
