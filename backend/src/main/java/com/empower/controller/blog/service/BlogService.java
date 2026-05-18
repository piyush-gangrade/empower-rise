package com.empower.controller.blog.service;

import com.empower.controller.blog.Blog;
import com.empower.controller.blog.dto.CreateBlogDto;
import com.empower.controller.blog.dto.UpdateBlogDto;
import com.empower.controller.category.Category;
import com.empower.controller.category.service.CategoryRepository;
import com.empower.exceptions.ResourceNotFoundException;
import com.empower.exceptions.ValidationException;
import com.empower.utils.cloudinary.UploadImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UploadImage uploadImage;

    // Create or Update Blog
    public Blog createOrUpdateBlog(CreateBlogDto data) {
        try {
            MultipartFile images[] = data.getImages();

            Category category = categoryRepository.findById(data.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found by id"));

            Blog blog = new Blog();
            blog.setName(data.getName());
            blog.setCategory(category);
            blog.setDescription(data.getDescription());
            blog.setTitle(data.getTitle());

            if (images != null && images.length > 0) {
                String[] uploadedImageUrls = new String[images.length];
                for (int i = 0; i < images.length; i++) {
                    File convFile = convertMultiPartToFile(images[i]);
                    Map<String, Object> uploadResult = uploadImage.uploadImage(convFile.getAbsolutePath());
                    uploadedImageUrls[i] = (String) uploadResult.get("url");
                    convFile.delete();
                }
                blog.setImages(uploadedImageUrls);
            }

            return blogRepository.save(blog);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get Blog by ID
    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found by id"));
    }

    // Get All Blogs
    public Map<String, Object> getAllProject(int page, int limit){
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<Blog> projectList = blogRepository.findAll(pageable);

        Map<String, Object> list =  new HashMap<>();
        list.put("totalDoc",projectList.getTotalElements());
        list.put("totalPage", projectList.getTotalPages());
        list.put("data", projectList.getContent());

        return list;
    }

    // Get Blog by Category
    public Map<String, Object> getBlogByCategory(Long categoryId, int page, int limit) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found by id"));

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Blog> projectList = blogRepository.findByCategory(pageable, category);

        Map<String, Object> list = new HashMap<>();
        list.put("totalDoc", projectList.getTotalElements());
        list.put("totalPage", projectList.getTotalPages());
        list.put("data", projectList.getContent());

        return list;
    }

    // Update Blog Service
    public Blog updateBlog(Long id, UpdateBlogDto data) {
        try {
            Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found by id"));

        if (data.getTitle() != null) {
            blog.setTitle(data.getTitle());
        }

        if (data.getDescription() != null) {
            blog.setDescription(data.getDescription());
        }

        if (data.getName() != null) {
            blog.setName(data.getName());
        }

        if (data.getImages() != null && data.getImages().length > 0) {
            String[] uploadedImageUrls = new String[data.getImages().length];
            for (int i = 0; i < data.getImages().length; i++) {
                File convFile = convertMultiPartToFile(data.getImages()[i]);
                Map<String, Object> uploadResult = uploadImage.uploadImage(convFile.getAbsolutePath());
                uploadedImageUrls[i] = (String) uploadResult.get("url");
                convFile.delete();
            }
            blog.setImages(uploadedImageUrls);
        }
        
        blogRepository.save(blog);
        return blog;
        
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }

    }

    // Delete Blog by ID
    public boolean deleteBlog(Long id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
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

