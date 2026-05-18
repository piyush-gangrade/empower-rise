package com.empower.controller.user;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empower.annotation.RolesAllowed;
import com.empower.controller.user.dto.CreateUsersDto;
import com.empower.controller.user.dto.LoginUsersDto;
import com.empower.controller.user.dto.UpdateUsersDto;
import com.empower.controller.user.dto.UpdatePasswordDto;
import com.empower.controller.user.service.UserService;
import com.empower.response.NegativeResponse;
import com.empower.response.PositiveResponse;
import com.empower.response.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/User")
@Tag(name = "User Controller", description = "User API List")
public class UserController {
    @Autowired
    private final UserService UserService;

    @Operation(summary = "Create a new User", description = "Add a new User with given details")
    @PostMapping()
    public ResponseEntity<Response> createUser(@RequestBody CreateUsersDto data) {
        try {

            User getUser = UserService.createUser(data);
            return ResponseEntity.ok(new PositiveResponse("User Created", getUser));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All User with pagination", description="Get all User with pagination , search query return data list, total documents and page number")
    @RolesAllowed("User")
    @GetMapping()
    public ResponseEntity<Response> getAllUser(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search
    ) {
        try {
            return ResponseEntity
                    .ok(new PositiveResponse("User list ", UserService.getAllUser(page, limit, search)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse("Something wrong"));
        }
    }

    @Operation(summary = "User Login Controller", description = "User Login in request, Request with email and password")
    @PostMapping("/login")
    public ResponseEntity<Response> UserLogin(@RequestBody LoginUsersDto data) {
        try {
            HashMap<String, Object> response = UserService.UserLogin(data);
            return ResponseEntity.ok(new PositiveResponse("Login successfully ", response));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get Single User", description = "Get Single User by id")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getSingleUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(new PositiveResponse("User found by id", UserService.getUserById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary ="Update User ", description="User update require User id and updated fields, password cannot be updated with this request")
    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id, @RequestBody UpdateUsersDto data) {
        try {
            System.out.println("testing in controller");
            return ResponseEntity
                    .ok(new PositiveResponse("User data updated successfully", UserService.updateUser(id, data)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Update User status ", description = "This request will be toggle the User status field")
    @PatchMapping("/update-status/{id}")
    public ResponseEntity<Response> updateStatus(@PathVariable Long id) {
        try {
            UserService.updateStatus(id);
            return ResponseEntity.ok(new PositiveResponse("User status updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "User Update Password", description = "User old and new password is required to update")
    @PatchMapping("/update-password/{id}")
    public ResponseEntity<Response> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordDto data) {
        try {
            UserService.updatePassword(id, data);
            return ResponseEntity.ok(new PositiveResponse("Password update successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "User Delete by id")
    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id) {
        try {
            UserService.deleteUser(id);
            return ResponseEntity.ok(new PositiveResponse("User Deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

}
