package com.kemal.spring.domain;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	
	List<UserRole> findByUserId(Long userId);
}
