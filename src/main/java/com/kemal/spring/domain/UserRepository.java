package com.kemal.spring.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmailOrUsername(String email, String username);
    User findByEmail(String email);
    User findById(Long id);
    User findByUsername(String username);
	User findByFacebookId(String facebookId);

}
