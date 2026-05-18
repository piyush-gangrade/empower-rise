package com.empower.controller.blog.service;

import com.empower.controller.blog.Blog;
import com.empower.controller.category.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    public Page<Blog> findAll(Pageable pageable);

    public Page<Blog> findByCategory(Pageable pageable, Category category);

    @Modifying
    @Query("UPDATE Blog b SET b.totalVisits = b.totalVisits + 1 WHERE b.id = :blogId")
    void incrementTotalVisits(@Param("blogId") Long blogId);

    @Modifying
    @Query("UPDATE Blog b SET b.totalPeople = b.totalPeople + 1 WHERE b.id = :blogId")
    void incrementTotalPeople(@Param("blogId") Long blogId);

    @Modifying
    @Query("UPDATE Blog b SET b.totalPeople = b.totalPeople + 1, b.totalDonation = b.totalDonation + :amount WHERE b.id = :blogId")
    void incrementTotalDonation(@Param("blogId") Long blogId, @Param("amount") int amount);



}
