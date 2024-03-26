package com.jungstagram.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jungstagram.domain.Follow;
import com.jungstagram.domain.Token;
import com.jungstagram.domain.User;
import com.jungstagram.dto.UserDto;
import com.jungstagram.persistence.FollowRepository;
import com.jungstagram.persistence.PostRepository;
import com.jungstagram.persistence.TokenRepository;
import com.jungstagram.persistence.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PostRepository postRepo;
	@Autowired
	private TokenRepository tokenRepo;
	@Autowired
	private FollowRepository followRepo;

	@Transactional
	public User findUserById(Long userId) {
		return userRepo.findById(userId).get();
	}
	
	@Transactional
	public User findUserByUsernameAndPassword(UserDto user) {
		return userRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	
	@Transactional
	public User findUserByPostId(Long postId) {
		return postRepo.findById(postId)
				.get()
				.getUser();
	}
	
	@Transactional
	public User saveUser(UserDto dto) {
		return userRepo.save(User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.build());
	}
	
	@Transactional
	public List<Long> findFolloweeListByUserId(Long userId){
		List<Long> followeeList = new ArrayList<Long>();
		List<Follow> followList = followRepo.findByFollowerId(userId);
		
		for (Follow follow : followList) {
			followeeList.add(follow.getFollowee().getId());
		}
		return followeeList;
	}

	public Token saveToken(User user) {
		Token token = new Token();
		token.setId(user.getId());
		token.setCreatedAt(new Date());

		return tokenRepo.save(token);
	}
	
}
