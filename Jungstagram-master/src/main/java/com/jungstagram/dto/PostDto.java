package com.jungstagram.dto;

import com.jungstagram.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Long id;
	private String title;
	private String content;
	private User user;
	
	@Builder
	public PostDto(String title, String content, User user) {
		this.title = title;
		this.content = content;
		this.user = user;
	}
}
