package com.empower.controller.donation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empower.controller.category.Category;
import com.empower.controller.donation.Donation;
import com.empower.controller.user.User;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    
    public Page<Donation> findAll(Pageable pageable);

    public Page<Donation> findByCategory(Category category, Pageable pageable);

    public Page<Donation> findByUser(User user, Pageable pageable);

    @Modifying
    @Query("UPDATE Donation d SET d.donatedPeople = d.donatedPeople + 1 WHERE d.id = :donationId")
    void incrementTotalVisits(@Param("donationId") Long donationId);

    @Modifying
    @Query("UPDATE Donation d SET d.collectedAmount = d.collectedAmount + :amount WHERE d.id = :donationId")
    void incrementTotalDonation(@Param("donationId") Long donationId, @Param("amount") int amount);

}
