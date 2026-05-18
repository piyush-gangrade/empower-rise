package com.empower.controller.fund.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empower.controller.category.Category;
import com.empower.controller.fund.Fund;
import com.empower.controller.user.User;

public interface FundRepository extends JpaRepository<Fund, Long> {
    
    public Page<Fund> findAll(Pageable pageable);

    public Page<Fund> findByCategory(Category category, Pageable pageable);

    public Page<Fund> findByUser(User user, Pageable pageable);

    @Modifying
    @Query("UPDATE Fund f SET f.totalPeople = f.totalPeople + 1 WHERE f.id = :fundId")
    void incrementTotalVisits(@Param("fundId") Long fundId);

    @Modifying
    @Query("UPDATE Fund f SET f.collectedAmount = f.collectedAmount + :amount WHERE f.id = :fundId")
    void incrementTotalDonation(@Param("fundId") Long fundId, @Param("amount") int amount);

    @Modifying
    @Query("UPDATE Fund f SET f.donatedPeople = f.donatedPeople + 1 WHERE f.id = :fundId")
    void incrementTotalPeople(@Param("fundId") Long fundId);

}
