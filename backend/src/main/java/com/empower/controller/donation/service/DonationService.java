package com.empower.controller.donation.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empower.controller.donation.Donation;
import com.empower.controller.donation.dto.CreateDonationDto;
import com.empower.controller.fund.Fund;
import com.empower.controller.fund.service.FundRepository; // You will need to import your actual FundRepository
import com.empower.controller.user.User;
import com.empower.controller.user.service.UserRepository; // And your UserRepository
import com.empower.enums.StatusEnum;
import com.empower.exceptions.ResourceNotFoundException;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private UserRepository userRepository;

    // Create Donation - Marked Transactional so if updating the Fund fails, the donation rolls back
    @Transactional
    public Donation createDonation(CreateDonationDto data) {

        // 1. Find the campaign they are trying to donate to
        Fund fund = fundRepository.findById(data.getFundId())
            .orElseThrow(() -> new ResourceNotFoundException("The specified Fund does not exist"));

        // 2. Build the new Donation record
        Donation donation = new Donation();
        donation.setAmount(data.getAmount());
        donation.setDonorName(data.getDonorName());
        donation.setMessage(data.getMessage());
        donation.setAnonymous(data.getAnonymous() != null ? data.getAnonymous() : false);
        donation.setFund(fund);
        donation.setStatus(StatusEnum.SUCCESSFUL); // Assuming immediate success for now

        // 3. Optional: Link the logged-in user if an ID was passed from the frontend
        if (data.getUserId() != null) {
            User user = userRepository.findById(data.getUserId()).orElse(null);
            donation.setUser(user);
        }

        // 4. Update the parent Fund's statistics
        // (Assuming collectedAmount is an int or double in your Fund entity. Adjust type casting if necessary)
        int currentAmount = fund.getCollectedAmount();
fund.setCollectedAmount((int) (currentAmount + data.getAmount()));

        int currentDonors = fund.getDonatedPeople();
        fund.setDonatedPeople(currentDonors + 1);

        // 5. Save everything
        fundRepository.save(fund);
        return donationRepository.save(donation);
    }

    // Get Donation by ID
    public Donation getDonationById(Long id) {
        return donationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Donation not found"));
    }

    // Get All Donations (Global Admin view)
    public Map<String, Object> getAllDonations(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Donation> donationPage = donationRepository.findAll(pageable);

        Map<String, Object> result = new HashMap<>();
        result.put("totalDoc", donationPage.getTotalElements());
        result.put("totalPage", donationPage.getTotalPages());
        result.put("data", donationPage.getContent());

        return result;
    }

    // Get Donations for a specific Fund (To show on the Fund Details page)
    public Map<String, Object> getDonationsByFund(Long fundId, int page, int limit) {
        Fund fund = fundRepository.findById(fundId)
            .orElseThrow(() -> new ResourceNotFoundException("Fund not found"));

        Pageable pageable = PageRequest.of(page - 1, limit);

        // Note: You will need to add this method to your DonationRepository:
        // Page<Donation> findByFund(Fund fund, Pageable pageable);
        Page<Donation> donationPage = donationRepository.findByFund(fund, pageable);

        Map<String, Object> result = new HashMap<>();
        result.put("totalDoc", donationPage.getTotalElements());
        result.put("totalPage", donationPage.getTotalPages());
        result.put("data", donationPage.getContent());

        return result;
    }

    // Delete Donation Service
    @Transactional
    public boolean deleteDonation(Long id) {
        Donation donation = donationRepository.findById(id).orElse(null);
        if (donation != null) {
            // Optional: If you delete a donation, you usually want to subtract that amount from the Fund
            Fund fund = donation.getFund();
            if (fund != null && donation.getStatus() == StatusEnum.SUCCESSFUL) {
                fund.setCollectedAmount((int) (fund.getCollectedAmount() - donation.getAmount()));
                fund.setDonatedPeople(Math.max(0, fund.getDonatedPeople() - 1));
                fundRepository.save(fund);
            }
            donationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}