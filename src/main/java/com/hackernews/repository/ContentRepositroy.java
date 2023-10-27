package com.hackernews.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hackernews.entity.Content;

public interface ContentRepositroy extends JpaRepository<Content, Integer>{
	
	@Query("select c from Content c where c.catagory= 'show' or c.catagory='normal' or c.catagory = null")
	public List<Content> getAllShowAndNormal();
	
	@Query("select c from Content c where c.catagory= 'ask'")
	public List<Content> getAllAsk();
	@Query("select c from Content c where c.catagory= 'show'")
	public List<Content> getAllShow();
	@Query("select c from Content c where c.catagory= 'job'")
	public List<Content> getAllJob();
	
}
