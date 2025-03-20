package com.user_organization_management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user_organization_management.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//	List<User> findByEmailContainingOrMobileContaining(String email, String mobile);
//	List<User> findByEmailContaining(String email);
//	List<User> findByMobileContaining(String mobile);
	
	 Page<User> findByEmailContainingOrMobileContaining(String email, String mobile, Pageable pageable);
	 Page<User> findByEmailLike(String email, Pageable pageable);
	 Page<User> findByMobileLike(String mobile, Pageable pageable);
}