package com.kemal.spring.domain;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for storing user roles
 * @author vlad
 *
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	/**
	 * Finds user roles by userId
	 * @param userId
	 * @return list of userRoles
	 */
	List<UserRole> findByUserId(Long userId);
	/**
	 * Deletes userRoles by userId
	 * @param userId
	 */
	void deleteByUserId(Long userId);
}
