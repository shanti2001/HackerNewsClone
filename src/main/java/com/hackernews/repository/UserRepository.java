package com.hackernews.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackernews.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{

}
