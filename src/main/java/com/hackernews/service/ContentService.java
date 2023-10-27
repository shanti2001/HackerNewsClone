package com.hackernews.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.hackernews.entity.Content;
import com.hackernews.repository.ContentRepositroy;
import com.hackernews.repository.UserRepository;

@Service
public class ContentService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	ContentRepositroy contentRepositroy;
	
	public void addContent(Content content) {
		
		content.setSubmitTime(new Timestamp(new Date().getTime()));
		content.setUser(userRepository.findAll().get(0));
		
		contentRepositroy.save(content);
	}
	public Set<Content> findContentsByTitle(String searchText) {
		String[] searchAll = searchText.split(" ");
		Set<Content> results = new HashSet<>();

		for (String searchOne : searchAll) {
			results.addAll(contentRepositroy.findByTitleContaining(searchOne));
			results.addAll(contentRepositroy.findByUrlContaining(searchOne));
			results.addAll(contentRepositroy.findByTextContaining(searchOne));
		}
		return results;
	}
	
}
