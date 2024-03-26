package com.jungstagram.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jungstagram.domain.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {
	List<Follow> findByFollowerId(Long userId);
	
	Long deleteByFollowerIdAndFolloweeId(Long userId, Long followeeId);
}
