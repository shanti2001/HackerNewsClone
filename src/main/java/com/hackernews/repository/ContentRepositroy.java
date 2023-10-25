package com.hackernews.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackernews.entity.Content;

public interface ContentRepositroy extends JpaRepository<Content, Integer>{
	
}
