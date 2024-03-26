package com.jungstagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jungstagram.domain.Follow;
import com.jungstagram.domain.User;
import com.jungstagram.persistence.FollowRepository;
import com.jungstagram.persistence.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FollowService {
	@Autowired
	private FollowRepository followRepo;
	@Autowired
	private UserRepository userRepo;
	
	@Transactional
	public void saveFollowByUserIdAndFolloweeId(Long userId, Long followeeId) {
		followRepo.save(Follow.builder()
				.follower(userRepo.findById(userId).get())
				.followee(userRepo.findById(followeeId).get())
				.build());
	}
	
	@Transactional
	public void deleteFollowByUserIdAndFolloweeId(Long userId, Long followeeId) {
		followRepo.deleteByFollowerIdAndFolloweeId(userId, followeeId);
	}

}
