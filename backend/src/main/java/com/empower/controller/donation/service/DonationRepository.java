package com.empower.controller.donation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empower.controller.donation.Donation;
import com.empower.controller.fund.Fund;
import com.empower.controller.user.User;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    // Standard pagination for all donations
    Page<Donation> findAll(Pageable pageable);

    // Find all donations made by a specific user
    Page<Donation> findByUser(User user, Pageable pageable);

    // Find all donations made to a specific campaign (Fund)
    // THIS is the method your DonationService actually needs now!
    Page<Donation> findByFund(Fund fund, Pageable pageable);

}