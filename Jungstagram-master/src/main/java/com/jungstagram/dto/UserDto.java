package com.jungstagram.dto;

import com.jungstagram.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	private Long id;
	private String password;
	private String username;
	
	public User toEntity() {
		return User.builder()
				.id(id)
				.password(password)
				.username(username)
				.build();
			
	}
}
