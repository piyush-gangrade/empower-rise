package com.empower.controller.fund.service;

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

import com.empower.controller.category.Category;
import com.empower.controller.category.service.CategoryRepository;
import com.empower.controller.fund.Fund;
import com.empower.controller.fund.dto.CreateFundDto;
import com.empower.controller.fund.dto.UpdateFundDto;
import com.empower.exceptions.ResourceNotFoundException;
import com.empower.exceptions.ValidationException;
import com.empower.utils.cloudinary.UploadImage;

@Service
public class FundService {
    @Autowired
    private FundRepository fundRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UploadImage uploadImage;

    // Create or Update Fund
    public Fund createFund(CreateFundDto data) {
        try {
            MultipartFile images[] = data.getImages();

            Category category = categoryRepository.findById(data.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found by id"));

            Fund Fund = new Fund();

            Fund.setTitle(data.getTitle());
            Fund.setAmount(data.getAmount());
            Fund.setCategory(category);
            Fund.setDescription(data.getDescription());
            Fund.setDayLeft(data.getDayLeft());
            

            if (images != null && images.length > 0) {
                String[] uploadedImageUrls = new String[images.length];
                for (int i = 0; i < images.length; i++) {
                    File convFile = convertMultiPartToFile(images[i]);
                    Map<String, Object> uploadResult = uploadImage.uploadImage(convFile.getAbsolutePath());
                    uploadedImageUrls[i] = (String) uploadResult.get("url");
                    convFile.delete();
                }
                Fund.setImages(uploadedImageUrls);
            }

            return fundRepository.save(Fund);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get Fund by ID
    public Fund getFundById(Long id) {
        return fundRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found by id"));
    }

    // Get All Funds
    public Map<String, Object> getAllProject(int page, int limit){
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<Fund> projectList = fundRepository.findAll(pageable);

        Map<String, Object> list =  new HashMap<>();
        list.put("totalDoc",projectList.getTotalElements());
        list.put("totalPage", projectList.getTotalPages());
        list.put("data", projectList.getContent());

        return list;
    }

    // Get Fund by Category
    public Map<String, Object> getFundByCategory(Long categoryId, int page, int limit) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found by id"));

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Fund> projectList = fundRepository.findByCategory(category, pageable);

        Map<String, Object> list = new HashMap<>();
        list.put("totalDoc", projectList.getTotalElements());
        list.put("totalPage", projectList.getTotalPages());
        list.put("data", projectList.getContent());

        return list;
    }

    // Update Fund Service
    public Fund updateFund(Long id, UpdateFundDto data) {
        try {
            Fund Fund = fundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fund not found by id"));

        if (data.getTitle() != null) {
            Fund.setTitle(data.getTitle());
        }

        if (data.getDescription() != null) {
            Fund.setDescription(data.getDescription());
        }

        if (data.getImages() != null && data.getImages().length > 0) {
            String[] uploadedImageUrls = new String[data.getImages().length];
            for (int i = 0; i < data.getImages().length; i++) {
                File convFile = convertMultiPartToFile(data.getImages()[i]);
                Map<String, Object> uploadResult = uploadImage.uploadImage(convFile.getAbsolutePath());
                uploadedImageUrls[i] = (String) uploadResult.get("url");
                convFile.delete();
            }
            Fund.setImages(uploadedImageUrls);
        }
        
        fundRepository.save(Fund);
        return Fund;
        
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }

    }

    // Delete Fund by ID
    public boolean deleteFund(Long id) {
        if (fundRepository.existsById(id)) {
            fundRepository.deleteById(id);
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
