package com.user_organization_management.service;

import java.util.List;
import java.util.Optional;

import com.user_organization_management.entity.OrganizationEntity;
import com.user_organization_management.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.user_organization_management.dto.UserDTO;
import com.user_organization_management.exception.EntityNotFoundException;
import com.user_organization_management.repository.OrganizationRepository;
import com.user_organization_management.repository.UserRepository;

@Service
public class UserService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserService.class); 

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private OrganizationRepository organizationRepository;
	
	public List<UserDTO> getAllUsers() {
        logger.info("Fetching all users...");
        List<UserEntity> users = userRepository.findAll();
        logger.info("Found {} users", users.size());
        return users.stream().map(this::convertEntityToDTO).toList();
    }
	
//    public List<UserDTO> getUsersByFilter(String email, String mobile) {
//        if (email != null && mobile != null) {
//            return userRepository.findByEmailContainingOrMobileContaining(email, mobile)
//                    .stream().map(this::convertToDTO).toList();
//        } else if (email != null) {
//            return userRepository.findByEmailContaining(email)
//                    .stream().map(this::convertToDTO).toList();
//        } else if (mobile != null) {
//            return userRepository.findByMobileContaining(mobile)
//                    .stream().map(this::convertToDTO).toList();
//        } else {
//            return getAllUsers();
//        }
//    }
//	
    public Page<UserDTO> getUsersByFilterWithPagination(String email, String mobile, int page, int size) {
        Pageable pageable = PageRequest.of(page, size); 
        Page<UserEntity> usersPage;
        logger.info("Filtering users by email: {} and mobile: {}", email, mobile);
        if (email != null && mobile != null) {
            usersPage = userRepository.findByEmailContainingOrMobileContaining(email, mobile, pageable);
        } else if (email != null) {
            usersPage = userRepository.findByEmailLike(email, pageable);
        } else if (mobile != null) {
            usersPage = userRepository.findByMobileLike(mobile, pageable);
        } else {
            usersPage = userRepository.findAll(pageable);
        }
        logger.info("Filtered users count: {}", usersPage.getNumberOfElements());

        return usersPage.map(this::convertEntityToDTO); 
    }
    
//	public Optional<UserDTO> getUserById(Long id) {
//		logger.info("Fetching user by id: {}", id);
//        return userRepository.findById(id).map(this::convertEntityToDTO);
//	}
	
    public Optional<UserDTO> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .map(this::convertEntityToDTO)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found")));
    }
    
	public UserDTO createUser(UserDTO userDto) {
		logger.info("Cereate User: {}", userDto);

		UserEntity user = convertDTOToEntity(userDto);
		UserEntity savedUser = userRepository.save(user);
		logger.info("user Saved: {}", savedUser);
		return convertEntityToDTO(savedUser);
		
	}
	
	public UserDTO assignUserToOrganization(Long userId, Long orgId) {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Use Not Found")) ;
		OrganizationEntity organization = organizationRepository.findById(orgId)
				.orElseThrow(() -> new RuntimeException("OrganizationEntity Not Found")) ;
		user.setOrganization(organization);
        return convertEntityToDTO(userRepository.save(user));
	}
	
	public UserDTO unassignUserFromOrganization(Long userId) {
		logger.info("un assign user: {}", userId);
		UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User Not Found"));
	    user.setOrganization(null);
		logger.info("user after un assign : {}", user);

	    return convertEntityToDTO(userRepository.save(user));
	}
	
	public void deleteUser(Long id) {
		logger.info("check User with id :{}", id);
		UserEntity user = userRepository.findById(id).orElseThrow(() ->
		new EntityNotFoundException("User with id " + id + " not found"));	
		
		logger.info("user  deleted: {}", user);
	    userRepository.deleteById(user.getId());
	    //(search) not delete direct
	}
	
	private UserDTO convertEntityToDTO(UserEntity user) {
//        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getMobile(),
//                user.getOrganization() != null ? user.getOrganization().getId() : null);
		return new UserDTO(user.getId(), user.getName(),user.getPassword() ,user.getEmail(), user.getMobile(),
                user.getOrganization() != null ? user.getOrganization().getId() : null);
    }

    private UserEntity convertDTOToEntity(UserDTO userDTO) {
        OrganizationEntity organization = userDTO.getOrganizationId() != null ?
                organizationRepository.findById(userDTO.getOrganizationId()).orElse(null) : null;

        return new UserEntity(userDTO.getId(), userDTO.getName(), userDTO.getPassword() ,userDTO.getEmail(),
                userDTO.getMobile(), organization);
    }
}
