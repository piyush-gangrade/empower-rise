package com.empower.controller.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    // Create or Update Blog
    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response> createBlog(@ModelAttribute @Valid CreateDonationDto data) {
        try {
            Donation donation = donationService.createOrUpdateDonation(data);
            return ResponseEntity.ok(new PositiveResponse("Blog Created Successfully", donation));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @PatchMapping(path = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response> updateBlog(@PathVariable Long id, @ModelAttribute @Valid UpdateDonationDto data) {
        try {
            Donation donation = donationService.updateDonation(id, data);
            return ResponseEntity.ok(new PositiveResponse("Blog updated successfully", donation));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    // Get Blog by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getBlogById(@PathVariable Long id) {
        try {
            Donation donation = donationService.getDonationById(id);
            return ResponseEntity.ok(new PositiveResponse("Blog found by id", donation));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    // Get All Blogs
    @GetMapping("/all")
    public ResponseEntity<Response> getAllBlogs(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            return ResponseEntity.status(200)
                    .body(new PositiveResponse("Blog list", donationService.getAllProject(page, limit)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }
    
    // Get All Blogs
    @GetMapping("/all-by-category/{categoryId}")
    public ResponseEntity<Response> getAllBlogsByCategory(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @PathVariable("categoryId") Long categoryId) {
        try {
            return ResponseEntity.status(200).body(new PositiveResponse( "Blog list", donationService.getDonationByCategory(categoryId, page, limit)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    // Delete Blog by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteBlog(@PathVariable Long id) {
        if (donationService.deleteDonation(id)) {
            return ResponseEntity.status(200).body(new PositiveResponse("Successfully deleted", null));
        }
        return ResponseEntity.status(400).body(new NegativeResponse("Resource not found"));
    }

}
