package com.empower.controller.donation.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.empower.controller.donation.Donation;
import com.empower.controller.donation.dto.CreateDonationDto;
import com.empower.controller.donation.dto.UpdateDonationDto;
import com.empower.controller.category.Category;
import com.empower.controller.category.service.CategoryRepository;
import com.empower.exceptions.ResourceNotFoundException;
import com.empower.exceptions.ValidationException;
import com.empower.utils.cloudinary.UploadImage;

@Service
public class DonationService {
    @Autowired
    private DonationRepository donationRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UploadImage uploadImage;

    // Create or Update Donation
    public Donation createOrUpdateDonation(CreateDonationDto data) {
        try {
            MultipartFile images[] = data.getImages();

            Category category = categoryRepository.findById(data.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found by id"));

            Donation donation = new Donation();

            donation.setTitle(data.getTitle());
            donation.setAmount(data.getAmount());
            donation.setCategory(category);
            donation.setDescription(data.getDescription());
            donation.setDayLeft(data.getDayLeft());
            

            if (images != null && images.length > 0) {
                String[] uploadedImageUrls = new String[images.length];
                for (int i = 0; i < images.length; i++) {
                    File convFile = convertMultiPartToFile(images[i]);
                    Map<String, Object> uploadResult = uploadImage.uploadImage(convFile.getAbsolutePath());
                    uploadedImageUrls[i] = (String) uploadResult.get("url");
                    convFile.delete();
                }
                donation.setImages(uploadedImageUrls);
            }

            return donationRepository.save(donation);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get Donation by ID
    public Donation getDonationById(Long id) {
        return donationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found by id"));
    }

    // Get All Donations
    public Map<String, Object> getAllProject(int page, int limit){
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<Donation> projectList = donationRepository.findAll(pageable);

        Map<String, Object> list =  new HashMap<>();
        list.put("totalDoc",projectList.getTotalElements());
        list.put("totalPage", projectList.getTotalPages());
        list.put("data", projectList.getContent());

        return list;
    }

    // Get Donation by Category
    public Map<String, Object> getDonationByCategory(Long categoryId, int page, int limit) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found by id"));

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Donation> projectList = donationRepository.findByCategory(category, pageable);

        Map<String, Object> list = new HashMap<>();
        list.put("totalDoc", projectList.getTotalElements());
        list.put("totalPage", projectList.getTotalPages());
        list.put("data", projectList.getContent());

        return list;
    }

    // Update Donation Service
    public Donation updateDonation(Long id, UpdateDonationDto data) {
        try {
            Donation Donation = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found by id"));

        if (data.getTitle() != null) {
            Donation.setTitle(data.getTitle());
        }

        if (data.getDescription() != null) {
            Donation.setDescription(data.getDescription());
        }

        if (data.getImages() != null && data.getImages().length > 0) {
            String[] uploadedImageUrls = new String[data.getImages().length];
            for (int i = 0; i < data.getImages().length; i++) {
                File convFile = convertMultiPartToFile(data.getImages()[i]);
                Map<String, Object> uploadResult = uploadImage.uploadImage(convFile.getAbsolutePath());
                uploadedImageUrls[i] = (String) uploadResult.get("url");
                convFile.delete();
            }
            Donation.setImages(uploadedImageUrls);
        }
        
        donationRepository.save(Donation);
        return Donation;
        
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }

    }

    // Delete Donation by ID
    public boolean deleteDonation(Long id) {
        if (donationRepository.existsById(id)) {
            donationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Convert MultipartFile to File
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
