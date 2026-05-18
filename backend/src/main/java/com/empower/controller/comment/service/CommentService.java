package com.empower.controller.comment.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.empower.controller.comment.Comment;
import com.empower.controller.comment.dto.CreateCommentDto;
import com.empower.controller.donation.Donation;
import com.empower.controller.donation.service.DonationRepository;
import com.empower.controller.fund.Fund;
import com.empower.controller.fund.service.FundRepository;
import com.empower.controller.user.User;
import com.empower.controller.user.service.UserRepository;
import com.empower.exceptions.ResourceNotFoundException;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment createComment(CreateCommentDto data, Long userId) {
        Comment comment = new Comment();

        if (data.getText() != null) {
            comment.setText(data.getText());
        }

        if (data.getDonationId() != null) {
            Donation donation = donationRepository.findById(data.getDonationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Donation not found by id"));
            comment.setDonation(donation);
        }

        if (data.getFundId() != null) {
            Fund fund = fundRepository.findById(data.getFundId())
                    .orElseThrow(() -> new ResourceNotFoundException("Fund not found by id"));
            comment.setFund(fund);
        }

        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            comment.setUser(user);
        }

        return commentRepository.save(comment);

    }

    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found by id"));
    }
    
    public void deleteComment(Long id) {
        Comment comment = findById(id);
        commentRepository.delete(comment);
    }

    public Map<String, Object> getAllComment(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Comment> commentList = commentRepository.findAll(pageable);
        Map<String, Object> list = new HashMap<>();
        list.put("comments", commentList.getContent());
        list.put("totalDocs", commentList.getTotalElements());
        list.put("totalPages", commentList.getTotalPages());
        return list;
    }

    public Map<String, Object> getAllCommentByDonation(Long donationId, int page, int limit) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found by id"));
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Comment> commentList = commentRepository.findByDonation(donation, pageable);
        Map<String, Object> list = new HashMap<>();
        list.put("comments", commentList.getContent());
        list.put("totalDocs", commentList.getTotalElements());
        list.put("totalPages", commentList.getTotalPages());
        return list;
    }
    
    public Map<String, Object> getAllCommentByFund(Long fundId, int page, int limit) {
        Fund fund = fundRepository.findById(fundId)
                .orElseThrow(() -> new ResourceNotFoundException("Fund not found by id"));
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Comment> commentList = commentRepository.findByFund(fund, pageable);
        Map<String, Object> list = new HashMap<>();
        list.put("comments", commentList.getContent());
        list.put("totalDocs", commentList.getTotalElements());
        list.put("totalPages", commentList.getTotalPages());
        return list;
    }
    
    public Map<String, Object> getAllCommentByUser(Long userId, int page, int limit) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by id"));
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Comment> commentList = commentRepository.findByUser(user, pageable);
        Map<String, Object> list = new HashMap<>();
        list.put("comments", commentList.getContent());
        list.put("totalDocs", commentList.getTotalElements());
        list.put("totalPages", commentList.getTotalPages());
        return list;
    }
    
    public Map<String, Object> getAllCommentByUserIdAndDonationId(Long userId, Long donationId, int page, int limit) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by id"));
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found by id"));
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Comment> commentList = commentRepository.findByUserAndDonation(user, donation, pageable);
        Map<String, Object> list = new HashMap<>();
        list.put("comments", commentList.getContent());
        list.put("totalDocs", commentList.getTotalElements());
        list.put("totalPages", commentList.getTotalPages());
        return list;
    }
    
    public Map<String, Object> getAllCommentByUserIdAndFundId(Long userId, Long fundId, int page, int limit) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by id"));
        Fund fund = fundRepository.findById(fundId)
                .orElseThrow(() -> new ResourceNotFoundException("Fund not found by id"));
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Comment> commentList = commentRepository.findByUserAndFund(user, fund, pageable);
        Map<String, Object> list = new HashMap<>();
        list.put("comments", commentList.getContent());
        list.put("totalDocs", commentList.getTotalElements());
        list.put("totalPages", commentList.getTotalPages());
        return list;
    }

    public Comment updateComment(Long commentId, CreateCommentDto data) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found by id"));
        if (data.getText() != null) {
            comment.setText(data.getText());
        }
        return commentRepository.save(comment);
    }

}
