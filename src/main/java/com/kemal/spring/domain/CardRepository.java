package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
	void deleteById(Long id);
	Card findById(Long id);
	List<Card> findByUserId(Long userId);
	void deleteByUserId(Long userId);
}
