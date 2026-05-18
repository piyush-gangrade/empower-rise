package com.empower.controller.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empower.controller.user.User;

public interface UserRepository extends JpaRepository<User, Long>{
    public User findByEmailOrPhoneNumber(String email, String phoneNumber);

    public User findByEmail(String email);

    public Page<User> findByRole(String role, Pageable pageable);

    @Query("SELECT a FROM Admin a WHERE a.username LIKE CONCAT('%',:prefix, '%') OR a.phoneNumber LIKE CONCAT('%',:prefix, '%')")
    Page<User> findByNameOrPhoneNumberWithRegex(@Param("prefix") String prefix, Pageable pageable);

    public Page<User> findByActive(boolean active, Pageable pageable);

}