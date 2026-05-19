package com.empower.controller.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.empower.controller.donation.dto.CreateDonationDto;
import com.empower.controller.donation.dto.UpdateDonationDto;
import com.empower.controller.donation.service.DonationService;
import com.empower.response.NegativeResponse;
import com.empower.response.PositiveResponse;
import com.empower.response.Response;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;

    // Create Donation (Accepts JSON now, no multipart bullshit)
    @PostMapping(path = "/create")
    public ResponseEntity<Response> createDonation(@RequestBody @Valid CreateDonationDto data) {
        try {
            Donation donation = donationService.createDonation(data);
            return ResponseEntity.ok(new PositiveResponse("Donation processed successfully", donation));
        } catch (Exception e) {
            e.printStackTrace(); // Keep this so you aren't debugging blind
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    // Get Donation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getDonationById(@PathVariable Long id) {
        try {
            Donation donation = donationService.getDonationById(id);
            return ResponseEntity.ok(new PositiveResponse("Donation found", donation));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    // Get All Donations (Paginated)
    @GetMapping("/all")
    public ResponseEntity<Response> getAllDonations(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            return ResponseEntity.status(200)
                    .body(new PositiveResponse("Donation list", donationService.getAllDonations(page, limit)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    // Get All Donations for a Specific Fund
    @GetMapping("/by-fund/{fundId}")
    public ResponseEntity<Response> getDonationsByFund(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @PathVariable("fundId") Long fundId) {
        try {
            return ResponseEntity.status(200).body(new PositiveResponse("Fund donations", donationService.getDonationsByFund(fundId, page, limit)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    // Delete Donation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteDonation(@PathVariable Long id) {
        if (donationService.deleteDonation(id)) {
            return ResponseEntity.status(200).body(new PositiveResponse("Successfully deleted donation", null));
        }
        return ResponseEntity.status(400).body(new NegativeResponse("Donation not found"));
    }
}