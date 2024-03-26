package com.jungstagram.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jungstagram.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class WebController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		if (userId != null) {
			model.addAttribute("userId", userId);
		}
		return "index";
	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping(value = "/post/detail/{postId}")
	public String detail(@PathVariable Long postId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		Long writerId = userService.findUserByPostId(postId).getId();
		System.out.println(userId.equals(writerId));
		System.out.println("writer = " + writerId + " userId = " + userId);
		if (userId.equals(writerId)) {
			model.addAttribute("writer", writerId);
		}
		model.addAttribute("id", postId);
		return "detail";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		Object sessionCheck = session.getAttribute("userId");
		if (sessionCheck != null) {
			session.removeAttribute("userId");
		}
		return "login";
	}
}
