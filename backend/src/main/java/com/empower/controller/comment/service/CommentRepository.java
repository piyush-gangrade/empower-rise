package com.empower.controller.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.empower.controller.comment.Comment;
import com.empower.controller.donation.Donation;
import com.empower.controller.fund.Fund;
import com.empower.controller.user.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public Page<Comment> findAll(Pageable pageable);
    public Page<Comment> findByDonation(Donation donation, Pageable pageable);
    public Page<Comment> findByFund(Fund fund, Pageable pageable);
    public Page<Comment> findByUser(User user, Pageable pageable);
    public Page<Comment> findByUserAndDonation(User user, Donation donation, Pageable page);
    public Page<Comment> findByUserAndFund(User user, Fund fund, Pageable pageable);
}
