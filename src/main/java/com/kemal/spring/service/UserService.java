package com.kemal.spring.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
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
import com.kemal.spring.web.dto.UserUpdateDto;
import com.mysql.fabric.xmlrpc.base.Array;

@Service
public class UserService {

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UserRepository userRepository;
	private RoleService roleService;
	

	public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository,
			RoleService roleService) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
		this.roleService = roleService;
	}

	// region find methods
	// ==============================================================================================
	@Cacheable(value = "cache.allUsers")
	public List<User> findAll() {
		return userRepository.findAll();
	}



	@Cacheable(value = "cache.allUsersPageable")
	public Page<User> findAllPageable(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Cacheable(value = "cache.userByEmail", key = "#email", unless = "#result == null")
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Cacheable(value = "cache.userById", key = "#id", unless = "#result == null")
	public User findById(Long id) {
		return userRepository.findById(id);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	// ==============================================================================================
	// endregion

	@CacheEvict(value = { "cache.allUsersPageable", "cache.allUsers", "cache.userByEmail", "cache.userById",
			"cache.allUsersEagerly" }, allEntries = true)
	public void save(User user) {
		userRepository.save(user);
	}

	@CacheEvict(value = { "cache.allUsersPageable", "cache.allUsers", "cache.userByEmail", "cache.userById",
			"cache.allUsersEagerly" }, allEntries = true)
	public void delete(Long id) {
		userRepository.delete(id);
	}

	public void createUser(UserDto userDto) {
		User existingUser = userRepository.findByEmailOrUsername(userDto.getEmail(), userDto.getUsername());
		if(existingUser != null) {
			// to do
		} else {
			User user = UserConverter.convertToUser(userDto, bCryptPasswordEncoder.encode(userDto.getPassword()));
			roleService.assignRole(user, roleService.findByName("ROLE_USER"));
			save(user);
		}
	}

	public void updateUser(User user, UserUpdateDto userUpdateDto) {
		user.setName(userUpdateDto.getName());
		user.setSurname(userUpdateDto.getSurname());
		user.setUsername(userUpdateDto.getUsername());
		user.setEmail(userUpdateDto.getEmail());
	//	user.setRoles(getAssignedRolesList(userUpdateDto));
		user.setEnabled(userUpdateDto.isEnabled());
	}

}
