package com.hackernews.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
		content.setUser(userRepository.findAll().get(1));
		
		contentRepositroy.save(content);
	}
	
}
