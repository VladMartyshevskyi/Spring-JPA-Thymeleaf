package com.kemal.spring.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for storing information about users
 * @author vlad
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
	 * Finds user by email or username
	 * @param email
	 * @param username
	 * @return found user
	 */
	User findByEmailOrUsername(String email, String username);
    User findByEmail(String email);
    /**
     * Finds user by id
     * @param id 
     * @return found user
     */
    User findById(Long id);
    /**
     * Finds user by username
     * @param username
     * @return found user
     */
    User findByUsername(String username);
    /**
     * Finds user by facebookId
     * @param facebookId
     * @return found user
     */
	User findByFacebookId(String facebookId);

}
