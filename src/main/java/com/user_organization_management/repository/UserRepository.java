package com.user_organization_management.repository;

import com.user_organization_management.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
//	List<User> findByEmailContainingOrMobileContaining(String email, String mobile);
//	List<User> findByEmailContaining(String email);
//	List<User> findByMobileContaining(String mobile);
//
//	 Page<UserEntity> findByEmailContainingOrMobileContaining(String email, String mobile, Pageable pageable);
//	 Page<UserEntity> findByEmailLike(String email, Pageable pageable);
//	 Page<UserEntity> findByMobileLike(String mobile, Pageable pageable);
	 Optional<UserEntity> findByName(String name);
}