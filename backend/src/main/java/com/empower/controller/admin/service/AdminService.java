package com.empower.controller.admin.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.empower.config.JWTService;
// import com.Token.config.JWTConfig;
import com.empower.controller.admin.Admin;
import com.empower.controller.admin.dto.CreateAdminDto;
import com.empower.controller.admin.dto.LoginDto;
import com.empower.controller.admin.dto.UpdateAdminDto;
import com.empower.controller.admin.dto.UpdatePasswordDto;
import com.empower.exceptions.AlreadyExistsException;
import com.empower.exceptions.ResourceNotFoundException;

import io.jsonwebtoken.Claims;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    private final AdminRepository adminRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private final JWTService jwtService;

    public Admin createAdmin(CreateAdminDto data) {
        if (adminRepository.findByEmailOrPhoneNumber(data.getEmail(), data.getPhoneNumber()) != null) {
            throw new AlreadyExistsException(
                    "Admin already exists with email or PhoneNumber " + data.getEmail() + " " + data.getPhoneNumber());
        }

        // System.out.println("password " + data.getPassword());
        // System.out.println("Email  " + data.getEmail());


        String hashedPassword = passwordEncoder.encode(data.getPassword());

        Admin newAdmin = new Admin();
        newAdmin.setUsername(data.getUsername());
        newAdmin.setEmail(data.getEmail());
        newAdmin.setPhoneNumber(data.getPhoneNumber());
        newAdmin.setPassword(hashedPassword);

        adminRepository.save(newAdmin);

        return newAdmin;
    }

    public HashMap<String, Object> adminLogin(LoginDto data) {
        Admin getAdmin = adminRepository.findByEmail(data.getEmail());
        if (getAdmin == null) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        if (!passwordEncoder.matches(data.getPassword(), getAdmin.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        getAdmin.setPassword(null);

        String token = jwtService.generateToken(getAdmin.getId(), "Admin", 100000);

        Claims tokenData = jwtService.extractAllClaims(token);
        HashMap<String, Object> returnData = new HashMap<String, Object>();

        returnData.put("token", token);
        returnData.put("user", getAdmin);

        return returnData;
    }

    public Map<String, Object> getAllAdmin(int page, int limit, String search) {
        Pageable pageable = PageRequest.of(page-1,limit);

        Page<Admin> adminPage = (search != null) ?
                adminRepository.findByNameOrPhoneNumberWithRegex(search, pageable) :
                adminRepository.findAll(pageable);

        List<Admin> list = adminPage.getContent();
        System.out.println("admin list " + list);

        long totalDocs = adminPage.getTotalElements();
        int totalPages = adminPage.getTotalPages();

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("totalDocs", totalDocs);
        data.put("totalPages", totalPages);

        return data;
    }

    public Map<String, Object> getAllActiveAdmin(int page, int limit, String search, boolean active) {
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<Admin> adminPage;

        if (search != null) {
            adminPage = adminRepository.findByNameOrPhoneNumberOrActiveWithRegex(search, pageable, active);
        } else {
            adminPage = adminRepository.findByActive(active, pageable);
        }

        List<Admin> list = adminPage.getContent();
        long totalDocs = adminPage.getTotalElements();
        int totalPages = adminPage.getTotalPages();

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("totalDocs", totalDocs);
        data.put("totalPages", totalPages);
        return data;
    }

    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Admin Not Found By id"));
    }

    public Admin updateAdmin(Long id, UpdateAdminDto data) {
        Admin getAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found by id"));
        System.out.println(data.getName());
        if (data.getEmail() != null) {
            getAdmin.setEmail(data.getEmail());
        }

        if (data.getName() != null) {
            getAdmin.setUsername(data.getName());
        }

        if (data.getPhoneNumber() != null) {
            getAdmin.setPhoneNumber(data.getPhoneNumber());
        }

        adminRepository.save(getAdmin);
        return getAdmin;
    }


    public void updatePassword(Long id, UpdatePasswordDto data) {
        Admin getAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found by id"));
        boolean isPasswordMatch = passwordEncoder.matches(data.getOldPassword(), getAdmin.getPassword());

        if (!isPasswordMatch) {
            throw new ValidationException("Old password did not match");
        }

        String hashPassword = passwordEncoder.encode(data.getNewPassword());

        getAdmin.setPassword(hashPassword);

        adminRepository.save(getAdmin);
    }

    public void updateStatus(Long id) {
        Admin getAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found by id"));
        if(getAdmin.isActive() == true) {
            getAdmin.setActive(false);
        } else {
            getAdmin.setActive(true);
        }

        adminRepository.save(getAdmin);
    }

    public void deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        adminRepository.delete(admin);
    }

}

