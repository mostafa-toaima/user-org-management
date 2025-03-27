package com.user_organization_management.service;

import java.util.List;
import java.util.Optional;

import com.user_organization_management.entity.OrganizationEntity;
import com.user_organization_management.entity.UserEntity;
import com.user_organization_management.exception.EntityNotFoundException;
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

	public OrganizationDTO updateOrganization(Long id, OrganizationDTO dto) {
		OrganizationEntity organization = organizationRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Organization not found!"));
		if (!organization.getName().equals(dto.getName()) && organizationRepository.existsByName(dto.getName())) {
			throw new IllegalArgumentException("Organization name already exists!");
		}
		organization.setName(dto.getName());
		organization = organizationRepository.save(organization);
		return new OrganizationDTO(organization.getId(), organization.getName());
	}


	public void deleteOrg(Long id) {
		OrganizationEntity org = organizationRepository.findById(id).orElseThrow(() ->
				new EntityNotFoundException("Organization with id " + id + " not found"));
//		userRepository.deleteByOrganizationId(id); // Custom delete query
		organizationRepository.deleteById(org.getId());
		//(search) not delete direct
	}
}
