package com.jungstagram.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowDto {

	private Long follower_id;
	private Long followee_id;
	
	@Builder
	public FollowDto(Long follower_id, Long followee_id) {
		this.follower_id = follower_id;
		this.followee_id = followee_id;
	}
}
