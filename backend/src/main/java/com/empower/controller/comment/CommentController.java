package com.empower.controller.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empower.controller.comment.dto.CreateCommentDto;
import com.empower.controller.comment.service.CommentService;
import com.empower.response.NegativeResponse;
import com.empower.response.PositiveResponse;
import com.empower.response.Response;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    public CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<Response> createComment(CreateCommentDto data) {
        try {
            Comment comment = commentService.createComment(data, 1L);
            return ResponseEntity.ok(new PositiveResponse("Comment Created", comment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new NegativeResponse(e.getMessage()));
        }
    }
    
    // Get All Comments
    @GetMapping("/all")
    public ResponseEntity<Response> getAllComments(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            return ResponseEntity.status(200)
                    .body(new PositiveResponse("Comments get successfully", commentService.getAllComment(page, limit)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new NegativeResponse(e.getMessage()));
        }
    }

    // Get Comment by ID
    @GetMapping("/single/{commentId}")
    public ResponseEntity<Response> getCommentById(@PathVariable Long commentId) {
        try {
            return ResponseEntity
                    .ok(new PositiveResponse("Comment get successfully", commentService.findById(commentId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new NegativeResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/by-fund/{fundId}")
    public ResponseEntity<Response> getCommentsByFund(@PathVariable Long fundId,
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        try {
            return ResponseEntity.status(200)
                    .body(new PositiveResponse("Comments get successfully",
                            commentService.getAllCommentByFund(fundId, page, limit)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new NegativeResponse(e.getMessage()));
        }
    }
        
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<Response> getCommentsByUser(@RequestParam Long userId,
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        try {
            return ResponseEntity.status(200)
                    .body(new PositiveResponse("Comments get successfully",
                            commentService.getAllCommentByUser(userId, page, limit)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new NegativeResponse(e.getMessage()));
        }
    }

    @GetMapping("/by-donation/{donationId}")
    public ResponseEntity<Response> getCommentsByDonation(@RequestParam Long donationId,
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        try {
            return ResponseEntity.status(200)
                    .body(new PositiveResponse("Comments get successfully",
                            commentService.getAllCommentByDonation(donationId, page, limit)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new NegativeResponse(e.getMessage()));
        }
    }
    
    @GetMapping("by-user-and-donation/{userId}/{donationId}")
    public ResponseEntity<Response> getCommentsByUserAndDonation(@RequestParam Long userId, Long donationId,
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        try {
            return ResponseEntity.status(200)
                    .body(new PositiveResponse("Comments get successfully",
                            commentService.getAllCommentByUserIdAndDonationId(userId, donationId, page, limit)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new NegativeResponse(e.getMessage()));
        }
    }
    
    @GetMapping("by-user-and-fund/{userId}/{fundId}")
    public ResponseEntity<Response> getCommentsByUserAndFund(@RequestParam Long userId, Long fundId,
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        try {
            return ResponseEntity.status(200)
                    .body(new PositiveResponse("Comments get successfully",
                            commentService.getAllCommentByUserIdAndFundId(userId,fundId, page, limit)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new NegativeResponse(e.getMessage()));
        }
    }

    @PatchMapping("/update/{commentId}")
    public ResponseEntity<Response> updateComment(@PathVariable Long commentId, CreateCommentDto data) {
        try {
            Comment comment = commentService.updateComment(commentId, data);
            return ResponseEntity.ok(new PositiveResponse("Comment Updated", comment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new NegativeResponse(e.getMessage()));
        }
    }
    
    @PostMapping("/delete/{commentId}")
    public ResponseEntity<Response> deleteComment(@PathVariable Long commentId) {
      try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok(new PositiveResponse("Comment Deleted", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new NegativeResponse(e.getMessage()));
        }
    }

}
