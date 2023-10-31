package com.hackernews.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackernews.entity.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface ContentRepositroy extends JpaRepository<Content, Integer> {

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

    @Query("SELECT c FROM Content c WHERE c.title ilike %:searchText% ")
    List<Content> findByTitleContaining(String searchText);

    @Query("SELECT c FROM Content c WHERE c.url ilike %:searchText% ")
    List<Content> findByUrlContaining(String searchText);

    @Query("SELECT c FROM Content c WHERE c.text ilike %:searchText% ")
    List<Content> findByTextContaining(String searchText);

   // List<Content> findFilteredContent(String category, String sort, String timeframe);


    @Query("SELECT c FROM Content c " +
            "WHERE( c.title LIKE concat('%',:searchText,'%')"
            +"OR c.url LIKE concat('%',:searchText,'%')"
            + "OR c.text LIKE concat('%',:searchText,'%'))" +
            "AND (:catagory IS NULL OR c.catagory LIKE concat('%',:catagory,'%'))")
    Set<Content> findFilteredContent(@Param("catagory") String catagory, @Param("searchText") String searchText);
}


