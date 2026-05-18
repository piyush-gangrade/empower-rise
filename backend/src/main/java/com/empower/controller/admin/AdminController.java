package com.empower.controller.admin;


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
import com.empower.controller.admin.dto.CreateAdminDto;
import com.empower.controller.admin.dto.LoginDto;
import com.empower.controller.admin.dto.UpdateAdminDto;
import com.empower.controller.admin.dto.UpdatePasswordDto;
import com.empower.controller.admin.service.AdminService;
import com.empower.response.NegativeResponse;
import com.empower.response.PositiveResponse;
import com.empower.response.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin Controller", description = "Admin API List")
public class AdminController {
    @Autowired
    private final AdminService adminService;

    @Operation(summary = "Create a new admin", description = "Add a new admin with given details")
    @PostMapping()
    public ResponseEntity<Response> createAdmin(@RequestBody CreateAdminDto data) {
        try {

            Admin getAdmin = adminService.createAdmin(data);
            return ResponseEntity.ok(new PositiveResponse("Admin Created", getAdmin));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All Admin with pagination", description="Get all admin with pagination , search query return data list, total documents and page number")
    @RolesAllowed("Admin")
    @GetMapping()
    public ResponseEntity<Response> getAllAdmin(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search
    ) {
        try {
            return ResponseEntity
                    .ok(new PositiveResponse("Admin list ", adminService.getAllAdmin(page, limit, search)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse("Something wrong"));
        }
    }

    @Operation(summary = "Admin Login Controller", description = "Admin Login in request, Request with email and password")
    @PostMapping("/login")
    public ResponseEntity<Response> adminLogin(@RequestBody LoginDto data) {
        try {
            HashMap<String, Object> response = adminService.adminLogin(data);
            return ResponseEntity.ok(new PositiveResponse("Login successfully ", response));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All Active admin ", description = "Get the active admin list, which status is active")
    @GetMapping("/active")
    public ResponseEntity<Response> getActiveAdminList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search
    ) {
        try {
            Map<String, Object> adminList = adminService.getAllActiveAdmin(page, limit, search, true);
            return ResponseEntity.ok(new PositiveResponse("Active admin list", adminList));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All Not Active admin ", description = "Get the non active admin list, which status is active")
    @GetMapping("/turnOff")
    public ResponseEntity<Response> getNotActiveAdminList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search
    ) {
        try {
            Map<String, Object> adminList = adminService.getAllActiveAdmin(page, limit, search, false);
            return ResponseEntity.ok(new PositiveResponse("Status false admin list", adminList));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }


    @Operation(summary = "Get Single Admin", description = "Get Single admin by id")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getSingleAdmin(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(new PositiveResponse("Admin found by id", adminService.getAdminById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary ="Update admin ", description="Admin update require admin id and updated fields, password cannot be updated with this request")
    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateAdmin(@PathVariable Long id, @RequestBody UpdateAdminDto data) {
        try {
            System.out.println("testing in controller");
            return ResponseEntity
                    .ok(new PositiveResponse("Admin data updated successfully", adminService.updateAdmin(id, data)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Update admin status ", description = "This request will be toggle the admin status field")
    @PatchMapping("/update-status/{id}")
    public ResponseEntity<Response> updateStatus(@PathVariable Long id) {
        try {
            adminService.updateStatus(id);
            return ResponseEntity.ok(new PositiveResponse("Admin status updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Admin Update Password", description = "Admin old and new password is required to update")
    @PatchMapping("/update-password/{id}")
    public ResponseEntity<Response> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordDto data) {
        try {
            adminService.updatePassword(id, data);
            return ResponseEntity.ok(new PositiveResponse("Password update successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Admin Delete by id")
    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteAdmin(@PathVariable Long id) {
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.ok(new PositiveResponse("Admin Deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

}
