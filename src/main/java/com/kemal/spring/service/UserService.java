package com.kemal.spring.service;

import java.util.List;

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

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private CardService cardService;

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
	
	@CacheEvict(value = { "cache.allUsersPageable", "cache.allUsers", "cache.userByEmail", "cache.userById",
			"cache.allUsersEagerly" }, allEntries = true)
	public void save(User user) {
		userRepository.save(user);
	}

	@CacheEvict(value = { "cache.allUsersPageable", "cache.allUsers", "cache.userByEmail", "cache.userById",
			"cache.allUsersEagerly" }, allEntries = true)
	public void delete(Long id) {
		roleService.removeAllRoles(findById(id));
		cardService.deleteByUserId(id);
		userRepository.delete(id);
	}

	public void createUser(UserDto userDto) {
		User existingUser = userRepository.findByEmailOrUsername(userDto.getEmail(), userDto.getUsername());
		if(existingUser != null) {
			throw new RuntimeException("User already exists");
		} else {
			User user = UserConverter.convertToUser(userDto, bCryptPasswordEncoder.encode(userDto.getPassword()));
			save(user);
			roleService.assignRole(user, roleService.findByName("ROLE_USER"));
		}
	}
	
	public void updateUser(UserDto userDto, List<Role> roles) {
		User user = findById(userDto.getId());
		save(UserConverter.convertToUser(userDto, user.getPassword()));
		roleService.removeAllRoles(user);
		for(Role role : roles) {
			roleService.assignRole(user, role);
		}
	}

}
