package com.empower.controller.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.empower.controller.blog.dto.CreateBlogDto;
import com.empower.controller.blog.dto.UpdateBlogDto;
import com.empower.controller.blog.service.BlogService;
import com.empower.response.NegativeResponse;
import com.empower.response.PositiveResponse;
import com.empower.response.Response;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    // Create or Update Blog
    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response> createBlog(@ModelAttribute @Valid CreateBlogDto data) {
        try {
            Blog blog = blogService.createOrUpdateBlog(data);
            return ResponseEntity.ok(new PositiveResponse("Blog Created Successfully", blog));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @PatchMapping(path = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response> updateBlog(@PathVariable Long id, @ModelAttribute @Valid UpdateBlogDto data) {
        try {
            Blog blog = blogService.updateBlog(id, data);
            return ResponseEntity.ok(new PositiveResponse("Blog updated successfully", blog));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    // Get Blog by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getBlogById(@PathVariable Long id) {
        try {
            Blog blog = blogService.getBlogById(id);
            return ResponseEntity.ok(new PositiveResponse("Blog found by id", blog));
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
                    .body(new PositiveResponse("Blog list", blogService.getAllProject(page, limit)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }
    
    // Get All Blogs
    @GetMapping("/all-by-category/{categoryId}")
    public ResponseEntity<Response> getAllBlogsByCategory(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @PathVariable("categoryId") Long categoryId) {
        try {
            return ResponseEntity.status(200).body(new PositiveResponse( "Blog list", blogService.getBlogByCategory(categoryId, page, limit)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    // Delete Blog by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBlog(@PathVariable Long id) {
        if (blogService.deleteBlog(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

