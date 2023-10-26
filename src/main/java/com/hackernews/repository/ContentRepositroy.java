package com.hackernews.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackernews.entity.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ContentRepositroy extends JpaRepository<Content, Integer>{

    @Query(
            "SELECT c " +
                    "FROM Content c " +
                    "WHERE c.submitTime " +
                    "BETWEEN :startDateTime " +
                    "AND :endDateTime " +
                    "ORDER BY c.submitTime DESC"
    )
    List<Content> findAllBySubmitTimeBetween(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );
}
