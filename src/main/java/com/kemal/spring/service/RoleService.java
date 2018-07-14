package com.kemal.spring.service;

import java.util.List;
import java.util.stream.Collectors;

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

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	// region Find methods
	// ==================================================================================
	@Cacheable("cache.allRoles")
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Cacheable(value = "cache.roleByName", key = "#name", unless = "#result == null")
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}

	@Cacheable(value = "cache.roleById", key = "#id", unless = "#result == null")
	public Role findById(Long id) {
		return roleRepository.findById(id);
	}
	// ==================================================================================
	// endregion

	@CacheEvict(value = { "cache.allRoles", "cache.roleByName", "cache.roleById" }, allEntries = true)
	public void save(Role role) {
		roleRepository.save(role);
	}

	public boolean checkIfRoleNameIsTaken(List<Role> allRoles, Role role, Role persistedRole) {
		boolean roleNameAlreadyExists = false;
		for (Role role1 : allRoles) {
			// Check if the role name is edited and if it is taken
			if (!role.getName().equals(persistedRole.getName()) && role.getName().equals(role1.getName())) {
				roleNameAlreadyExists = true;
			}
		}
		return roleNameAlreadyExists;
	}

	public void assignRole(User user, Role role) {
		List<Role> roles = getAssignedRoles(user);
		if (!roles.contains(role)) {
			userRoleRepository.save(new UserRole(user.getId(), role.getId()));
		}
	}

	public List<Role> getAssignedRoles(User user) {
		return userRoleRepository.findByUserId(user.getId()).stream()
				.map(userRole -> findById(userRole.getRoleId()))
				.collect(Collectors.toList());
	}

}
