package com.empower.controller.user.service;


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
import com.empower.controller.user.User;
import com.empower.controller.user.dto.CreateUsersDto;
import com.empower.controller.user.dto.LoginUsersDto;
import com.empower.controller.user.dto.UpdateUsersDto;
import com.empower.controller.user.dto.UpdatePasswordDto;
import com.empower.exceptions.AlreadyExistsException;
import com.empower.exceptions.ResourceNotFoundException;

import io.jsonwebtoken.Claims;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository UserRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private final JWTService jwtService;

    public User createUser(CreateUsersDto data) {
        if (UserRepository.findByEmailOrPhoneNumber(data.getEmail(), data.getPhoneNumber()) != null) {
            throw new AlreadyExistsException(
                    "User already exists with email or PhoneNumber " + data.getEmail() + " " + data.getPhoneNumber());
        }

        User newUser = new User();
        newUser.setUsername(data.getName());
        newUser.setEmail(data.getEmail());
        newUser.setPhoneNumber(data.getPhoneNumber());
        if (data.getPassword() != null) {
            String hashedPassword = passwordEncoder.encode(data.getPassword());
            newUser.setPassword(hashedPassword);
        }

        if (data.getAddress() != null) {
            newUser.setAddress(data.getAddress());
        }

        if (data.getPhotoURL() != null) {
            newUser.setPhotoURL(data.getPhotoURL());
        }
        UserRepository.save(newUser);
        return newUser;
    }

    public HashMap<String, Object> UserLogin(LoginUsersDto data) {
        User getUser = UserRepository.findByEmail(data.getEmail());
        if (getUser == null) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        if (!passwordEncoder.matches(data.getPassword(), getUser.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        getUser.setPassword(null);

        String token = jwtService.generateToken(getUser.getId(), "User", 100000);

        Claims tokenData = jwtService.extractAllClaims(token);
        HashMap<String, Object> returnData = new HashMap<String, Object>();

        returnData.put("token", token);
        returnData.put("user", getUser);

        return returnData;
    }

    public Map<String, Object> getAllUser(int page, int limit, String search) {
        Pageable pageable = PageRequest.of(page-1,limit);

        Page<User> UserPage = (search != null) ?
                UserRepository.findByNameOrPhoneNumberWithRegex(search, pageable) :
                UserRepository.findAll(pageable);

        List<User> list = UserPage.getContent();
        System.out.println("User list " + list);

        long totalDocs = UserPage.getTotalElements();
        int totalPages = UserPage.getTotalPages();

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("totalDocs", totalDocs);
        data.put("totalPages", totalPages);

        return data;
    }

    public User getUserById(Long id) {
        return UserRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found By id"));
    }

    public User updateUser(Long id, UpdateUsersDto data) {
        User getUser = UserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by id"));
        System.out.println(data.getName());
        if (data.getEmail() != null) {
            getUser.setEmail(data.getEmail());
        }

        if (data.getName() != null) {
            getUser.setUsername(data.getName());
        }

        if (data.getPhoneNumber() != null) {
            getUser.setPhoneNumber(data.getPhoneNumber());
        }

        if (data.getAddress() != null) {
            getUser.setAddress(data.getAddress());
        }

        // if (data.getPhotoURL() != null) {
        //     // User.setPhotoURL(data.getPhotoURL());
        // }

        UserRepository.save(getUser);
        return getUser;
    }


    public void updatePassword(Long id, UpdatePasswordDto data) {
        User getUser = UserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by id"));
        boolean isPasswordMatch = passwordEncoder.matches(data.getOldPassword(), getUser.getPassword());

        if (!isPasswordMatch) {
            throw new ValidationException("Old password did not match");
        }

        String hashPassword = passwordEncoder.encode(data.getNewPassword());

        getUser.setPassword(hashPassword);

        UserRepository.save(getUser);
    }

    public void updateStatus(Long id) {
        User getUser = UserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by id"));
        if(getUser.isActive() == true) {
            getUser.setActive(false);
        } else {
            getUser.setActive(true);
        }

        UserRepository.save(getUser);
    }

    public void deleteUser(Long id) {
        User User = UserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UserRepository.delete(User);
    }

}

