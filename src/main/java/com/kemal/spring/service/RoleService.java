package com.kemal.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Role;
import com.kemal.spring.domain.RoleRepository;
import com.kemal.spring.domain.User;
import com.kemal.spring.domain.UserRole;
import com.kemal.spring.domain.UserRoleRepository;

@Service
public class RoleService {
	
	private static Logger logger = LoggerFactory.getLogger(CardService.class);

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	public List<Role> findAll() {
		logger.debug("Method findAll was invoked");
		return roleRepository.findAll();
	}

	public Role findByName(String name) {
		logger.debug("Method findByName was invoked with name {}", name);
		return roleRepository.findByName(name);
	}

	public Role findById(Long id) {
		logger.debug("Method findById was invoked with id {}", id);
		return roleRepository.findById(id);
	}
	
	public void save(Role role) {
		logger.debug("Method save was invoked with role {}", role);
		Role existingRole = findByName(role.getName());
		if (existingRole != null && !existingRole.getName().equals(role.getName())) {
			logger.error("Role name is already taken {}", role);
			throw new RuntimeException("Role name is already taken");
		} 
		roleRepository.save(role);
		logger.info("Role was saved {}", role);
	}

	
	@Transactional
	public void assignRole(User user, Role role) {
		logger.debug("Method assignRole was invoked with user {} and role {}", user, role);
		List<Role> roles = getAssignedRoles(user);
		if (!roles.contains(role)) {
			userRoleRepository.save(new UserRole(user.getId(), role.getId()));
			logger.info("User with id {} got role {}", user.getId(), role);
		}
	}

	public List<Role> getAssignedRoles(User user) {
		logger.debug("Method getAssignedRoles was invoked with user {}", user);
		return userRoleRepository.findByUserId(user.getId()).stream()
				.map(userRole -> findById(userRole.getRoleId()))
				.collect(Collectors.toList());
	}
	@Transactional
	public void removeAllRoles(User user) {
		userRoleRepository.deleteByUserId(user.getId());
		logger.debug("All roles were removed in user {}", user);
	}

}
