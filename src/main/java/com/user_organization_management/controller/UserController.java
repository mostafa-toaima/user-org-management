package com.user_organization_management.controller;

import com.user_organization_management.model.CustomPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user_organization_management.dto.UserDTO;
import com.user_organization_management.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<CustomPageResponse<UserDTO>> getAllUsers(
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String mobile,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(userService.getUsersByFilterWithPagination(email, mobile, page, size));
	}

	@GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
	
	@PutMapping("/{userId}/assign/{orgId}")
    public ResponseEntity<UserDTO> assignUserToOrganization(@PathVariable Long userId, @PathVariable Long orgId) {
        return ResponseEntity.ok(userService.assignUserToOrganization(userId, orgId));
    }
	
	@PutMapping("/un-assign/{userId}")
	public ResponseEntity<UserDTO> unAssignUserFromOrganization(@PathVariable Long userId) {
		return ResponseEntity.ok(userService.unassignUserFromOrganization(userId));
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
