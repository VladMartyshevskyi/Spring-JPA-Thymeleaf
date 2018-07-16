package com.kemal.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
	
	@CacheEvict(value = { "cache.allRoles", "cache.roleByName", "cache.roleById" }, allEntries = true)
	public void save(Role role) {
		roleRepository.save(role);
	}

	@Transactional
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
	@Transactional
	public void removeAllRoles(User user) {
		userRoleRepository.deleteByUserId(user.getId());
	}

}
