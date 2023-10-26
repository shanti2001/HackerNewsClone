package com.hackernews.controller;

import com.hackernews.repository.ContentRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hackernews.entity.Content;
import com.hackernews.service.ContentService;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


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


}
