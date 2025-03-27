package com.user_organization_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user_organization_management.dto.OrganizationDTO;
import com.user_organization_management.service.OrganizationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;

	@GetMapping
	public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() {
		return ResponseEntity.ok(organizationService.getAllOrganizations());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable Long id) {
		 return organizationService.getOrganizationById(id)
				 .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/add-org")
	public ResponseEntity<OrganizationDTO> createOrganization(@Valid @RequestBody OrganizationDTO organizationDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok(organizationService.createOrganization(organizationDTO));
	}
	
}
