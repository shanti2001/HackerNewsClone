package com.hackernews.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackernews.entity.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ContentRepositroy extends JpaRepository<Content, Integer>{

    @Query(
            "SELECT c " + "FROM Content c " +
                    "WHERE c.submitTime " +
                    "BETWEEN :startDateTime " + "AND :endDateTime " +
                    "ORDER BY c.submitTime DESC"
    )
    List<Content> findAllBySubmitTimeBetween(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );

//    @Query(
//            "SELECT c " + "FROM Content c " +
//                    "WHERE c.title " +
//                    "ilike %:keyword% " +
//                    "OR c.url ilike %:keyword% " +
//                    "OR c.text ilike %:keyword%")
//    List<Content> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT c FROM Content c WHERE c.title ilike %:searchText% ")
    List<Content> findByTitleContaining(String searchText);

    @Query("SELECT c FROM Content c WHERE c.url ilike %:searchText% ")
    List<Content> findByUrlContaining(String searchText);

    @Query("SELECT c FROM Content c WHERE c.text ilike %:searchText% ")
    List<Content> findByTextContaining(String searchText);
}
