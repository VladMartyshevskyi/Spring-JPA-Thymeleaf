package com.kemal.spring.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for storing information about roles
 * @author vlad
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	/**
	 * Finds role by given name
	 * @param name
	 * @return found role
	 */
	Role findByName(String name);

	/**
	 * Finds role by given id
	 * @param id
	 * @return found role
	 */
	Role findById(Long id);
}
