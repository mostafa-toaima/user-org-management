package com.user_organization_management.service;

import java.util.List;
import java.util.Optional;

import com.user_organization_management.entity.OrganizationEntity;
import com.user_organization_management.entity.UserEntity;
import com.user_organization_management.mapper.UserMapper;
import com.user_organization_management.model.CustomPageResponse;
import com.user_organization_management.specification.UserSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

	private final UserMapper userMapper = UserMapper.INSTANCE;


	public CustomPageResponse<UserDTO> getUsersByFilterWithPagination(String email, String mobile, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

		Specification<UserEntity> spec = UserSpecification.filterByEmailAndMobile(email, mobile);

		Page<UserEntity> userPage = userRepository.findAll(spec, pageable);

		List<UserDTO> users = userPage.getContent().stream().map(userMapper::toDTO).toList();

		return CustomPageResponse.<UserDTO>builder()
				.content(users)
				.currentPage(userPage.getNumber())
				.totalPages(userPage.getTotalPages())
				.totalElements(userPage.getTotalElements())
				.isFirst(userPage.isFirst())
				.isLast(userPage.isLast())
				.pageSize(userPage.getSize())
				.build();
	}
	public Optional<UserDTO> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found")));
    }

	public UserDTO createUser(UserDTO userDto) {
		logger.info("Cereate User: {}", userDto);
		UserEntity user = userMapper.toEntity(userDto);
		return userMapper.toDTO(userRepository.save(user));

	}

	public UserDTO assignUserToOrganization(Long userId, Long orgId) {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Use Not Found")) ;
		OrganizationEntity organization = organizationRepository.findById(orgId)
				.orElseThrow(() -> new RuntimeException("OrganizationEntity Not Found")) ;
		user.setOrganization(organization);
		return userMapper.toDTO(userRepository.save(user));
	}
	
	public UserDTO unassignUserFromOrganization(Long userId) {
		logger.info("un assign user: {}", userId);
		UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User Not Found"));
	    user.setOrganization(null);
		logger.info("user after un assign : {}", user);

		return userMapper.toDTO(userRepository.save(user));
	}
	
	public void deleteUser(Long id) {
		logger.info("check User with id :{}", id);
		UserEntity user = userRepository.findById(id).orElseThrow(() ->
		new EntityNotFoundException("User with id " + id + " not found"));	
		
		logger.info("user  deleted: {}", user);
	    userRepository.deleteById(user.getId());
	    //(search) not delete direct
	}
	
//	private UserDTO convertEntityToDTO(UserEntity user) {
////        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getMobile(),
////                user.getOrganization() != null ? user.getOrganization().getId() : null);
//		return new UserDTO(user.getId(), user.getName(),user.getPassword() ,user.getEmail(), user.getMobile(),
//                user.getOrganization() != null ? user.getOrganization().getId() : null);
//    }

//    private UserEntity convertDTOToEntity(UserDTO userDTO) {
//        OrganizationEntity organization = userDTO.getOrganizationId() != null ?
//                organizationRepository.findById(userDTO.getOrganizationId()).orElse(null) : null;
//		String existingPassword = userDTO.getId() != null
//				? userRepository.findById(userDTO.getId()).map(UserEntity::getPassword).orElse(null)
//				: null;
//        return new UserEntity(userDTO.getId(), userDTO.getName(), existingPassword ,userDTO.getEmail(),
//                userDTO.getMobile(), organization);
//    }
}
