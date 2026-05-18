package com.empower.controller.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empower.controller.admin.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
    public Admin findByEmailOrPhoneNumber(String email, String phoneNumber);

    public Admin findByEmail(String email);

    @Query("SELECT a FROM Admin a WHERE a.username LIKE CONCAT('%',:prefix, '%') OR a.phoneNumber LIKE CONCAT('%',:prefix, '%')")
    Page<Admin> findByNameOrPhoneNumberWithRegex(@Param("prefix") String prefix, Pageable pageable);

    @Query("SELECT a FROM Admin a WHERE a.active = :active AND (a.username LIKE CONCAT('%', :prefix, '%') OR a.phoneNumber LIKE CONCAT('%', :prefix, '%'))")
    Page<Admin> findByNameOrPhoneNumberOrActiveWithRegex(@Param("prefix") String prefix, Pageable pageable, @Param("active") boolean active);

    public Page<Admin> findByActive(boolean active, Pageable pageable);

}