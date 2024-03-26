package com.jungstagram.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jungstagram.domain.Token;
import com.jungstagram.domain.User;
import com.jungstagram.dto.FollowDto;
import com.jungstagram.dto.UserDto;
import com.jungstagram.service.FollowService;
import com.jungstagram.service.PostService;
import com.jungstagram.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserRestController {

	@Autowired
	private UserService userService;
	@Autowired
	private FollowService followService;
	
	@GetMapping("/user")
	public User getUser(@RequestParam(value = "id") Long id) {
		return userService.findUserById(id);
	}

	@PostMapping("/user")
	public User saveUser(@RequestBody UserDto dto) {
		return userService.saveUser(dto);
	}

	@PostMapping("/auth")
	public Token loginUser(@RequestBody UserDto user, HttpServletRequest request) {
		User result = userService.findUserByUsernameAndPassword(user);
		if (result != null) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", result.getId());
			return userService.saveToken(result);
		}
		return null;
	}
	
	@PostMapping("/follow")
	public boolean follow(@RequestBody FollowDto followDto, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		followService.saveFollowByUserIdAndFolloweeId(userId, followDto.getFollowee_id());
		return true;
	}

	@DeleteMapping("/follow")
	public boolean unfollow(@RequestBody FollowDto followDto, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		followService.deleteFollowByUserIdAndFolloweeId(userId, followDto.getFollowee_id());
		return true;
	}
}
