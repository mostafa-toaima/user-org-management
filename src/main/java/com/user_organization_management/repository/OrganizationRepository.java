package com.user_organization_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user_organization_management.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}