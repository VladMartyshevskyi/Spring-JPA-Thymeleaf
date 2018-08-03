package com.kemal.spring.service;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Role;
import com.kemal.spring.domain.User;
import com.kemal.spring.domain.UserRepository;
import com.kemal.spring.web.dto.UserDto;

@Service
public class UserService {
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private CardService cardService;

	public List<User> findAll() {
		logger.debug("Method findAll was invoked");
		return userRepository.findAll();
	}

	public Page<User> findAllPageable(Pageable pageable) {
		logger.debug("Method findAllPageable was invoked with pageable {}", pageable);
		return userRepository.findAll(pageable);
	}

	public User findByEmail(String email) {
		logger.debug("Method findByEmail was invoked with email {}", email);
		return userRepository.findByEmail(email);
	}

	public User findById(Long id) {
		logger.debug("Method findById was invoked with id {}", id);
		return userRepository.findById(id);
	}

	public User findByUsername(String username) {
		logger.debug("Method findByUsername was invoked with username {}", username);
		return userRepository.findByUsername(username);
	}
	
	public void save(User user) {
		logger.debug("Method save was invoked with user {}", user);
		userRepository.save(user);
	}

	public void delete(Long id) {
		logger.debug("Method delete was invoked with id {}", id);
		roleService.removeAllRoles(findById(id));
		cardService.deleteByUserId(id);
		userRepository.delete(id);
		logger.info("User with id {} was deleted", id);
	}

	public void createUser(UserDto userDto) {
		logger.debug("Method createUser was invoked with userDto {}", userDto);
		User existingUser = userRepository.findByEmailOrUsername(userDto.getEmail(), userDto.getUsername());
		if(existingUser != null) {
			logger.error("User from userDto already exists. userDto: {}", userDto);
			throw new RuntimeException("User already exists");
		} else {
			User user = UserConverter.convertToUser(userDto, bCryptPasswordEncoder.encode(userDto.getPassword()));
			save(user);
			roleService.assignRole(user, roleService.findByName("ROLE_USER"));
			logger.info("User was created {}", user);
		}
	}
	
	public void updateUser(UserDto userDto, List<Role> roles) {
		logger.debug("Method updateUser was invoked with userDto {} and roles {} ",userDto, roles);
		User user = findById(userDto.getId());
		save(UserConverter.convertToUser(userDto, user.getPassword()));
		roleService.removeAllRoles(user);
		for(Role role : roles) {
			roleService.assignRole(user, role);
		}
		logger.info("User was updated {} with roles {}", user, roles);
	}

	public void createSocialUser(Principal principal) {
		User existingUser = findByUsername(principal.getName());
		if(existingUser == null) {
			User user = new User();
			user.setFacebookId(principal.getName());
			user.setUsername(principal.getName());
			save(user);
		}
		
	}

}
