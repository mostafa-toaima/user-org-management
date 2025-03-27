package com.user_organization_management.service;

import java.util.List;
import java.util.Optional;

import com.user_organization_management.entity.OrganizationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user_organization_management.dto.OrganizationDTO;
import com.user_organization_management.repository.OrganizationRepository;

@Service
public class OrganizationService {
	@Autowired 
	private OrganizationRepository organizationRepository;
	
	 public List<OrganizationDTO> getAllOrganizations() {
	        return organizationRepository.findAll()
	                .stream()
	                .map(org -> new OrganizationDTO(org.getId(), org.getName()))
	                .toList();
	 }
	 public Optional<OrganizationDTO> getOrganizationById(Long id) {
		 	return organizationRepository.findById(id)
                .map(org -> new OrganizationDTO(org.getId(), org.getName()));
	 }


	public OrganizationDTO createOrganization(OrganizationDTO dto) {
		if (organizationRepository.existsByName(dto.getName())) {
			throw new IllegalArgumentException("Organization name already exists!");
		}
		OrganizationEntity organization = new OrganizationEntity(dto.getName());
		organization = organizationRepository.save(organization);
		return new OrganizationDTO(organization.getId(), organization.getName());
	}

}
