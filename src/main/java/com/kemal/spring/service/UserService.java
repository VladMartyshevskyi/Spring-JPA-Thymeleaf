package com.kemal.spring.service;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
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

	public User findByFacebookId(String facebookId) {
		logger.debug("Method findByFacebookId was invoked with id {}", facebookId);
		return userRepository.findByFacebookId(facebookId);
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
	
	@Transactional
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

	public void authenticateSocialUser(Principal principal ) {
		User user = findByFacebookId(principal.getName());
		if(user == null) {
			OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
	        Authentication authentication = oAuth2Authentication.getUserAuthentication();
	        Map<String, String> authenticationDetails = (LinkedHashMap<String, String>) authentication.getDetails();
	        user = new User();
	  
			user.setFacebookId(principal.getName());
			user.setUsername(authenticationDetails.get("name"));
			user.setName(authenticationDetails.get("name"));
			user.setEnabled(true);
			save(user);
			roleService.assignRole(user, roleService.findByName("ROLE_USER"));
			logger.info("Social user registered {} ", user);
		}
		logger.debug("Logging with user {}", user);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), "", Arrays.asList(
			      new SimpleGrantedAuthority("ROLE_USER")));
		SecurityContextHolder.getContext().setAuthentication(token);
	}

}
